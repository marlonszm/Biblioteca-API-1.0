package io.github.marlonszm.libraryapi.controller;

import io.github.marlonszm.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.marlonszm.libraryapi.controller.dto.ErroResposta;
import io.github.marlonszm.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import io.github.marlonszm.libraryapi.controller.mappers.LivroMapper;
import io.github.marlonszm.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.marlonszm.libraryapi.model.GeneroLivro;
import io.github.marlonszm.libraryapi.model.Livro;
import io.github.marlonszm.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService livroService;
    private final LivroMapper  livroMapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        Livro livro = livroMapper.toEntity(dto);
        livroService.salvar(livro);
        var url = gerarHeaderLocation(livro.getId());
        return ResponseEntity.created(url).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(@PathVariable("id") String id) {
        return livroService.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    var dto = livroMapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id) {
        return livroService.obterPorId(UUID.fromString(id)).map(livro -> {
            livroService.deletar(livro);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ResultadoPesquisaLivroDTO>> pesquisa(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "nome-autor", required = false)
            String nomeAutor,
            @RequestParam(value = "genero", required = false)
            GeneroLivro genero,
            @RequestParam(value = "ano-publicacao", required = false)
            Integer anoPublicacao
    ) {
        var resultado = livroService.pesquisa(isbn, titulo, nomeAutor, genero, anoPublicacao);
        var lista = resultado.stream().map(livroMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }


}
