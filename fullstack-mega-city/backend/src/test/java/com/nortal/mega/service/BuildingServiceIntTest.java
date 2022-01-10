package com.nortal.mega.service;

import com.nortal.mega.persistence.BuildingDboMapper;
import com.nortal.mega.persistence.BuildingRepository;
import com.nortal.mega.persistence.entity.BuildingDbo;
import com.nortal.mega.rest.BuildingRequestDto;
import com.nortal.mega.rest.BuildingRequestDtoMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BuildingServiceIntTest {

    @Mock
    private BuildingRepository buildingRepository;
    @Mock
    private BuildingDboMapper buildingDboMapper;
    @Mock
    private BuildingRequestDtoMapper buildingRequestDtoMapper;
    @InjectMocks
    private BuildingService buildingService;
    @Captor
    ArgumentCaptor<BuildingDbo> buildingArgumentCaptor;

    @Test
    void shouldFindAllBuildings() {
        List<BuildingDbo> buildings = List.of(new BuildingDbo(
                1L,
                "test_name",
                "test_address",
                "CX25", "SECTOR-1",
                400,
                200)
        );

        Building expectedBuilding = new Building(
                1L,
                "test_name",
                "test_address",
                "CX25",
                "SECTOR-1",
                400,
                200
        );

        when(buildingRepository.findAll()).thenReturn(buildings);
        when(buildingDboMapper.map(any(BuildingDbo.class))).thenReturn(expectedBuilding);

        List<Building> actualBuildings = buildingService.findAll();

        verify(buildingRepository, times(1)).findAll();
        verify(buildingDboMapper, times(1)).map(any(BuildingDbo.class));
        verifyNoMoreInteractions(buildingRepository);

        assertThat(actualBuildings).isNotNull();
        assertThat(actualBuildings.isEmpty()).isFalse();
        assertThat(actualBuildings.get(0)).isExactlyInstanceOf(Building.class);
        assertThat(actualBuildings.get(0)).isEqualTo(expectedBuilding);
        assertThat(actualBuildings.size()).isEqualTo(1);
    }

    @Test
    void shouldFindBuildingById() {
        BuildingDbo buildingDbo = new BuildingDbo(
                1L,
                "test_name",
                "test_address",
                "CX25", "SECTOR-1",
                400,
                200
        );

        Building expectedBuilding = new Building(
                1L,
                "test_name",
                "test_address",
                "CX25",
                "SECTOR-1",
                400,
                200
        );

        when(buildingRepository.findById(anyLong())).thenReturn(Optional.of(buildingDbo));
        when(buildingDboMapper.map(any(BuildingDbo.class))).thenReturn(expectedBuilding);

        Building actualBuilding = buildingService.findBuildingById(anyLong());

        verify(buildingRepository, times(1)).findById(anyLong());
        verify(buildingDboMapper, times(1)).map(any(BuildingDbo.class));
        verifyNoMoreInteractions(buildingRepository);

        assertThat(actualBuilding).isNotNull();
        assertThat(actualBuilding).isExactlyInstanceOf(Building.class);
        assertThat(actualBuilding.getId()).isEqualTo(1L);
        assertThat(actualBuilding.getName()).isEqualTo("test_name");
        assertThat(actualBuilding.getAddress()).isEqualTo("test_address");
        assertThat(actualBuilding.getIndex()).isEqualTo("CX25");
        assertThat(actualBuilding.getSectorCode()).isEqualTo("SECTOR-1");
        assertThat(actualBuilding.getEnergyUnits()).isEqualTo(400);
        assertThat(actualBuilding.getEnergyUnitMax()).isEqualTo(200);
    }

    @Test
    void shouldSaveBuilding() {
        BuildingRequestDto buildingRequestDto = new BuildingRequestDto().setName("test_name").setAddress("address_building").setSectorCode("sector-test").setIndex("CU89").setEnergyUnits(400).setEnergyUnitMax(4000);
        Building expectedBuilding = new Building().setName("test_name").setAddress("address_building").setSectorCode("sector-test").setIndex("CU89").setEnergyUnits(400).setEnergyUnitMax(4000);
        BuildingDbo buildingToSave = new BuildingDbo().setName("test_name").setAddress("address_building").setSectorCode("sector-test").setIndex("CU89").setEnergyUnits(400).setEnergyUnitMax(4000);

        when(buildingRepository.findByName(buildingRequestDto.getName())).thenReturn(Optional.empty());
        when(buildingRequestDtoMapper.map(any(BuildingRequestDto.class))).thenReturn(expectedBuilding);
        when(buildingDboMapper.map(any(Building.class))).thenReturn(buildingToSave);
        when(buildingRepository.save(any(BuildingDbo.class))).thenReturn(buildingToSave);
        when(buildingDboMapper.map(any(BuildingDbo.class))).thenReturn(expectedBuilding);

        Building actualBuilding = buildingService.saveBuilding(buildingRequestDto);

        verify(buildingRepository, times(1)).findByName(anyString());
        verify(buildingRepository, times(1)).save(any(BuildingDbo.class));
        verify(buildingDboMapper, times(1)).map(any(Building.class));
        verify(buildingRequestDtoMapper, times(1)).map(any(BuildingRequestDto.class));
        verifyNoMoreInteractions(buildingRepository);

        assertThat(actualBuilding).isNotNull();
        assertThat(actualBuilding).isExactlyInstanceOf(Building.class);
        assertThat(actualBuilding).isEqualTo(expectedBuilding);
    }

    @Test
    void updateBuilding() {
        BuildingRequestDto buildingRequestDto = new BuildingRequestDto().setName("test_name").setAddress("address_building").setSectorCode("sector-test").setIndex("CU89").setEnergyUnits(400).setEnergyUnitMax(4000);
        Building expectedBuilding = new Building().setName("test_name").setAddress("address_building").setSectorCode("sector-test").setIndex("CU89").setEnergyUnits(400).setEnergyUnitMax(4000);
        BuildingDbo buildingToUpdate = new BuildingDbo().setName("test_name").setAddress("address_building").setSectorCode("sector-test").setIndex("CU89").setEnergyUnits(400).setEnergyUnitMax(4000);

        when(buildingRepository.findById(anyLong())).thenReturn(Optional.of(new BuildingDbo()));
        when(buildingRequestDtoMapper.map(any(BuildingRequestDto.class))).thenReturn(expectedBuilding);
        when(buildingDboMapper.map(any(Building.class))).thenReturn(buildingToUpdate);
        when(buildingRepository.save(any(BuildingDbo.class))).thenReturn(buildingToUpdate);
        when(buildingDboMapper.map(any(BuildingDbo.class))).thenReturn(expectedBuilding);

        Building actualBuilding = buildingService.saveBuilding(buildingRequestDto);

        verify(buildingRequestDtoMapper, times(1)).map(any(BuildingRequestDto.class));
        verify(buildingDboMapper, times(1)).map(any(Building.class));
        verify(buildingRepository, times(1)).save(buildingArgumentCaptor.capture());
        verify(buildingDboMapper, times(1)).map(any(BuildingDbo.class));

        assertThat(actualBuilding).isNotNull();
        assertThat(actualBuilding).isExactlyInstanceOf(Building.class);
        assertThat(actualBuilding).isEqualTo(expectedBuilding);
    }

    @Test
    void shouldDeleteBuildingById() {
        Building expectedBuilding = new Building().setName("test_name").setAddress("address_building").setSectorCode("sector-test").setIndex("CU89").setEnergyUnits(400).setEnergyUnitMax(4000);
        BuildingDbo buildingToDelete = new BuildingDbo().setName("test_name").setAddress("address_building").setSectorCode("sector-test").setIndex("CU89").setEnergyUnits(400).setEnergyUnitMax(4000);

        when(buildingRepository.findById(anyLong())).thenReturn(Optional.of(buildingToDelete));
        doNothing().when(buildingRepository).delete(any(BuildingDbo.class));
        when(buildingDboMapper.map(any(BuildingDbo.class))).thenReturn(expectedBuilding);

        buildingService.deleteBuildingById(anyLong());

        verify(buildingRepository, times(1)).findById(anyLong());
        verify(buildingRepository, times(1)).delete(buildingArgumentCaptor.capture());
        verify(buildingDboMapper, times(1)).map(any(BuildingDbo.class));

        BuildingDbo expectedBuildingDbo = buildingArgumentCaptor.getValue();

        assertThat(expectedBuildingDbo).isNotNull();
        assertThat(expectedBuildingDbo).isExactlyInstanceOf(BuildingDbo.class);
        assertThat(expectedBuildingDbo).isEqualTo(buildingToDelete);
    }
}