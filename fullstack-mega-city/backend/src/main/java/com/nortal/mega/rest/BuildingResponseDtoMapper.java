package com.nortal.mega.rest;

import com.nortal.mega.service.Building;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BuildingResponseDtoMapper {

    BuildingResponseDto map(Building building);
    Building map(BuildingResponseDto buildingResponseDto);
}
