package br.senac.tads.dsw.produtos_ado.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desativa a proteção CSRF para permitir POST, PUT e DELETE no Swagger/Postman
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin())) // Permite o uso de frames para o H2 Console
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/h2-console/**").permitAll() // Libera o Swagger
                .anyRequest().permitAll() // Libera todas as rotas da API para testes locais sem login
            );
            
        return http.build();
    }
}