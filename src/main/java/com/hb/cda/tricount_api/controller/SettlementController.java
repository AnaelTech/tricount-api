package com.hb.cda.tricount_api.controller;

import com.hb.cda.tricount_api.dto.request.SettlementRequestDto;
import com.hb.cda.tricount_api.dto.response.SettlementsResponseDto;
import com.hb.cda.tricount_api.service.SettlementService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Contrôleur REST pour la gestion des règlements
 */
@RestController
@RequestMapping("/api/settlements")
@RequiredArgsConstructor
@Slf4j
@Validated
public class SettlementController {

    private final SettlementService settlementService;

    /**
     * Créer un nouveau règlement
     * POST /api/settlements
     */
    @PostMapping
    public ResponseEntity<SettlementsResponseDto> createSettlement(@Valid @RequestBody SettlementRequestDto settlementRequestDto) {
        log.info("Requête de création de règlement reçue");
        
        SettlementsResponseDto createdSettlement = settlementService.createSettlement(settlementRequestDto);
        return new ResponseEntity<>(createdSettlement, HttpStatus.CREATED);
    }

    /**
     * Récupérer tous les règlements
     * GET /api/settlements
     */
    @GetMapping
    public ResponseEntity<List<SettlementsResponseDto>> getAllSettlements() {
        log.info("Requête de récupération de tous les règlements");
        
        List<SettlementsResponseDto> settlements = settlementService.getAllSettlements();
        return ResponseEntity.ok(settlements);
    }

    /**
     * Récupérer un règlement par son ID
     * GET /api/settlements/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<SettlementsResponseDto> getSettlementById(@PathVariable String id) {
        log.info("Requête de récupération du règlement avec l'ID : {}", id);
        
        SettlementsResponseDto settlement = settlementService.getSettlementById(id);
        return ResponseEntity.ok(settlement);
    }

    /**
     * Récupérer tous les règlements d'un groupe
     * GET /api/settlements/group/{groupId}
     */
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<SettlementsResponseDto>> getSettlementsByGroupId(@PathVariable String groupId) {
        log.info("Requête de récupération des règlements du groupe : {}", groupId);
        
        List<SettlementsResponseDto> settlements = settlementService.getSettlementsByGroupId(groupId);
        return ResponseEntity.ok(settlements);
    }

    /**
     * Récupérer tous les règlements d'un utilisateur
     * GET /api/settlements/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SettlementsResponseDto>> getSettlementsByUserId(@PathVariable String userId) {
        log.info("Requête de récupération des règlements de l'utilisateur : {}", userId);
        
        List<SettlementsResponseDto> settlements = settlementService.getSettlementsByUserId(userId);
        return ResponseEntity.ok(settlements);
    }

    /**
     * Calculer les règlements pour un groupe
     * POST /api/settlements/calculate/{groupId}
     */
    @PostMapping("/calculate/{groupId}")
    public ResponseEntity<List<SettlementsResponseDto>> calculateSettlementsForGroup(@PathVariable String groupId) {
        log.info("Requête de calcul des règlements pour le groupe : {}", groupId);
        
        List<SettlementsResponseDto> settlements = settlementService.calculateSettlementsForGroup(groupId);
        return ResponseEntity.ok(settlements);
    }

    /**
     * Récupérer le solde d'un utilisateur
     * GET /api/settlements/user/{userId}/balance
     */
    @GetMapping("/user/{userId}/balance")
    public ResponseEntity<Double> getUserBalance(@PathVariable String userId) {
        log.info("Requête de récupération du solde de l'utilisateur : {}", userId);
        
        Double balance = settlementService.getUserBalance(userId);
        return ResponseEntity.ok(balance);
    }

    /**
     * Supprimer un règlement
     * DELETE /api/settlements/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSettlement(@PathVariable String id) {
        log.info("Requête de suppression du règlement avec l'ID : {}", id);
        
        settlementService.deleteSettlement(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Supprimer tous les règlements d'un groupe
     * DELETE /api/settlements/group/{groupId}
     */
    @DeleteMapping("/group/{groupId}")
    public ResponseEntity<Void> deleteAllSettlementsByGroupId(@PathVariable String groupId) {
        log.info("Requête de suppression de tous les règlements du groupe : {}", groupId);
        
        settlementService.deleteAllSettlementsByGroupId(groupId);
        return ResponseEntity.noContent().build();
    }
}
