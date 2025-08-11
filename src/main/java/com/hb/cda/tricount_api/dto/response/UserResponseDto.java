package com.hb.cda.tricount_api.dto.response;

import lombok.Data;

/**
 * DTO de réponse pour les utilisateurs
 * Contient les informations publiques d'un utilisateur
 */
@Data
public class UserResponseDto {
    // ID unique de l'utilisateur
    private String id;

    // Nom de l'utilisateur
    private String name;

    // Adresse email de l'utilisateur
    private String email;

    // Statut actif/inactif de l'utilisateur
    private Boolean active;
}
