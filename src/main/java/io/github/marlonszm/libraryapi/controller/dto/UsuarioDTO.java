package io.github.marlonszm.libraryapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Schema(name = "Usuários")
public record UsuarioDTO (
                          @NotBlank(message = "campo obrigatório")
                          @Schema(name = "Login")
                          String login,
                          @NotBlank(message = "campo obrigatório")
                          @Email(message = "inválido")
                          @Schema(name = "email")
                          String email,
                          @NotBlank(message = "campo obrigatório")
                          @Schema(name = "nome")
                          String senha,
                          List<String> roles){
}
