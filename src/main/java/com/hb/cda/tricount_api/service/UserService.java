package com.hb.cda.tricount_api.service;

import com.hb.cda.tricount_api.dto.request.UserRequestDto;
import com.hb.cda.tricount_api.dto.response.UserResponseDto;

import java.util.List;

/**
 * Service interface pour la gestion des utilisateurs
 */
public interface UserService {
    
    /**
     * Créer un nouvel utilisateur
     * @param userRequestDto les données de l'utilisateur à créer
     * @return l'utilisateur créé
     */
    UserResponseDto createUser(UserRequestDto userRequestDto);
    
    /**
     * Récupérer tous les utilisateurs
     * @return la liste de tous les utilisateurs
     */
    List<UserResponseDto> getAllUsers();
    
    /**
     * Récupérer un utilisateur par son ID
     * @param id l'ID de l'utilisateur
     * @return l'utilisateur trouvé
     */
    UserResponseDto getUserById(String id);
    
    /**
     * Récupérer un utilisateur par son email
     * @param email l'email de l'utilisateur
     * @return l'utilisateur trouvé
     */
    UserResponseDto getUserByEmail(String email);
    
    /**
     * Mettre à jour un utilisateur
     * @param id l'ID de l'utilisateur à mettre à jour
     * @param userRequestDto les nouvelles données
     * @return l'utilisateur mis à jour
     */
    UserResponseDto updateUser(String id, UserRequestDto userRequestDto);
    
    /**
     * Supprimer un utilisateur
     * @param id l'ID de l'utilisateur à supprimer
     */
    void deleteUser(String id);
    
    /**
     * Activer/désactiver un utilisateur
     * @param id l'ID de l'utilisateur
     * @param active le nouveau statut
     * @return l'utilisateur mis à jour
     */
    UserResponseDto setUserActive(String id, boolean active);
    
    /**
     * Récupérer les utilisateurs d'un groupe
     * @param groupId l'ID du groupe
     * @return la liste des utilisateurs du groupe
     */
    List<UserResponseDto> getUsersByGroupId(String groupId);
}
