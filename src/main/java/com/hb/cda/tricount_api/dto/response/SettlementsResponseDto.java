package com.hb.cda.tricount_api.dto.response;

import lombok.Data;

/**
 * DTO de réponse pour les règlements
 * Contient les informations d'un règlement entre utilisateurs
 */
@Data
public class SettlementsResponseDto {
    // ID unique du règlement
    private String id;

    // Montant du règlement
    private Double amount;

    // Utilisateur qui doit de l'argent
    private UserResponseDto debtor;

    // Utilisateur qui doit recevoir l'argent
    private UserResponseDto creditor;

    // ID du groupe concerné
    private String groupId;

    // Nom du groupe concerné
    private String groupName;

    // Commentaire du règlement
    private String comment;

    // Statut du règlement (calculé, confirmé, payé)
    private String status;
}
