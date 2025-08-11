package com.hb.cda.tricount_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.hb.cda.tricount_api.dto.request.GroupRequestDto;
import com.hb.cda.tricount_api.dto.response.GroupResponseDto;
import com.hb.cda.tricount_api.entity.Group;
import com.hb.cda.tricount_api.entity.Expense;

import java.util.List;

/**
 * Mapper pour convertir entre les entités Group et les DTOs
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ExpenseMapper.class}, 
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GroupMapper {
    
    /**
     * Convertit une entité Group en DTO de réponse
     * @param group l'entité Group à convertir
     * @return le DTO de réponse GroupResponseDto
     */
    @Mapping(target = "totalExpenses", source = "expenses", qualifiedByName = "calculateTotalExpenses")
    @Mapping(target = "membersCount", source = "users", qualifiedByName = "calculateMembersCount")
    GroupResponseDto toDto(Group group);
    
    /**
     * Convertit une liste d'entités Group en liste de DTOs de réponse
     * @param groups la liste d'entités Group à convertir
     * @return la liste de DTOs de réponse
     */
    List<GroupResponseDto> toDtoList(List<Group> groups);
    
    /**
     * Convertit un DTO de requête en entité Group
     * @param groupRequestDto le DTO de requête à convertir
     * @return l'entité Group créée
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "expenses", ignore = true)
    @Mapping(target = "settlements", ignore = true)
    Group toEntity(GroupRequestDto groupRequestDto);
    
    /**
     * Met à jour une entité Group existante avec les données d'un DTO de requête
     * @param groupRequestDto le DTO contenant les nouvelles données
     * @param group l'entité existante à mettre à jour
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "expenses", ignore = true)
    @Mapping(target = "settlements", ignore = true)
    void updateEntity(GroupRequestDto groupRequestDto, @MappingTarget Group group);
    
    /**
     * Calcule le total des dépenses d'un groupe
     * @param expenses la liste des dépenses
     * @return le montant total
     */
    @Named("calculateTotalExpenses")
    default Double calculateTotalExpenses(List<Expense> expenses) {
        return expenses == null ? 0.0 : expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }
    
    /**
     * Calcule le nombre de membres d'un groupe
     * @param users la liste des utilisateurs
     * @return le nombre de membres
     */
    @Named("calculateMembersCount")
    default Integer calculateMembersCount(List<?> users) {
        return users == null ? 0 : users.size();
    }
}
