package com.hb.cda.tricount_api.mapper;

import org.mapstruct.Mapper;

import com.hb.cda.tricount_api.dto.response.SettlementsResponseDto;
import com.hb.cda.tricount_api.entity.Settlement;

@Mapper
public interface SettlementMapper {
  SettlementsResponseDto toDto(Settlement settlement);
}
