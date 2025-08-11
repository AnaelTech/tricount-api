package com.hb.cda.tricount_api.repository;

import com.hb.cda.tricount_api.entity.Group;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository pour la gestion des groupes
 */
@Repository
public interface GroupRepository extends JpaRepository<Group, String> {

    /**
     * Trouve tous les groupes contenant un utilisateur spécifique
     * @param userId l'ID de l'utilisateur
     * @return la liste des groupes
     */
    List<Group> findByUsers_Id(String userId);

    /**
     * Trouve un groupe par son nom
     * @param name le nom du groupe
     * @return le groupe optionnel
     */
    Optional<Group> findByName(String name);
    
    /**
     * Trouve tous les groupes contenant un utilisateur avec leurs dépenses
     * @param userId l'ID de l'utilisateur
     * @return la liste des groupes avec leurs dépenses
     */
    @Query("SELECT DISTINCT g FROM Group g LEFT JOIN FETCH g.expenses LEFT JOIN FETCH g.users WHERE :userId MEMBER OF g.users")
    List<Group> findByUsersIdWithExpenses(@Param("userId") String userId);
    
    /**
     * Vérifie si un utilisateur appartient à un groupe
     * @param groupId l'ID du groupe
     * @param userId l'ID de l'utilisateur
     * @return true si l'utilisateur appartient au groupe
     */
    @Query("SELECT COUNT(g) > 0 FROM Group g WHERE g.id = :groupId AND :userId MEMBER OF g.users")
    boolean existsByIdAndUsersId(@Param("groupId") String groupId, @Param("userId") String userId);
}
