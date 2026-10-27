package br.senac.tads.dsw.produtos_ado.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration 
@EnableWebSecurity 
public class SecurityConfigurations {

    //Por ser http, irá operar em stateless, ou seja, não irá armazenar nada na memória
    @Bean //Mostra que esse método participa do SpringBoot
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(csrf -> csrf.disable())//obrigatório, cada autorização precisa ser autorizada com o token
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))//Necessário para permitir que o console H2 renderize a interface em frames
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//Obrigatório, como estamos usando uma api rest, precisa configurar para ser stateless
                .authorizeHttpRequests(authorize -> authorize
                                                    .requestMatchers(
                                                        "/swagger-ui/**",
                                                        "/swagger-ui.html",
                                                        "/v3/api-docs/**",
                                                        "/h2-console/**",
                                                        "/auth/register" //única rota da minha api
                                                    ).permitAll().anyRequest().authenticated() //permitir tudo nesses endpoints
                )           
                .build();
    }
    
    //Definir um tipo de criptografia para a senha
    @Bean 
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}