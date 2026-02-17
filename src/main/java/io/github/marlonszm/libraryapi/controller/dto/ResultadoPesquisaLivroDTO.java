package io.github.marlonszm.libraryapi.controller.dto;

import io.github.marlonszm.libraryapi.model.GeneroLivro;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Schema(name = " Resultado da pesquisa de livros")
public record ResultadoPesquisaLivroDTO(
        UUID id
        ,@Schema(name = "ISBN")
        String isbn
        ,@Schema(name = "Titulo")
        String titulo
        ,@Schema(name = "Data de publicação")
        LocalDate dataPublicacao
        ,@Schema(name = "Genero")
        GeneroLivro genero
        ,@Schema(name = "Preço")
        BigDecimal preco
        ,@Schema(name = " ID do Autor")
        UUID idAutor
        , AutorDTO autor
        ) {
}
