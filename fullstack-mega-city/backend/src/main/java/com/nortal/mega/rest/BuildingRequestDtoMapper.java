package com.nortal.mega.rest;

import com.nortal.mega.service.Building;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BuildingRequestDtoMapper {

    BuildingRequestDto map(Building building);
    Building map(BuildingRequestDto building);
}
