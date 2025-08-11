package com.hb.cda.tricount_api.service.impl;

import com.hb.cda.tricount_api.dto.request.UserRequestDto;
import com.hb.cda.tricount_api.dto.response.UserResponseDto;
import com.hb.cda.tricount_api.entity.User;
import com.hb.cda.tricount_api.mapper.UserMapper;
import com.hb.cda.tricount_api.repository.UserRepository;
import com.hb.cda.tricount_api.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service pour la gestion des utilisateurs
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Créer un nouvel utilisateur
     * @param userRequestDto les données de l'utilisateur à créer
     * @return l'utilisateur créé
     */
    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        log.info("Création d'un nouvel utilisateur avec l'email : {}", userRequestDto.getEmail());
        
        // Vérifier si l'email existe déjà
        if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new RuntimeException("Un utilisateur avec cet email existe déjà");
        }
        
        // Convertir le DTO en entité
        User user = userMapper.toEntity(userRequestDto);
        
        // Encoder le mot de passe
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        
        // Sauvegarder l'utilisateur
        User savedUser = userRepository.save(user);
        
        log.info("Utilisateur créé avec succès avec l'ID : {}", savedUser.getId());
        return userMapper.toDto(savedUser);
    }

    /**
     * Récupérer tous les utilisateurs
     * @return la liste de tous les utilisateurs
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        log.info("Récupération de tous les utilisateurs");
        List<User> users = userRepository.findAll();
        return userMapper.toDtoList(users);
    }

    /**
     * Récupérer un utilisateur par son ID
     * @param id l'ID de l'utilisateur
     * @return l'utilisateur trouvé
     */
    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserById(String id) {
        log.info("Récupération de l'utilisateur avec l'ID : {}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + id));
        
        return userMapper.toDto(user);
    }

    /**
     * Récupérer un utilisateur par son email
     * @param email l'email de l'utilisateur
     * @return l'utilisateur trouvé
     */
    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserByEmail(String email) {
        log.info("Récupération de l'utilisateur avec l'email : {}", email);
        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'email : " + email));
        
        return userMapper.toDto(user);
    }

    /**
     * Mettre à jour un utilisateur
     * @param id l'ID de l'utilisateur à mettre à jour
     * @param userRequestDto les nouvelles données
     * @return l'utilisateur mis à jour
     */
    @Override
    public UserResponseDto updateUser(String id, UserRequestDto userRequestDto) {
        log.info("Mise à jour de l'utilisateur avec l'ID : {}", id);
        
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + id));
        
        // Vérifier si le nouvel email n'est pas déjà utilisé par un autre utilisateur
        Optional<User> userWithSameEmail = userRepository.findByEmail(userRequestDto.getEmail());
        if (userWithSameEmail.isPresent() && !userWithSameEmail.get().getId().equals(id)) {
            throw new RuntimeException("Un autre utilisateur utilise déjà cet email");
        }
        
        // Mettre à jour les champs
        userMapper.updateEntity(userRequestDto, existingUser);
        
        // Encoder le nouveau mot de passe si fourni
        if (userRequestDto.getPassword() != null && !userRequestDto.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        }
        
        User savedUser = userRepository.save(existingUser);
        
        log.info("Utilisateur mis à jour avec succès");
        return userMapper.toDto(savedUser);
    }

    /**
     * Supprimer un utilisateur
     * @param id l'ID de l'utilisateur à supprimer
     */
    @Override
    public void deleteUser(String id) {
        log.info("Suppression de l'utilisateur avec l'ID : {}", id);
        
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Utilisateur non trouvé avec l'ID : " + id);
        }
        
        userRepository.deleteById(id);
        log.info("Utilisateur supprimé avec succès");
    }

    /**
     * Activer/désactiver un utilisateur
     * @param id l'ID de l'utilisateur
     * @param active le nouveau statut
     * @return l'utilisateur mis à jour
     */
    @Override
    public UserResponseDto setUserActive(String id, boolean active) {
        log.info("Changement du statut de l'utilisateur {} à : {}", id, active);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + id));
        
        user.setActive(active);
        User savedUser = userRepository.save(user);
        
        return userMapper.toDto(savedUser);
    }

    /**
     * Récupérer les utilisateurs d'un groupe
     * @param groupId l'ID du groupe
     * @return la liste des utilisateurs du groupe
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> getUsersByGroupId(String groupId) {
        log.info("Récupération des utilisateurs du groupe : {}", groupId);
        
        List<User> users = userRepository.findByGroup_Id(groupId);
        return userMapper.toDtoList(users);
    }
}
