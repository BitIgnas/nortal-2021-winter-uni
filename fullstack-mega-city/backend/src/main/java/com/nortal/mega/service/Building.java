package com.nortal.mega.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Building {

    private Long id;
    private String name;
    private String address;
    private String index;
    private String sectorCode;
    private Integer energyUnits;
    private Integer energyUnitMax;
}
