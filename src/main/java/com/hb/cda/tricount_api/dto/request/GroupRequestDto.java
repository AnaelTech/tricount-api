package com.hb.cda.tricount_api.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class GroupRequestDto {

  private String name;

  private List<String> users;
}
