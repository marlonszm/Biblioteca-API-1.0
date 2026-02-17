package io.github.marlonszm.libraryapi.controller;

import io.github.marlonszm.libraryapi.controller.dto.UsuarioDTO;
import io.github.marlonszm.libraryapi.controller.mappers.UsuarioMapper;
import io.github.marlonszm.libraryapi.model.Usuario;
import io.github.marlonszm.libraryapi.repository.UsuarioRepository;
import io.github.marlonszm.libraryapi.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
@Tag(name = "Usuários")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Salvar",
            description = "Cadastrar novo usuário"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso."),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "409", description = "Usuário já cadastrado")
    })
    public void salvar(@RequestBody @Valid UsuarioDTO dto){
        var usuario = usuarioMapper.toEntity(dto);
        usuarioService.salvar(usuario);
    }



}


