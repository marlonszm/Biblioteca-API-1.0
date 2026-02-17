package io.github.marlonszm.libraryapi.controller.dto;

import io.github.marlonszm.libraryapi.model.GeneroLivro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Cadastro de livros")
public record CadastroLivroDTO(
        @ISBN
        @NotBlank(message = "campo obrigatório")
        @Schema(name = "ISBN")
        String isbn,
        @NotBlank(message = "campo obrigatório")
        @Schema(name = "Titulo")
        String titulo,
        @NotNull(message = "campo obrigatório")
        @Past(message = "não pode ser uma data futura")
        @Schema(name = "Data de publicação")
        LocalDate dataPublicacao,
        @Schema(name = "Gênero")
        GeneroLivro genero,
        @Schema(name = "Preço")
        BigDecimal preco,
        @Schema(name = "ID do Autor")
        @NotNull(message = "campo obrigatório")
        UUID idAutor) {
}
