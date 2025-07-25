package com.hb.cda.tricount_api.mapper;

import org.mapstruct.Mapper;

import com.hb.cda.tricount_api.dto.response.GroupResponseDto;
import com.hb.cda.tricount_api.entity.Group;

@Mapper
public interface GroupMapper {
  GroupResponseDto toDto(Group group);
}
