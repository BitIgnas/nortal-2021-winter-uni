package com.nortal.mega.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "building")
@AllArgsConstructor
@NoArgsConstructor
public class BuildingDbo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "index")
    private String index;
    @Column(name = "sector_code")
    private String sectorCode;
    @Column(name = "energy_units")
    private Integer energyUnits;
    @Column(name = "energy_unit_max")
    private Integer energyUnitMax;
}
