package com.hb.cda.tricount_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.hb.cda.tricount_api.dto.request.UserRequestDto;
import com.hb.cda.tricount_api.dto.response.UserResponseDto;
import com.hb.cda.tricount_api.entity.User;

import java.util.List;

/**
 * Mapper pour convertir entre les entités User et les DTOs
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    
    /**
     * Convertit une entité User en DTO de réponse
     * @param user l'entité User à convertir
     * @return le DTO de réponse UserResponseDto
     */
    UserResponseDto toDto(User user);
    
    /**
     * Convertit une liste d'entités User en liste de DTOs de réponse
     * @param users la liste d'entités User à convertir
     * @return la liste de DTOs de réponse
     */
    List<UserResponseDto> toDtoList(List<User> users);
    
    /**
     * Convertit un DTO de requête en entité User
     * @param userRequestDto le DTO de requête à convertir
     * @return l'entité User créée
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "group", ignore = true)
    @Mapping(target = "expenses", ignore = true)
    @Mapping(target = "paidExpenses", ignore = true)
    @Mapping(target = "creditorSettlements", ignore = true)
    @Mapping(target = "debtorSettlements", ignore = true)
    User toEntity(UserRequestDto userRequestDto);
    
    /**
     * Met à jour une entité User existante avec les données d'un DTO de requête
     * @param userRequestDto le DTO contenant les nouvelles données
     * @param user l'entité existante à mettre à jour
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "group", ignore = true)
    @Mapping(target = "expenses", ignore = true)
    @Mapping(target = "paidExpenses", ignore = true)
    @Mapping(target = "creditorSettlements", ignore = true)
    @Mapping(target = "debtorSettlements", ignore = true)
    void updateEntity(UserRequestDto userRequestDto, @MappingTarget User user);
}
