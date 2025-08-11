package com.hb.cda.tricount_api.controller;

import com.hb.cda.tricount_api.dto.request.UserRequestDto;
import com.hb.cda.tricount_api.dto.response.UserResponseDto;
import com.hb.cda.tricount_api.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Contrôleur REST pour la gestion des utilisateurs
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {

    private final UserService userService;

    /**
     * Créer un nouvel utilisateur
     * POST /api/users
     */
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        log.info("Requête de création d'utilisateur reçue pour l'email : {}", userRequestDto.getEmail());
        
        UserResponseDto createdUser = userService.createUser(userRequestDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * Récupérer tous les utilisateurs
     * GET /api/users
     */
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        log.info("Requête de récupération de tous les utilisateurs");
        
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Récupérer un utilisateur par son ID
     * GET /api/users/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable String id) {
        log.info("Requête de récupération de l'utilisateur avec l'ID : {}", id);
        
        UserResponseDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Récupérer un utilisateur par son email
     * GET /api/users/email/{email}
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
        log.info("Requête de récupération de l'utilisateur avec l'email : {}", email);
        
        UserResponseDto user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    /**
     * Mettre à jour un utilisateur
     * PUT /api/users/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable String id,
            @Valid @RequestBody UserRequestDto userRequestDto) {
        log.info("Requête de mise à jour de l'utilisateur avec l'ID : {}", id);
        
        UserResponseDto updatedUser = userService.updateUser(id, userRequestDto);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Supprimer un utilisateur
     * DELETE /api/users/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        log.info("Requête de suppression de l'utilisateur avec l'ID : {}", id);
        
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Activer/désactiver un utilisateur
     * PATCH /api/users/{id}/active
     */
    @PatchMapping("/{id}/active")
    public ResponseEntity<UserResponseDto> setUserActive(
            @PathVariable String id,
            @RequestParam boolean active) {
        log.info("Requête de changement de statut de l'utilisateur {} à : {}", id, active);
        
        UserResponseDto updatedUser = userService.setUserActive(id, active);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Récupérer les utilisateurs d'un groupe
     * GET /api/users/group/{groupId}
     */
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<UserResponseDto>> getUsersByGroupId(@PathVariable String groupId) {
        log.info("Requête de récupération des utilisateurs du groupe : {}", groupId);
        
        List<UserResponseDto> users = userService.getUsersByGroupId(groupId);
        return ResponseEntity.ok(users);
    }
}
