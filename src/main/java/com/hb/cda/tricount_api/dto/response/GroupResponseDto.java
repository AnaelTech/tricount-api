package com.hb.cda.tricount_api.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class GroupResponseDto {
  private String id;

  private String name;

  private List<String> users;

  private List<String> expenses;
}
