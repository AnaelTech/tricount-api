package com.hb.cda.tricount_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.hb.cda.tricount_api.dto.request.ExpenseRequestDto;
import com.hb.cda.tricount_api.dto.response.ExpenseResponseDto;
import com.hb.cda.tricount_api.entity.Expense;
import com.hb.cda.tricount_api.entity.User;

import java.util.List;

/**
 * Mapper pour convertir entre les entités Expense et les DTOs
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class}, 
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ExpenseMapper {
    
    /**
     * Convertit une entité Expense en DTO de réponse
     * @param expense l'entité Expense à convertir
     * @return le DTO de réponse ExpenseResponseDto
     */
    @Mapping(target = "groupId", source = "group.id")
    @Mapping(target = "groupName", source = "group.name")
    @Mapping(target = "amountPerBeneficiary", source = ".", qualifiedByName = "calculateAmountPerBeneficiary")
    ExpenseResponseDto toDto(Expense expense);
    
    /**
     * Convertit une liste d'entités Expense en liste de DTOs de réponse
     * @param expenses la liste d'entités Expense à convertir
     * @return la liste de DTOs de réponse
     */
    List<ExpenseResponseDto> toDtoList(List<Expense> expenses);
    
    /**
     * Convertit un DTO de requête en entité Expense
     * @param expenseRequestDto le DTO de requête à convertir
     * @return l'entité Expense créée
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "payer", ignore = true)
    @Mapping(target = "beneficiary", ignore = true)
    @Mapping(target = "group", ignore = true)
    Expense toEntity(ExpenseRequestDto expenseRequestDto);
    
    /**
     * Met à jour une entité Expense existante avec les données d'un DTO de requête
     * @param expenseRequestDto le DTO contenant les nouvelles données
     * @param expense l'entité existante à mettre à jour
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "payer", ignore = true)
    @Mapping(target = "beneficiary", ignore = true)
    @Mapping(target = "group", ignore = true)
    void updateEntity(ExpenseRequestDto expenseRequestDto, @MappingTarget Expense expense);
    
    /**
     * Calcule le montant par bénéficiaire
     * @param expense l'entité Expense
     * @return le montant par bénéficiaire
     */
    @Named("calculateAmountPerBeneficiary")
    default Double calculateAmountPerBeneficiary(Expense expense) {
        if (expense == null || expense.getBeneficiary() == null || expense.getBeneficiary().isEmpty()) {
            return 0.0;
        }
        return expense.getAmount() / expense.getBeneficiary().size();
    }
}
