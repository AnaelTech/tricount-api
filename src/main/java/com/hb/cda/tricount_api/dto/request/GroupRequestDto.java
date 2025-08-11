package com.hb.cda.tricount_api.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * DTO de requête pour créer ou modifier un groupe
 */
@Data
public class GroupRequestDto {
    
    // Nom du groupe (obligatoire)
    @NotBlank(message = "Le nom du groupe est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom du groupe doit contenir entre 2 et 50 caractères")
    private String name;

    // Liste des IDs des utilisateurs à ajouter au groupe
    private List<String> userIds;
}
