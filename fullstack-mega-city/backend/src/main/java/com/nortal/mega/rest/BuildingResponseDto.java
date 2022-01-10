package com.nortal.mega.rest;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BuildingResponseDto {

    private Long id;
    private String name;
    private String address;
    private String index;
    private String sectorCode;
    private Integer energyUnits;
    private Integer energyUnitMax;

}
