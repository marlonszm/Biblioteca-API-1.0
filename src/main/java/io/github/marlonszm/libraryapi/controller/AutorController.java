package io.github.marlonszm.libraryapi.controller;

import io.github.marlonszm.libraryapi.controller.dto.AutorDTO;
import io.github.marlonszm.libraryapi.controller.dto.ErroResposta;
import io.github.marlonszm.libraryapi.controller.mappers.AutorMapper;
import io.github.marlonszm.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.marlonszm.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.marlonszm.libraryapi.model.Autor;
import io.github.marlonszm.libraryapi.model.Usuario;
import io.github.marlonszm.libraryapi.security.SecurityService;
import io.github.marlonszm.libraryapi.service.AutorService;
import io.github.marlonszm.libraryapi.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@Tag(name = "Autores")
@RequestMapping("/autores")
// https://localhost:8080/autores
public class AutorController implements GenericController {

    private final AutorService autorService;
    private final SecurityService securityService;
    private final AutorMapper autorMapper;

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(
            summary = "Salvar",
            description = "Cadastrar novo autor"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso."),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "409", description = "Autor já cadastrado")
    })
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO dto) {
        Autor autorEntidade = autorMapper.toEntity(dto);
        autorService.salvar(autorEntidade);
        // Serve para captar os dados da requisição atual para construir uma nova URL
        // Domínio e Path da API
        // Nesse caso, inserir o id do usuário no path da URL https://localhost:8080/autores/{id}
        URI location = gerarHeaderLocation(autorEntidade.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(
            summary = "Obter detalhes",
            description = "Retorna os dados do autor pelo ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor encontrado"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable String id) {
        var idAutor = UUID.fromString(id);
        return autorService
                .visualizarPorId(idAutor)
                .map(autor -> {
                    AutorDTO dto = autorMapper.toDTO(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(
            summary = "Deletar",
            description = "Deleta um autor existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado"),
            @ApiResponse(responseCode = "400", description = "Autor não possui livro cadastrado")
    })
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.visualizarPorId(idAutor);
        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        autorService.deletar(autorOptional.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(
            summary = "Pesquisar",
            description = "Realiza pesquisa de autores por parâmetros"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso."),
    })
    public ResponseEntity<List<AutorDTO>> pesquisar(@RequestParam(value = "name", required = false) String nome,
                                                    @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        List<Autor> resultado = autorService.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> lista = resultado
                .stream().map(autorMapper::toDTO).collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(
            summary = "Atualizar",
            description = "Atualiza autor existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado"),
            @ApiResponse(responseCode = "409", description = "Autor já cadastrado")
    })
    public ResponseEntity<Void> atualizar(@PathVariable String id,
                                            @RequestBody @Valid AutorDTO dto) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.visualizarPorId(idAutor);
        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Autor autorEntidade = autorOptional.get();
        autorEntidade.setName(dto.name());
        autorEntidade.setNacionalidade(dto.nacionalidade());
        autorEntidade.setDataNascimento(dto.dataNascimento());
        autorService.atualizar(autorEntidade);
        return ResponseEntity.noContent().build();

    }
}
