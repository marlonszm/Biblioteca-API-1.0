package io.github.marlonszm.libraryapi.controller;

import io.github.marlonszm.libraryapi.controller.dto.AutorDTO;
import io.github.marlonszm.libraryapi.controller.dto.ErroResposta;
import io.github.marlonszm.libraryapi.controller.mappers.AutorMapper;
import io.github.marlonszm.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.marlonszm.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.marlonszm.libraryapi.model.Autor;
import io.github.marlonszm.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/autores")
// https://localhost:8080/autores
public class AutorController implements GenericController {

    private final AutorService autorService;
    private final AutorMapper autorMapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO dto) {
        try{
            Autor autorEntidade = autorMapper.toEntity(dto);
            autorService.salvar(autorEntidade);
            // Serve para captar os dados da requisição atual para construir uma nova URL
            // Domínio e Path da API
            // Nesse caso, inserir o id do usuário no path da URL https://localhost:8080/autores/{id}
            URI location = gerarHeaderLocation(autorEntidade.getId());
            return ResponseEntity.created(location).build();
        }catch(RegistroDuplicadoException e){
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
     }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable String id){
        var idAutor = UUID.fromString(id);
        return autorService
                .visualizarPorId(idAutor)
                .map(autor -> {
                    AutorDTO dto = autorMapper.toDTO(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable String id){
        try{
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = autorService.visualizarPorId(idAutor);
            if(autorOptional.isEmpty()) {
                return  ResponseEntity.notFound().build();
            }
            autorService.deletar(autorOptional.get());
            return ResponseEntity.noContent().build();
        }catch(OperacaoNaoPermitidaException e){
            var erroResposta = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erroResposta.status()).body(erroResposta);
        }
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(@RequestParam(value = "name", required = false) String nome,
                                                    @RequestParam(value = "nacionalidade", required = false) String nacionalidade){
        List<Autor> resultado =  autorService.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> lista = resultado
                .stream().map(autorMapper::toDTO).collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable String id,
                                          @RequestBody @Valid AutorDTO dto){
        try{
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = autorService.visualizarPorId(idAutor);
            if(autorOptional.isEmpty()) {
                return  ResponseEntity.notFound().build();
            }
            Autor autorEntidade = autorOptional.get();
            autorEntidade.setName(dto.name());
            autorEntidade.setNacionalidade(dto.nacionalidade());
            autorEntidade.setDataNascimento(dto.dataNascimento());
            autorService.atualizar(autorEntidade);
            return ResponseEntity.noContent().build();
        }catch(RegistroDuplicadoException e){
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }

    }
}
