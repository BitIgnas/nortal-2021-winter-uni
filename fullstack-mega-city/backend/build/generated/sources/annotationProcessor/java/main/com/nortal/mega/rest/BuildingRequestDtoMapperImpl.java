package com.nortal.mega.rest;

import com.nortal.mega.service.Building;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-05T23:11:31+0200",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.jar, environment: Java 11.0.11 (AdoptOpenJDK)"
)
@Component
public class BuildingRequestDtoMapperImpl implements BuildingRequestDtoMapper {

    @Override
    public BuildingRequestDto map(Building building) {
        if ( building == null ) {
            return null;
        }

        BuildingRequestDto buildingRequestDto = new BuildingRequestDto();

        buildingRequestDto.setName( building.getName() );
        buildingRequestDto.setAddress( building.getAddress() );
        buildingRequestDto.setIndex( building.getIndex() );
        buildingRequestDto.setSectorCode( building.getSectorCode() );
        buildingRequestDto.setEnergyUnits( building.getEnergyUnits() );
        buildingRequestDto.setEnergyUnitMax( building.getEnergyUnitMax() );

        return buildingRequestDto;
    }

    @Override
    public Building map(BuildingRequestDto building) {
        if ( building == null ) {
            return null;
        }

        Building building1 = new Building();

        building1.setName( building.getName() );
        building1.setAddress( building.getAddress() );
        building1.setIndex( building.getIndex() );
        building1.setSectorCode( building.getSectorCode() );
        building1.setEnergyUnits( building.getEnergyUnits() );
        building1.setEnergyUnitMax( building.getEnergyUnitMax() );

        return building1;
    }
}
