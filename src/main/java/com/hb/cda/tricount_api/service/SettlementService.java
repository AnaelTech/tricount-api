package com.hb.cda.tricount_api.service;

import com.hb.cda.tricount_api.dto.request.SettlementRequestDto;
import com.hb.cda.tricount_api.dto.response.SettlementsResponseDto;

import java.util.List;

/**
 * Service interface pour la gestion des règlements
 */
public interface SettlementService {
    
    /**
     * Créer un nouveau règlement
     * @param settlementRequestDto les données du règlement à créer
     * @return le règlement créé
     */
    SettlementsResponseDto createSettlement(SettlementRequestDto settlementRequestDto);
    
    /**
     * Récupérer tous les règlements
     * @return la liste de tous les règlements
     */
    List<SettlementsResponseDto> getAllSettlements();
    
    /**
     * Récupérer un règlement par son ID
     * @param id l'ID du règlement
     * @return le règlement trouvé
     */
    SettlementsResponseDto getSettlementById(String id);
    
    /**
     * Récupérer tous les règlements d'un groupe
     * @param groupId l'ID du groupe
     * @return la liste des règlements du groupe
     */
    List<SettlementsResponseDto> getSettlementsByGroupId(String groupId);
    
    /**
     * Récupérer tous les règlements d'un utilisateur (créditeur ou débiteur)
     * @param userId l'ID de l'utilisateur
     * @return la liste des règlements de l'utilisateur
     */
    List<SettlementsResponseDto> getSettlementsByUserId(String userId);
    
    /**
     * Calculer les règlements pour un groupe donné
     * Cette méthode calcule qui doit combien à qui dans un groupe
     * @param groupId l'ID du groupe
     * @return la liste des règlements calculés
     */
    List<SettlementsResponseDto> calculateSettlementsForGroup(String groupId);
    
    /**
     * Supprimer un règlement
     * @param id l'ID du règlement à supprimer
     */
    void deleteSettlement(String id);
    
    /**
     * Supprimer tous les règlements d'un groupe
     * @param groupId l'ID du groupe
     */
    void deleteAllSettlementsByGroupId(String groupId);
    
    /**
     * Récupérer le solde d'un utilisateur (total créances - total dettes)
     * @param userId l'ID de l'utilisateur
     * @return le solde de l'utilisateur
     */
    Double getUserBalance(String userId);
}
