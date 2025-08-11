package com.hb.cda.tricount_api.controller;

import com.hb.cda.tricount_api.dto.request.ExpenseRequestDto;
import com.hb.cda.tricount_api.dto.response.ExpenseResponseDto;
import com.hb.cda.tricount_api.entity.ExpenseStatus;
import com.hb.cda.tricount_api.service.ExpenseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Contrôleur REST pour la gestion des dépenses
 */
@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ExpenseController {

    private final ExpenseService expenseService;

    /**
     * Créer une nouvelle dépense
     * POST /api/expenses
     */
    @PostMapping
    public ResponseEntity<ExpenseResponseDto> createExpense(@Valid @RequestBody ExpenseRequestDto expenseRequestDto) {
        log.info("Requête de création de dépense reçue : {}", expenseRequestDto.getDescription());
        
        ExpenseResponseDto createdExpense = expenseService.createExpense(expenseRequestDto);
        return new ResponseEntity<>(createdExpense, HttpStatus.CREATED);
    }

    /**
     * Récupérer toutes les dépenses
     * GET /api/expenses
     */
    @GetMapping
    public ResponseEntity<List<ExpenseResponseDto>> getAllExpenses() {
        log.info("Requête de récupération de toutes les dépenses");
        
        List<ExpenseResponseDto> expenses = expenseService.getAllExpenses();
        return ResponseEntity.ok(expenses);
    }

    /**
     * Récupérer une dépense par son ID
     * GET /api/expenses/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDto> getExpenseById(@PathVariable String id) {
        log.info("Requête de récupération de la dépense avec l'ID : {}", id);
        
        ExpenseResponseDto expense = expenseService.getExpenseById(id);
        return ResponseEntity.ok(expense);
    }

    /**
     * Récupérer toutes les dépenses d'un groupe
     * GET /api/expenses/group/{groupId}
     */
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<ExpenseResponseDto>> getExpensesByGroupId(@PathVariable String groupId) {
        log.info("Requête de récupération des dépenses du groupe : {}", groupId);
        
        List<ExpenseResponseDto> expenses = expenseService.getAllExpensesByGroupId(groupId);
        return ResponseEntity.ok(expenses);
    }

    /**
     * Récupérer toutes les dépenses payées par un utilisateur
     * GET /api/expenses/payer/{userId}
     */
    @GetMapping("/payer/{userId}")
    public ResponseEntity<List<ExpenseResponseDto>> getExpensesByPayerId(@PathVariable String userId) {
        log.info("Requête de récupération des dépenses payées par l'utilisateur : {}", userId);
        
        List<ExpenseResponseDto> expenses = expenseService.getAllExpensesByUserId(userId);
        return ResponseEntity.ok(expenses);
    }

    /**
     * Récupérer toutes les dépenses où un utilisateur est bénéficiaire
     * GET /api/expenses/group/{groupId}/beneficiary/{userId}
     */
    @GetMapping("/group/{groupId}/beneficiary/{userId}")
    public ResponseEntity<List<ExpenseResponseDto>> getExpensesByGroupIdAndBeneficiaryId(
            @PathVariable String groupId,
            @PathVariable String userId) {
        log.info("Requête de récupération des dépenses du groupe {} où l'utilisateur {} est bénéficiaire", 
                groupId, userId);
        
        List<ExpenseResponseDto> expenses = expenseService.getExpensesByGroupIdAndBeneficiaryId(groupId, userId);
        return ResponseEntity.ok(expenses);
    }

    /**
     * Récupérer les dépenses par statut
     * GET /api/expenses/status/{status}
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ExpenseResponseDto>> getExpensesByStatus(@PathVariable ExpenseStatus status) {
        log.info("Requête de récupération des dépenses avec le statut : {}", status);
        
        List<ExpenseResponseDto> expenses = expenseService.getExpensesByStatus(status);
        return ResponseEntity.ok(expenses);
    }

    /**
     * Mettre à jour une dépense
     * PUT /api/expenses/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponseDto> updateExpense(
            @PathVariable String id,
            @Valid @RequestBody ExpenseRequestDto expenseRequestDto) {
        log.info("Requête de mise à jour de la dépense avec l'ID : {}", id);
        
        ExpenseResponseDto updatedExpense = expenseService.updateExpenseById(id, expenseRequestDto);
        return ResponseEntity.ok(updatedExpense);
    }

    /**
     * Changer le statut d'une dépense
     * PATCH /api/expenses/{id}/status
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<ExpenseResponseDto> updateExpenseStatus(
            @PathVariable String id,
            @RequestParam ExpenseStatus status) {
        log.info("Requête de changement de statut de la dépense {} à : {}", id, status);
        
        ExpenseResponseDto updatedExpense = expenseService.updateExpenseStatus(id, status);
        return ResponseEntity.ok(updatedExpense);
    }

    /**
     * Supprimer une dépense
     * DELETE /api/expenses/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable String id) {
        log.info("Requête de suppression de la dépense avec l'ID : {}", id);
        
        expenseService.deleteExpenseById(id);
        return ResponseEntity.noContent().build();
    }
}
