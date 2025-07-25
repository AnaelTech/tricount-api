package com.hb.cda.tricount_api.dto.request;

import lombok.Data;

@Data
public class UserRequestDto {
  private String name;

  private String email;

  private String password;
}
