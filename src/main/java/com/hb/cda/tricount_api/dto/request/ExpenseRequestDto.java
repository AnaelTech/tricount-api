package com.hb.cda.tricount_api.dto.request;

import java.util.List;

import lombok.Data;

@Data
public class ExpenseRequestDto {
  private String description;

  private Double amount;

  private String payer;

  private List<String> beneficiaries;
}
