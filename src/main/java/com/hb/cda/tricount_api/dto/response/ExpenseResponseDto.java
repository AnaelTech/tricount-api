package com.hb.cda.tricount_api.dto.response;

import lombok.Data;
import java.util.List;
import com.hb.cda.tricount_api.entity.ExpenseStatus;

/**
 * DTO de réponse pour les dépenses
 * Contient toutes les informations d'une dépense
 */
@Data
public class ExpenseResponseDto {
    // ID unique de la dépense
    private String id;

    // Description de la dépense
    private String description;

    // Montant de la dépense
    private Double amount;

    // Statut de la dépense
    private ExpenseStatus status;

    // Utilisateur qui a payé la dépense
    private UserResponseDto payer;

    // Liste des bénéficiaires de la dépense
    private List<UserResponseDto> beneficiaries;

    // Groupe auquel appartient la dépense
    private String groupId;

    // Nom du groupe
    private String groupName;

    // Montant par bénéficiaire
    private Double amountPerBeneficiary;
}
