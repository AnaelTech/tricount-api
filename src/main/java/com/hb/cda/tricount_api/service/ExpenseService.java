package com.hb.cda.tricount_api.service;

import java.util.List;

import com.hb.cda.tricount_api.dto.request.ExpenseRequestDto;
import com.hb.cda.tricount_api.dto.response.ExpenseResponseDto;
import com.hb.cda.tricount_api.entity.ExpenseStatus;

/**
 * Service interface pour la gestion des dépenses
 */
public interface ExpenseService {

    /**
     * Créer une nouvelle dépense
     * @param expenseRequestDto les données de la dépense à créer
     * @return la dépense créée
     */
    ExpenseResponseDto createExpense(ExpenseRequestDto expenseRequestDto);

    /**
     * Récupérer toutes les dépenses
     * @return la liste de toutes les dépenses
     */
    List<ExpenseResponseDto> getAllExpenses();

    /**
     * Récupérer une dépense par son ID
     * @param expenseId l'ID de la dépense
     * @return la dépense trouvée
     */
    ExpenseResponseDto getExpenseById(String expenseId);

    /**
     * Récupérer toutes les dépenses d'un groupe
     * @param groupId l'ID du groupe
     * @return la liste des dépenses du groupe
     */
    List<ExpenseResponseDto> getAllExpensesByGroupId(String groupId);

    /**
     * Récupérer toutes les dépenses payées par un utilisateur
     * @param userId l'ID de l'utilisateur
     * @return la liste des dépenses payées par l'utilisateur
     */
    List<ExpenseResponseDto> getAllExpensesByUserId(String userId);

    /**
     * Récupérer toutes les dépenses où un utilisateur est bénéficiaire
     * @param groupId l'ID du groupe
     * @param userId l'ID de l'utilisateur
     * @return la liste des dépenses où l'utilisateur est bénéficiaire
     */
    List<ExpenseResponseDto> getExpensesByGroupIdAndBeneficiaryId(String groupId, String userId);

    /**
     * Mettre à jour une dépense
     * @param expenseId l'ID de la dépense à mettre à jour
     * @param expenseRequestDto les nouvelles données
     * @return la dépense mise à jour
     */
    ExpenseResponseDto updateExpenseById(String expenseId, ExpenseRequestDto expenseRequestDto);

    /**
     * Supprimer une dépense
     * @param expenseId l'ID de la dépense à supprimer
     */
    void deleteExpenseById(String expenseId);

    /**
     * Changer le statut d'une dépense
     * @param expenseId l'ID de la dépense
     * @param status le nouveau statut
     * @return la dépense mise à jour
     */
    ExpenseResponseDto updateExpenseStatus(String expenseId, ExpenseStatus status);

    /**
     * Récupérer les dépenses par statut
     * @param status le statut des dépenses
     * @return la liste des dépenses avec ce statut
     */
    List<ExpenseResponseDto> getExpensesByStatus(ExpenseStatus status);
}
