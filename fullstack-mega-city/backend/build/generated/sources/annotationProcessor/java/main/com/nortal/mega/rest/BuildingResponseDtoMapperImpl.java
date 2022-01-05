package com.nortal.mega.rest;

import com.nortal.mega.service.Building;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-06T00:01:51+0200",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.jar, environment: Java 11.0.11 (AdoptOpenJDK)"
)
@Component
public class BuildingResponseDtoMapperImpl implements BuildingResponseDtoMapper {

    @Override
    public BuildingResponseDto map(Building building) {
        if ( building == null ) {
            return null;
        }

        BuildingResponseDto buildingResponseDto = new BuildingResponseDto();

        buildingResponseDto.setName( building.getName() );
        buildingResponseDto.setAddress( building.getAddress() );
        buildingResponseDto.setIndex( building.getIndex() );
        buildingResponseDto.setSectorCode( building.getSectorCode() );
        buildingResponseDto.setEnergyUnits( building.getEnergyUnits() );
        buildingResponseDto.setEnergyUnitMax( building.getEnergyUnitMax() );

        return buildingResponseDto;
    }

    @Override
    public Building map(BuildingResponseDto buildingResponseDto) {
        if ( buildingResponseDto == null ) {
            return null;
        }

        Building building = new Building();

        building.setName( buildingResponseDto.getName() );
        building.setAddress( buildingResponseDto.getAddress() );
        building.setIndex( buildingResponseDto.getIndex() );
        building.setSectorCode( buildingResponseDto.getSectorCode() );
        building.setEnergyUnits( buildingResponseDto.getEnergyUnits() );
        building.setEnergyUnitMax( buildingResponseDto.getEnergyUnitMax() );

        return building;
    }
}
