package io.github.marlonszm.libraryapi.controller;

import io.github.marlonszm.libraryapi.security.CustomAuthentication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Tag(name = "Login")
public class LoginViewController {

    @Operation(
            summary = "Página inicial",
            description = "Retorna o html da página inicial"
    )
    @GetMapping("/login")
    public String paginaLogin() {
        return "login";
    }

    @GetMapping("/")
    @ResponseBody
    public String paginaHome(Authentication authentication) {
        if(authentication instanceof CustomAuthentication customAuth){
            System.out.println(customAuth.getUsuario());
        }
        return "Olá " + authentication.getName();
    }

    @GetMapping("/authorized")
    @ResponseBody
    @Operation(
            summary = "Solicitar código de autorização",
            description = "Solcita código de autorização para utilização da aplicação"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso.")
    })
    public String getAuthorizationCode(@RequestParam("code") String code){
        return "Seu authorization code" + code;
    }

}
