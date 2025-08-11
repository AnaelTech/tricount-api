package com.hb.cda.tricount_api.repository;

import com.hb.cda.tricount_api.entity.Expense;
import com.hb.cda.tricount_api.entity.ExpenseStatus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository pour la gestion des dépenses
 */
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, String> {

  /**
   * Trouve toutes les dépenses d'un groupe
   * 
   * @param groupId l'ID du groupe
   * @return la liste des dépenses
   */
  List<Expense> findByGroup_Id(String groupId);

  /**
   * Trouve toutes les dépenses d'un groupe payées par un utilisateur spécifique
   * 
   * @param groupId l'ID du groupe
   * @param payerId l'ID du payeur
   * @return la liste des dépenses
   */
  List<Expense> findByGroup_IdAndPayer_Id(String groupId, String payerId);

  /**
   * Trouve toutes les dépenses d'un groupe où un utilisateur est bénéficiaire
   * 
   * @param groupId l'ID du groupe
   * @param userId  l'ID de l'utilisateur bénéficiaire
   * @return la liste des dépenses
   */
  @Query("SELECT e FROM Expense e WHERE e.group.id = :groupId AND :userId MEMBER OF e.beneficiary")
  List<Expense> findByGroup_IdAndBeneficiaries_Id(@Param("groupId") String groupId, @Param("userId") String userId);

  /**
   * Trouve toutes les dépenses d'un groupe avec un montant supérieur à une valeur
   * 
   * @param groupId l'ID du groupe
   * @param amount  le montant minimum
   * @return la liste des dépenses
   */
  List<Expense> findByGroup_IdAndAmountGreaterThan(String groupId, Double amount);

  /**
   * Trouve toutes les dépenses d'un groupe avec un montant inférieur à une valeur
   * 
   * @param groupId l'ID du groupe
   * @param amount  le montant maximum
   * @return la liste des dépenses
   */
  List<Expense> findByGroup_IdAndAmountLessThan(String groupId, Double amount);

  /**
   * Trouve toutes les dépenses par statut
   * 
   * @param status le statut des dépenses
   * @return la liste des dépenses
   */
  List<Expense> findByStatus(ExpenseStatus status);

  /**
   * Trouve toutes les dépenses d'un utilisateur
   * 
   * @param userId l'ID de l'utilisateur
   * @return la liste des dépenses
   */
  @Query("SELECT e FROM Expense e WHERE e.payer.id = :userId")
  List<Expense> findByPayer_Id(String userId);

  /**
   * Trouve toutes les dépenses d'un groupe par statut
   * 
   * @param groupId l'ID du groupe
   * @param status  le statut des dépenses
   * @return la liste des dépenses
   */
  List<Expense> findByGroup_IdAndStatus(String groupId, ExpenseStatus status);

  /**
   * Calcule le total des dépenses d'un groupe
   * 
   * @param groupId l'ID du groupe
   * @return le montant total
   */
  @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.group.id = :groupId")
  Double sumAmountByGroupId(@Param("groupId") String groupId);

  /**
   * Calcule le total des dépenses payées par un utilisateur dans un groupe
   * 
   * @param groupId l'ID du groupe
   * @param payerId l'ID du payeur
   * @return le montant total payé
   */
  @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.group.id = :groupId AND e.payer.id = :payerId")
  Double sumAmountByGroupIdAndPayerId(@Param("groupId") String groupId, @Param("payerId") String payerId);
}
