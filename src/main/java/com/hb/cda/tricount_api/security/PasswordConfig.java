package com.hb.cda.tricount_api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration de sécurité pour l'encodage des mots de passe
 */
@Configuration
public class PasswordConfig {

    /**
     * Bean pour l'encodage des mots de passe
     * @return l'encoder BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
