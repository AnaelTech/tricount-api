package com.hb.cda.tricount_api.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * DTO de requête pour créer ou modifier une dépense
 */
@Data
public class ExpenseRequestDto {
    
    // Description de la dépense (obligatoire)
    @NotBlank(message = "La description est obligatoire")
    @Size(max = 255, message = "La description ne peut pas dépasser 255 caractères")
    private String description;

    // Montant de la dépense (obligatoire et positif)
    @NotNull(message = "Le montant est obligatoire")
    @Positive(message = "Le montant doit être positif")
    private Double amount;

    // ID de l'utilisateur qui a payé (obligatoire)
    @NotBlank(message = "L'ID du payeur est obligatoire")
    private String payerId;

    // Liste des IDs des bénéficiaires
    @NotNull(message = "La liste des bénéficiaires est obligatoire")
    @Size(min = 1, message = "Il doit y avoir au moins un bénéficiaire")
    private List<String> beneficiaryIds;

    // ID du groupe (obligatoire)
    @NotBlank(message = "L'ID du groupe est obligatoire")
    private String groupId;
}
