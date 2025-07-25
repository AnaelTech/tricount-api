package com.hb.cda.tricount_api.dto.request;

import lombok.Data;

@Data
public class SettlementRequestDto {
  private String description;

  private Double amount;

  private String debtor;

  private String creditor;

  private String groupId;
}
