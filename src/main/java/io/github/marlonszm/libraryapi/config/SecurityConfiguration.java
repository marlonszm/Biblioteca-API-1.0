package io.github.marlonszm.libraryapi.config;

import io.github.marlonszm.libraryapi.model.Usuario;
import io.github.marlonszm.libraryapi.security.CustomUserDetailsService;
import io.github.marlonszm.libraryapi.security.LoginSocialSuccessHandler;
import io.github.marlonszm.libraryapi.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, LoginSocialSuccessHandler successHandler) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .formLogin(configurer ->{
                    configurer.loginPage("/login");
                })
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/login").permitAll();
                    authorize.requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll();

                    // regras de autor e livro nos seus prórpios controllers

                    // "anyRequest" deve ser sempre a última na ordem de precedência de
                    // autorizações de consulta de endpoints
                    authorize.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 ->
                        oauth2
                                .loginPage("/login")
                                .successHandler(successHandler)
                )
                .oauth2ResourceServer(oauth2RS -> oauth2RS.jwt(Customizer.withDefaults()))
                .build();
    }

    // Eliminação de prefixos nas roles geradas
    // automaticamente pelo spring
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    // Configura o prefixo 'SCOPE' no token JWT
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
         var authoritiesConverter = new JwtGrantedAuthoritiesConverter();
         authoritiesConverter.setAuthorityPrefix("");

         var converter = new JwtAuthenticationConverter();

         converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);

         return converter;
    }

}
