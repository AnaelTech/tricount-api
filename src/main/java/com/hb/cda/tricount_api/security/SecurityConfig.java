package com.hb.cda.tricount_api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration de sécurité pour l'API
 * Configuration simple permettant l'accès à toutes les routes (pour le développement)
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configuration de la chaîne de filtres de sécurité
     * @param http l'objet HttpSecurity à configurer
     * @return la chaîne de filtres configurée
     * @throws Exception en cas d'erreur de configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Désactiver CSRF pour les API REST
            .csrf(csrf -> csrf.disable())
            
            // Permettre l'accès à toutes les routes (développement uniquement)
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/**").permitAll()
                .requestMatchers("/actuator/**").permitAll()
                .anyRequest().authenticated()
            )
            
            // Désactiver la protection contre le clickjacking pour le développement
            .headers(headers -> headers
                .frameOptions().disable()
            );

        return http.build();
    }
}
