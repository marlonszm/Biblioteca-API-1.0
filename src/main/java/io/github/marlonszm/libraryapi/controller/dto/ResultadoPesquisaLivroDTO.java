package io.github.marlonszm.libraryapi.controller.dto;

import io.github.marlonszm.libraryapi.model.GeneroLivro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ResultadoPesquisaLivroDTO(
        UUID id
        ,String isbn
        , String titulo
        , LocalDate dataPublicacao
        , GeneroLivro genero
        , BigDecimal preco
        , UUID idAutor
        , AutorDTO autor
        ) {
}
