package com.hb.cda.tricount_api.service.impl;

import com.hb.cda.tricount_api.dto.request.ExpenseRequestDto;
import com.hb.cda.tricount_api.dto.response.ExpenseResponseDto;
import com.hb.cda.tricount_api.entity.Expense;
import com.hb.cda.tricount_api.entity.ExpenseStatus;
import com.hb.cda.tricount_api.entity.Group;
import com.hb.cda.tricount_api.entity.User;
import com.hb.cda.tricount_api.mapper.ExpenseMapper;
import com.hb.cda.tricount_api.repository.ExpenseRepository;
import com.hb.cda.tricount_api.repository.GroupRepository;
import com.hb.cda.tricount_api.repository.UserRepository;
import com.hb.cda.tricount_api.service.ExpenseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implémentation du service pour la gestion des dépenses
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ExpenseServiceImpl implements ExpenseService {

  private final ExpenseRepository expenseRepository;
  private final UserRepository userRepository;
  private final GroupRepository groupRepository;
  private final ExpenseMapper expenseMapper;

  /**
   * Créer une nouvelle dépense
   * 
   * @param expenseRequestDto les données de la dépense à créer
   * @return la dépense créée
   */
  @Override
  public ExpenseResponseDto createExpense(ExpenseRequestDto expenseRequestDto) {
    log.info("Création d'une nouvelle dépense : {}", expenseRequestDto.getDescription());

    // Récupérer le payeur
    User payer = userRepository.findById(expenseRequestDto.getPayerId())
        .orElseThrow(() -> new RuntimeException("Payeur non trouvé avec l'ID : " + expenseRequestDto.getPayerId()));

    // Récupérer le groupe
    Group group = groupRepository.findById(expenseRequestDto.getGroupId())
        .orElseThrow(() -> new RuntimeException("Groupe non trouvé avec l'ID : " + expenseRequestDto.getGroupId()));

    // Vérifier que le payeur fait partie du groupe
    if (!group.getUsers().contains(payer)) {
      throw new RuntimeException("Le payeur ne fait pas partie du groupe");
    }

    // Récupérer les bénéficiaires
    List<User> beneficiaries = userRepository.findAllById(expenseRequestDto.getBeneficiaryIds());
    if (beneficiaries.size() != expenseRequestDto.getBeneficiaryIds().size()) {
      throw new RuntimeException("Certains bénéficiaires spécifiés n'existent pas");
    }

    // Vérifier que tous les bénéficiaires font partie du groupe
    for (User beneficiary : beneficiaries) {
      if (!group.getUsers().contains(beneficiary)) {
        throw new RuntimeException("Le bénéficiaire " + beneficiary.getName() + " ne fait pas partie du groupe");
      }
    }

    // Convertir le DTO en entité
    Expense expense = expenseMapper.toEntity(expenseRequestDto);
    expense.setPayer(payer);
    expense.setGroup(group);
    expense.setBeneficiary(beneficiaries);
    expense.setStatus(ExpenseStatus.PENDING);

    // Sauvegarder la dépense
    Expense savedExpense = expenseRepository.save(expense);

    log.info("Dépense créée avec succès avec l'ID : {}", savedExpense.getId());
    return expenseMapper.toDto(savedExpense);
  }

  /**
   * Récupérer toutes les dépenses
   * 
   * @return la liste de toutes les dépenses
   */
  @Override
  @Transactional(readOnly = true)
  public List<ExpenseResponseDto> getAllExpenses() {
    log.info("Récupération de toutes les dépenses");
    List<Expense> expenses = expenseRepository.findAll();
    return expenseMapper.toDtoList(expenses);
  }

  /**
   * Récupérer une dépense par son ID
   * 
   * @param expenseId l'ID de la dépense
   * @return la dépense trouvée
   */
  @Override
  @Transactional(readOnly = true)
  public ExpenseResponseDto getExpenseById(String expenseId) {
    log.info("Récupération de la dépense avec l'ID : {}", expenseId);

    Expense expense = expenseRepository.findById(expenseId)
        .orElseThrow(() -> new RuntimeException("Dépense non trouvée avec l'ID : " + expenseId));

    return expenseMapper.toDto(expense);
  }

  /**
   * Récupérer toutes les dépenses d'un groupe
   * 
   * @param groupId l'ID du groupe
   * @return la liste des dépenses du groupe
   */
  @Override
  @Transactional(readOnly = true)
  public List<ExpenseResponseDto> getAllExpensesByGroupId(String groupId) {
    log.info("Récupération des dépenses du groupe : {}", groupId);

    // Vérifier que le groupe existe
    if (!groupRepository.existsById(groupId)) {
      throw new RuntimeException("Groupe non trouvé avec l'ID : " + groupId);
    }

    List<Expense> expenses = expenseRepository.findByGroup_Id(groupId);
    return expenseMapper.toDtoList(expenses);
  }

  /**
   * Récupérer toutes les dépenses payées par un utilisateur
   * 
   * @param userId l'ID de l'utilisateur
   * @return la liste des dépenses payées par l'utilisateur
   */
  @Override
  @Transactional(readOnly = true)
  public List<ExpenseResponseDto> getAllExpensesByUserId(String userId) {
    log.info("Récupération des dépenses payées par l'utilisateur : {}", userId);

    // Vérifier que l'utilisateur existe
    if (!userRepository.existsById(userId)) {
      throw new RuntimeException("Utilisateur non trouvé avec l'ID : " + userId);
    }

    List<Expense> expenses = expenseRepository.findByPayer_Id(userId);
    return expenseMapper.toDtoList(expenses);
  }

  /**
   * Récupérer toutes les dépenses où un utilisateur est bénéficiaire
   * 
   * @param groupId l'ID du groupe
   * @param userId  l'ID de l'utilisateur
   * @return la liste des dépenses où l'utilisateur est bénéficiaire
   */
  @Override
  @Transactional(readOnly = true)
  public List<ExpenseResponseDto> getExpensesByGroupIdAndBeneficiaryId(String groupId, String userId) {
    log.info("Récupération des dépenses du groupe {} où l'utilisateur {} est bénéficiaire", groupId, userId);

    List<Expense> expenses = expenseRepository.findByGroup_IdAndBeneficiaries_Id(groupId, userId);
    return expenseMapper.toDtoList(expenses);
  }

  /**
   * Mettre à jour une dépense
   * 
   * @param expenseId         l'ID de la dépense à mettre à jour
   * @param expenseRequestDto les nouvelles données
   * @return la dépense mise à jour
   */
  @Override
  public ExpenseResponseDto updateExpenseById(String expenseId, ExpenseRequestDto expenseRequestDto) {
    log.info("Mise à jour de la dépense avec l'ID : {}", expenseId);

    Expense existingExpense = expenseRepository.findById(expenseId)
        .orElseThrow(() -> new RuntimeException("Dépense non trouvée avec l'ID : " + expenseId));

    // Mettre à jour les champs de base
    expenseMapper.updateEntity(expenseRequestDto, existingExpense);

    // Mettre à jour le payeur si nécessaire
    if (!existingExpense.getPayer().getId().equals(expenseRequestDto.getPayerId())) {
      User newPayer = userRepository.findById(expenseRequestDto.getPayerId())
          .orElseThrow(() -> new RuntimeException("Nouveau payeur non trouvé"));
      existingExpense.setPayer(newPayer);
    }

    // Mettre à jour les bénéficiaires si nécessaire
    List<User> newBeneficiaries = userRepository.findAllById(expenseRequestDto.getBeneficiaryIds());
    existingExpense.setBeneficiary(newBeneficiaries);

    Expense savedExpense = expenseRepository.save(existingExpense);

    log.info("Dépense mise à jour avec succès");
    return expenseMapper.toDto(savedExpense);
  }

  /**
   * Supprimer une dépense
   * 
   * @param expenseId l'ID de la dépense à supprimer
   */
  @Override
  public void deleteExpenseById(String expenseId) {
    log.info("Suppression de la dépense avec l'ID : {}", expenseId);

    if (!expenseRepository.existsById(expenseId)) {
      throw new RuntimeException("Dépense non trouvée avec l'ID : " + expenseId);
    }

    expenseRepository.deleteById(expenseId);
    log.info("Dépense supprimée avec succès");
  }

  /**
   * Changer le statut d'une dépense
   * 
   * @param expenseId l'ID de la dépense
   * @param status    le nouveau statut
   * @return la dépense mise à jour
   */
  @Override
  public ExpenseResponseDto updateExpenseStatus(String expenseId, ExpenseStatus status) {
    log.info("Changement du statut de la dépense {} à : {}", expenseId, status);

    Expense expense = expenseRepository.findById(expenseId)
        .orElseThrow(() -> new RuntimeException("Dépense non trouvée avec l'ID : " + expenseId));

    expense.setStatus(status);
    Expense savedExpense = expenseRepository.save(expense);

    return expenseMapper.toDto(savedExpense);
  }

  /**
   * Récupérer les dépenses par statut
   * 
   * @param status le statut des dépenses
   * @return la liste des dépenses avec ce statut
   */
  @Override
  @Transactional(readOnly = true)
  public List<ExpenseResponseDto> getExpensesByStatus(ExpenseStatus status) {
    log.info("Récupération des dépenses avec le statut : {}", status);

    List<Expense> expenses = expenseRepository.findByStatus(status);
    return expenseMapper.toDtoList(expenses);
  }
}
