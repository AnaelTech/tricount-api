package com.hb.cda.tricount_api.service;

import com.hb.cda.tricount_api.dto.request.GroupRequestDto;
import com.hb.cda.tricount_api.dto.response.GroupResponseDto;

import java.util.List;

/**
 * Service interface pour la gestion des groupes
 */
public interface GroupService {
    
    /**
     * Créer un nouveau groupe
     * @param groupRequestDto les données du groupe à créer
     * @return le groupe créé
     */
    GroupResponseDto createGroup(GroupRequestDto groupRequestDto);
    
    /**
     * Récupérer tous les groupes
     * @return la liste de tous les groupes
     */
    List<GroupResponseDto> getAllGroups();
    
    /**
     * Récupérer un groupe par son ID
     * @param id l'ID du groupe
     * @return le groupe trouvé
     */
    GroupResponseDto getGroupById(String id);
    
    /**
     * Mettre à jour un groupe
     * @param id l'ID du groupe à mettre à jour
     * @param groupRequestDto les nouvelles données
     * @return le groupe mis à jour
     */
    GroupResponseDto updateGroup(String id, GroupRequestDto groupRequestDto);
    
    /**
     * Supprimer un groupe
     * @param id l'ID du groupe à supprimer
     */
    void deleteGroup(String id);
    
    /**
     * Ajouter un utilisateur à un groupe
     * @param groupId l'ID du groupe
     * @param userId l'ID de l'utilisateur
     * @return le groupe mis à jour
     */
    GroupResponseDto addUserToGroup(String groupId, String userId);
    
    /**
     * Retirer un utilisateur d'un groupe
     * @param groupId l'ID du groupe
     * @param userId l'ID de l'utilisateur
     * @return le groupe mis à jour
     */
    GroupResponseDto removeUserFromGroup(String groupId, String userId);
    
    /**
     * Récupérer les groupes d'un utilisateur
     * @param userId l'ID de l'utilisateur
     * @return la liste des groupes de l'utilisateur
     */
    List<GroupResponseDto> getGroupsByUserId(String userId);
}
