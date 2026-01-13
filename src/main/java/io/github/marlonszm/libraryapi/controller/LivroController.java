package io.github.marlonszm.libraryapi.controller;

import io.github.marlonszm.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.marlonszm.libraryapi.controller.dto.ErroResposta;
import io.github.marlonszm.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import io.github.marlonszm.libraryapi.controller.mappers.LivroMapper;
import io.github.marlonszm.libraryapi.exceptions.RegistroDuplicadoException;
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

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService livroService;
    private final LivroMapper livroMapper;

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
                .map(livro ->{
                    var dto = livroMapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());

    }



}
