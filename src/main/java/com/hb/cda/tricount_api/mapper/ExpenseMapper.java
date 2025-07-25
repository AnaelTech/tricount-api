package com.hb.cda.tricount_api.mapper;

import org.mapstruct.Mapper;

import com.hb.cda.tricount_api.dto.response.ExpenseResponseDto;
import com.hb.cda.tricount_api.entity.Expense;

@Mapper
public interface ExpenseMapper {
  ExpenseResponseDto toDto(Expense expense);
}
