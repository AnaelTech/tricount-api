package com.hb.cda.tricount_api.dto.response;

import lombok.Data;
import java.util.List;

/**
 * DTO de réponse pour les groupes
 * Contient les informations d'un groupe avec ses utilisateurs et dépenses
 */
@Data
public class GroupResponseDto {
    // ID unique du groupe
    private String id;

    // Nom du groupe
    private String name;

    // Liste des utilisateurs du groupe
    private List<UserResponseDto> users;

    // Liste des dépenses du groupe
    private List<ExpenseResponseDto> expenses;

    // Total des dépenses du groupe
    private Double totalExpenses;

    // Nombre de membres du groupe
    private Integer membersCount;
}
