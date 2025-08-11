package com.hb.cda.tricount_api.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * DTO de requête pour créer ou modifier un règlement
 */
@Data
public class SettlementRequestDto {
    
    // Commentaire du règlement
    @Size(max = 255, message = "Le commentaire ne peut pas dépasser 255 caractères")
    private String comment;

    // Montant du règlement (obligatoire et positif)
    @NotNull(message = "Le montant est obligatoire")
    @Positive(message = "Le montant doit être positif")
    private Double amount;

    // ID de l'utilisateur débiteur (obligatoire)
    @NotBlank(message = "L'ID du débiteur est obligatoire")
    private String debtorId;

    // ID de l'utilisateur créditeur (obligatoire)
    @NotBlank(message = "L'ID du créditeur est obligatoire")
    private String creditorId;

    // ID du groupe (obligatoire)
    @NotBlank(message = "L'ID du groupe est obligatoire")
    private String groupId;
}
