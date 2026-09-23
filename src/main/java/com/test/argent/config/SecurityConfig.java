package com.test.argent.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private static final String[] FRONTEND_PATHS = {"/", "/index.html", "/assets/**", "/favicon.ico"};
    private static final String API_PATHS = "/api/**";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(FRONTEND_PATHS).permitAll()
                        // Temporaire : API ouverte tant que l'authentification (JWT) n'est pas en place
                        .requestMatchers(API_PATHS).permitAll()
                        .anyRequest().denyAll())
                // API REST stateless : pas de session, donc pas de CSRF
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .build();
    }
}
