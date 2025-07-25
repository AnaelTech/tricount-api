package com.hb.cda.tricount_api.dto.response;

import lombok.Data;

@Data
public class SettlementsResponseDto {
  private String id;

  private Double amount;

  private UserResponseDto debtor;

  private UserResponseDto creditor;

  private String group;

  private String comment;
}
