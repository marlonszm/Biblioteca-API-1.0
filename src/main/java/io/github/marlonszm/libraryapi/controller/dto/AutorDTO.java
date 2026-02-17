package io.github.marlonszm.libraryapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Autor")
public record AutorDTO(UUID id,
                       @NotBlank(message = "campo obrigatório")
                       @Size(min = 2, max = 100, message = "campo fora do tamanho padrão")
                       @Schema(name = "nome")
                       String name,
                       @NotNull(message = "campo obrigatório")
                       @Past(message = "não pode ser uma data futura")
                       @Schema(name = "Data de nascimento")
                       LocalDate dataNascimento,
                       @NotBlank(message = "campo obrigatório")
                       @Size(max = 50, min = 2, message = "campo fora do tamanho padrão")
                       @Schema(name = "Nacionalidade")
                       String nacionalidade)
{

}
