package com.hb.cda.tricount_api.controller;

import com.hb.cda.tricount_api.dto.request.GroupRequestDto;
import com.hb.cda.tricount_api.dto.response.GroupResponseDto;
import com.hb.cda.tricount_api.service.GroupService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Contrôleur REST pour la gestion des groupes
 */
@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
@Slf4j
@Validated
public class GroupController {

    private final GroupService groupService;

    /**
     * Créer un nouveau groupe
     * POST /api/groups
     */
    @PostMapping
    public ResponseEntity<GroupResponseDto> createGroup(@Valid @RequestBody GroupRequestDto groupRequestDto) {
        log.info("Requête de création de groupe reçue pour le nom : {}", groupRequestDto.getName());
        
        GroupResponseDto createdGroup = groupService.createGroup(groupRequestDto);
        return new ResponseEntity<>(createdGroup, HttpStatus.CREATED);
    }

    /**
     * Récupérer tous les groupes
     * GET /api/groups
     */
    @GetMapping
    public ResponseEntity<List<GroupResponseDto>> getAllGroups() {
        log.info("Requête de récupération de tous les groupes");
        
        List<GroupResponseDto> groups = groupService.getAllGroups();
        return ResponseEntity.ok(groups);
    }

    /**
     * Récupérer un groupe par son ID
     * GET /api/groups/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<GroupResponseDto> getGroupById(@PathVariable String id) {
        log.info("Requête de récupération du groupe avec l'ID : {}", id);
        
        GroupResponseDto group = groupService.getGroupById(id);
        return ResponseEntity.ok(group);
    }

    /**
     * Mettre à jour un groupe
     * PUT /api/groups/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<GroupResponseDto> updateGroup(
            @PathVariable String id,
            @Valid @RequestBody GroupRequestDto groupRequestDto) {
        log.info("Requête de mise à jour du groupe avec l'ID : {}", id);
        
        GroupResponseDto updatedGroup = groupService.updateGroup(id, groupRequestDto);
        return ResponseEntity.ok(updatedGroup);
    }

    /**
     * Supprimer un groupe
     * DELETE /api/groups/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable String id) {
        log.info("Requête de suppression du groupe avec l'ID : {}", id);
        
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Ajouter un utilisateur à un groupe
     * POST /api/groups/{groupId}/users/{userId}
     */
    @PostMapping("/{groupId}/users/{userId}")
    public ResponseEntity<GroupResponseDto> addUserToGroup(
            @PathVariable String groupId,
            @PathVariable String userId) {
        log.info("Requête d'ajout de l'utilisateur {} au groupe {}", userId, groupId);
        
        GroupResponseDto updatedGroup = groupService.addUserToGroup(groupId, userId);
        return ResponseEntity.ok(updatedGroup);
    }

    /**
     * Retirer un utilisateur d'un groupe
     * DELETE /api/groups/{groupId}/users/{userId}
     */
    @DeleteMapping("/{groupId}/users/{userId}")
    public ResponseEntity<GroupResponseDto> removeUserFromGroup(
            @PathVariable String groupId,
            @PathVariable String userId) {
        log.info("Requête de suppression de l'utilisateur {} du groupe {}", userId, groupId);
        
        GroupResponseDto updatedGroup = groupService.removeUserFromGroup(groupId, userId);
        return ResponseEntity.ok(updatedGroup);
    }

    /**
     * Récupérer les groupes d'un utilisateur
     * GET /api/groups/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GroupResponseDto>> getGroupsByUserId(@PathVariable String userId) {
        log.info("Requête de récupération des groupes de l'utilisateur : {}", userId);
        
        List<GroupResponseDto> groups = groupService.getGroupsByUserId(userId);
        return ResponseEntity.ok(groups);
    }
}
