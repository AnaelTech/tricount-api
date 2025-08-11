package com.hb.cda.tricount_api.service.impl;

import com.hb.cda.tricount_api.dto.request.GroupRequestDto;
import com.hb.cda.tricount_api.dto.response.GroupResponseDto;
import com.hb.cda.tricount_api.entity.Group;
import com.hb.cda.tricount_api.entity.User;
import com.hb.cda.tricount_api.mapper.GroupMapper;
import com.hb.cda.tricount_api.repository.GroupRepository;
import com.hb.cda.tricount_api.repository.UserRepository;
import com.hb.cda.tricount_api.service.GroupService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation du service pour la gestion des groupes
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class GroupServiceImpl implements GroupService {

  private final GroupRepository groupRepository;
  private final UserRepository userRepository;
  private final GroupMapper groupMapper;

  /**
   * Créer un nouveau groupe
   * 
   * @param groupRequestDto les données du groupe à créer
   * @return le groupe créé
   */
  @Override
  public GroupResponseDto createGroup(GroupRequestDto groupRequestDto) {
    log.info("Création d'un nouveau groupe : {}", groupRequestDto.getName());

    // Vérifier si le nom existe déjà
    if (groupRepository.findByName(groupRequestDto.getName()).isPresent()) {
      throw new RuntimeException("Un groupe avec ce nom existe déjà");
    }

    // Convertir le DTO en entité
    Group group = groupMapper.toEntity(groupRequestDto);
    group.setUsers(new ArrayList<>());

    // Ajouter les utilisateurs si spécifiés
    if (groupRequestDto.getUserIds() != null && !groupRequestDto.getUserIds().isEmpty()) {
      List<User> users = userRepository.findAllById(groupRequestDto.getUserIds());
      if (users.size() != groupRequestDto.getUserIds().size()) {
        throw new RuntimeException("Certains utilisateurs spécifiés n'existent pas");
      }
      group.setUsers(users);
    }

    // Sauvegarder le groupe
    Group savedGroup = groupRepository.save(group);

    log.info("Groupe créé avec succès avec l'ID : {}", savedGroup.getId());
    return groupMapper.toDto(savedGroup);
  }

  /**
   * Récupérer tous les groupes
   * 
   * @return la liste de tous les groupes
   */
  @Override
  @Transactional(readOnly = true)
  public List<GroupResponseDto> getAllGroups() {
    log.info("Récupération de tous les groupes");
    List<Group> groups = groupRepository.findAll();
    return groupMapper.toDtoList(groups);
  }

  /**
   * Récupérer un groupe par son ID
   * 
   * @param id l'ID du groupe
   * @return le groupe trouvé
   */
  @Override
  @Transactional(readOnly = true)
  public GroupResponseDto getGroupById(String id) {
    log.info("Récupération du groupe avec l'ID : {}", id);

    Group group = groupRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Groupe non trouvé avec l'ID : " + id));

    return groupMapper.toDto(group);
  }

  /**
   * Mettre à jour un groupe
   * 
   * @param id              l'ID du groupe à mettre à jour
   * @param groupRequestDto les nouvelles données
   * @return le groupe mis à jour
   */
  @Override
  public GroupResponseDto updateGroup(String id, GroupRequestDto groupRequestDto) {
    log.info("Mise à jour du groupe avec l'ID : {}", id);

    Group existingGroup = groupRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Groupe non trouvé avec l'ID : " + id));

    // Vérifier si le nouveau nom n'est pas déjà utilisé par un autre groupe
    if (!existingGroup.getName().equals(groupRequestDto.getName())) {
      groupRepository.findByName(groupRequestDto.getName())
          .ifPresent(g -> {
            throw new RuntimeException("Un autre groupe utilise déjà ce nom");
          });
    }

    // Mettre à jour les champs
    groupMapper.updateEntity(groupRequestDto, existingGroup);

    Group savedGroup = groupRepository.save(existingGroup);

    log.info("Groupe mis à jour avec succès");
    return groupMapper.toDto(savedGroup);
  }

  /**
   * Supprimer un groupe
   * 
   * @param id l'ID du groupe à supprimer
   */
  @Override
  public void deleteGroup(String id) {
    log.info("Suppression du groupe avec l'ID : {}", id);

    if (!groupRepository.existsById(id)) {
      throw new RuntimeException("Groupe non trouvé avec l'ID : " + id);
    }

    groupRepository.deleteById(id);
    log.info("Groupe supprimé avec succès");
  }

  /**
   * Ajouter un utilisateur à un groupe
   * 
   * @param groupId l'ID du groupe
   * @param userId  l'ID de l'utilisateur
   * @return le groupe mis à jour
   */
  @Override
  public GroupResponseDto addUserToGroup(String groupId, String userId) {
    log.info("Ajout de l'utilisateur {} au groupe {}", userId, groupId);

    Group group = groupRepository.findById(groupId)
        .orElseThrow(() -> new RuntimeException("Groupe non trouvé avec l'ID : " + groupId));

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + userId));

    // Vérifier si l'utilisateur n'est pas déjà dans le groupe
    if (group.getUsers().contains(user)) {
      throw new RuntimeException("L'utilisateur fait déjà partie du groupe");
    }

    group.getUsers().add(user);
    Group savedGroup = groupRepository.save(group);

    log.info("Utilisateur ajouté au groupe avec succès");
    return groupMapper.toDto(savedGroup);
  }

  /**
   * Retirer un utilisateur d'un groupe
   * 
   * @param groupId l'ID du groupe
   * @param userId  l'ID de l'utilisateur
   * @return le groupe mis à jour
   */
  @Override
  public GroupResponseDto removeUserFromGroup(String groupId, String userId) {
    log.info("Suppression de l'utilisateur {} du groupe {}", userId, groupId);

    Group group = groupRepository.findById(groupId)
        .orElseThrow(() -> new RuntimeException("Groupe non trouvé avec l'ID : " + groupId));

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + userId));

    // Vérifier si l'utilisateur est dans le groupe
    if (!group.getUsers().contains(user)) {
      throw new RuntimeException("L'utilisateur ne fait pas partie du groupe");
    }

    group.getUsers().remove(user);
    Group savedGroup = groupRepository.save(group);

    log.info("Utilisateur retiré du groupe avec succès");
    return groupMapper.toDto(savedGroup);
  }

  /**
   * Récupérer les groupes d'un utilisateur
   * 
   * @param userId l'ID de l'utilisateur
   * @return la liste des groupes de l'utilisateur
   */
  @Override
  @Transactional(readOnly = true)
  public List<GroupResponseDto> getGroupsByUserId(String userId) {
    log.info("Récupération des groupes de l'utilisateur : {}", userId);

    // Vérifier que l'utilisateur existe
    if (!userRepository.existsById(userId)) {
      throw new RuntimeException("Utilisateur non trouvé avec l'ID : " + userId);
    }

    List<Group> groups = groupRepository.findByUsers_Id(userId);
    return groupMapper.toDtoList(groups);
  }
}
