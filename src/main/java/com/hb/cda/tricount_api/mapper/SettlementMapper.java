package com.hb.cda.tricount_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.hb.cda.tricount_api.dto.request.SettlementRequestDto;
import com.hb.cda.tricount_api.dto.response.SettlementsResponseDto;
import com.hb.cda.tricount_api.entity.Settlement;

import java.util.List;

/**
 * Mapper pour convertir entre les entités Settlement et les DTOs
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class}, 
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SettlementMapper {
    
    /**
     * Convertit une entité Settlement en DTO de réponse
     * @param settlement l'entité Settlement à convertir
     * @return le DTO de réponse SettlementsResponseDto
     */
    @Mapping(target = "groupId", source = "group.id")
    @Mapping(target = "groupName", source = "group.name")
    @Mapping(target = "status", constant = "CALCULATED")
    SettlementsResponseDto toDto(Settlement settlement);
    
    /**
     * Convertit une liste d'entités Settlement en liste de DTOs de réponse
     * @param settlements la liste d'entités Settlement à convertir
     * @return la liste de DTOs de réponse
     */
    List<SettlementsResponseDto> toDtoList(List<Settlement> settlements);
    
    /**
     * Convertit un DTO de requête en entité Settlement
     * @param settlementRequestDto le DTO de requête à convertir
     * @return l'entité Settlement créée
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "debtor", ignore = true)
    @Mapping(target = "creditor", ignore = true)
    @Mapping(target = "group", ignore = true)
    Settlement toEntity(SettlementRequestDto settlementRequestDto);
    
    /**
     * Met à jour une entité Settlement existante avec les données d'un DTO de requête
     * @param settlementRequestDto le DTO contenant les nouvelles données
     * @param settlement l'entité existante à mettre à jour
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "debtor", ignore = true)
    @Mapping(target = "creditor", ignore = true)
    @Mapping(target = "group", ignore = true)
    void updateEntity(SettlementRequestDto settlementRequestDto, @MappingTarget Settlement settlement);
}
