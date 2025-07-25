package com.hb.cda.tricount_api.dto.response;

import java.util.List;

import lombok.Data;

@Data
public class ExpenseResponseDto {
  private String id;

  private String description;

  private Double amount;

  private String status;

  private UserResponseDto payer;

  private List<UserResponseDto> beneficiaries;

  private GroupResponseDto group;
}
