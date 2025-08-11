package com.hb.cda.tricount_api.repository;

import com.hb.cda.tricount_api.entity.Settlement;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository pour la gestion des règlements
 */
@Repository
public interface SettlementRepository extends JpaRepository<Settlement, String> {
    
    /**
     * Trouve tous les règlements d'un groupe
     * @param groupId l'ID du groupe
     * @return la liste des règlements
     */
    List<Settlement> findByGroup_Id(String groupId);

    /**
     * Trouve tous les règlements où un utilisateur est débiteur
     * @param userId l'ID de l'utilisateur débiteur
     * @return la liste des règlements
     */
    List<Settlement> findByDebtor_Id(String userId);

    /**
     * Trouve tous les règlements où un utilisateur est créditeur
     * @param userId l'ID de l'utilisateur créditeur
     * @return la liste des règlements
     */
    List<Settlement> findByCreditor_Id(String userId);
    
    /**
     * Trouve tous les règlements d'un utilisateur (débiteur ou créditeur)
     * @param userId l'ID de l'utilisateur
     * @return la liste des règlements
     */
    @Query("SELECT s FROM Settlement s WHERE s.debtor.id = :userId OR s.creditor.id = :userId")
    List<Settlement> findByUserId(@Param("userId") String userId);
    
    /**
     * Trouve un règlement spécifique entre deux utilisateurs dans un groupe
     * @param groupId l'ID du groupe
     * @param debtorId l'ID du débiteur
     * @param creditorId l'ID du créditeur
     * @return le règlement optionnel
     */
    Optional<Settlement> findByGroup_IdAndDebtor_IdAndCreditor_Id(String groupId, String debtorId, String creditorId);
    
    /**
     * Supprime tous les règlements d'un groupe
     * @param groupId l'ID du groupe
     */
    void deleteByGroup_Id(String groupId);
    
    /**
     * Calcule le total des dettes d'un utilisateur
     * @param userId l'ID de l'utilisateur
     * @return le montant total des dettes
     */
    @Query("SELECT SUM(s.amount) FROM Settlement s WHERE s.debtor.id = :userId")
    Double sumDebtsByUserId(@Param("userId") String userId);
    
    /**
     * Calcule le total des créances d'un utilisateur
     * @param userId l'ID de l'utilisateur
     * @return le montant total des créances
     */
    @Query("SELECT SUM(s.amount) FROM Settlement s WHERE s.creditor.id = :userId")
    Double sumCreditsByUserId(@Param("userId") String userId);
}
