package com.hb.cda.tricount_api.mapper;

import org.mapstruct.Mapper;

import com.hb.cda.tricount_api.dto.response.UserResponseDto;
import com.hb.cda.tricount_api.entity.User;

@Mapper
public interface UserMapper {
  UserResponseDto toDto(User user);
}
