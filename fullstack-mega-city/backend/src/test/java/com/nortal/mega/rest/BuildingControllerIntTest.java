package com.nortal.mega.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nortal.mega.service.Building;
import com.nortal.mega.service.BuildingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class BuildingControllerIntTest {

    @MockBean
    private BuildingService buildingService;

    @MockBean
    private BuildingResponseDtoMapper buildingResponseDtoMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAll() throws Exception {
        List<Building> buildingResponses = List.of(new Building().setId(1L).setName("test_name").setAddress("address_building").setSectorCode("sector-test").setIndex("CU89").setEnergyUnits(400).setEnergyUnitMax(4000));
        BuildingResponseDto buildingResponseDto = new BuildingResponseDto().setId(1L).setName("test_name").setAddress("address_building").setSectorCode("sector-test").setIndex("CU89").setEnergyUnits(400).setEnergyUnitMax(4000);

        when(buildingService.findAll()).thenReturn(buildingResponses);
        when(buildingResponseDtoMapper.map(any(Building.class))).thenReturn(buildingResponseDto);

        mockMvc.perform(get("/api/v1/mega/building"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("test_name")))
                .andExpect(jsonPath("$[0].address", is("address_building")))
                .andExpect(jsonPath("$[0].sectorCode", is("sector-test")))
                .andExpect(jsonPath("$[0].index", is("CU89")))
                .andExpect(jsonPath("$[0].energyUnits", is(400)))
                .andExpect(jsonPath("$[0].energyUnitMax", is(4000)));

        verify(buildingService, times(1)).findAll();
        verify(buildingResponseDtoMapper, times(1)).map(any(Building.class));
        verifyNoMoreInteractions(buildingService);
        verifyNoMoreInteractions(buildingResponseDtoMapper);
    }

    @Test
    void getBuildingById() throws Exception {
        Building building = new Building().setId(1L).setName("test_name").setAddress("address_building").setSectorCode("sector-test").setIndex("CU89").setEnergyUnits(400).setEnergyUnitMax(4000);
        BuildingResponseDto buildingResponseDto = new BuildingResponseDto().setId(1L).setName("test_name").setAddress("address_building").setSectorCode("sector-test").setIndex("CU89").setEnergyUnits(400).setEnergyUnitMax(4000);

        when(buildingService.findBuildingById(anyLong())).thenReturn(building);
        when(buildingResponseDtoMapper.map(any(Building.class))).thenReturn(buildingResponseDto);

        mockMvc.perform(get("/api/v1/mega/building/{buildingId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("test_name")))
                .andExpect(jsonPath("$.address", is("address_building")))
                .andExpect(jsonPath("$.sectorCode", is("sector-test")))
                .andExpect(jsonPath("$.index", is("CU89")))
                .andExpect(jsonPath("$.energyUnits", is(400)))
                .andExpect(jsonPath("$.energyUnitMax", is(4000)));

        verify(buildingService, times(1)).findBuildingById(anyLong());
        verify(buildingResponseDtoMapper, times(1)).map(any(Building.class));
        verifyNoMoreInteractions(buildingService);
        verifyNoMoreInteractions(buildingResponseDtoMapper);
    }

    @Test
    void createBuilding() throws Exception {
        Building building = new Building().setId(1L).setName("test_name").setAddress("address_building").setSectorCode("sector-test").setIndex("CU89").setEnergyUnits(400).setEnergyUnitMax(4000);
        BuildingRequestDto buildingRequestDto = new BuildingRequestDto().setName("test_name").setAddress("address_building").setSectorCode("sector-test").setIndex("CU89").setEnergyUnits(400).setEnergyUnitMax(4000);
        BuildingResponseDto buildingResponseDto = new BuildingResponseDto().setId(1L).setName("test_name").setAddress("address_building").setSectorCode("sector-test").setIndex("CU89").setEnergyUnits(400).setEnergyUnitMax(4000);

        when(buildingService.saveBuilding(buildingRequestDto)).thenReturn(building);
        when(buildingResponseDtoMapper.map(any(Building.class))).thenReturn(buildingResponseDto);

        mockMvc.perform(post("/api/v1/mega/building", buildingRequestDto)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(buildingRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("test_name")))
                .andExpect(jsonPath("$.address", is("address_building")))
                .andExpect(jsonPath("$.sectorCode", is("sector-test")))
                .andExpect(jsonPath("$.index", is("CU89")))
                .andExpect(jsonPath("$.energyUnits", is(400)))
                .andExpect(jsonPath("$.energyUnitMax", is(4000)));

        verify(buildingService, times(1)).saveBuilding(any(BuildingRequestDto.class));
        verify(buildingResponseDtoMapper, times(1)).map(any(Building.class));
        verifyNoMoreInteractions(buildingService);
        verifyNoMoreInteractions(buildingResponseDtoMapper);
    }

    @Test
    void updateBuilding() throws Exception {
        Building building = new Building().setId(1L).setName("test_name").setAddress("address_building").setSectorCode("sector-test").setIndex("CU89").setEnergyUnits(400).setEnergyUnitMax(4000);
        BuildingRequestDto buildingRequestDto = new BuildingRequestDto().setName("test_name").setAddress("address_building").setSectorCode("sector-test").setIndex("CU89").setEnergyUnits(400).setEnergyUnitMax(4000);
        BuildingResponseDto buildingResponseDto = new BuildingResponseDto().setId(1L).setName("test_name").setAddress("address_building").setSectorCode("sector-test").setIndex("CU89").setEnergyUnits(400).setEnergyUnitMax(4000);

        when(buildingService.updateBuilding(buildingRequestDto)).thenReturn(building);
        when(buildingResponseDtoMapper.map(any(Building.class))).thenReturn(buildingResponseDto);

        mockMvc.perform(put("/api/v1/mega/building", buildingRequestDto)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(buildingRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("test_name")))
                .andExpect(jsonPath("$.address", is("address_building")))
                .andExpect(jsonPath("$.sectorCode", is("sector-test")))
                .andExpect(jsonPath("$.index", is("CU89")))
                .andExpect(jsonPath("$.energyUnits", is(400)))
                .andExpect(jsonPath("$.energyUnitMax", is(4000)));

        verify(buildingService, times(1)).updateBuilding(any(BuildingRequestDto.class));
        verify(buildingResponseDtoMapper, times(1)).map(any(Building.class));
        verifyNoMoreInteractions(buildingService);
        verifyNoMoreInteractions(buildingResponseDtoMapper);
    }

    @Test
    void deleteBuildingById() throws Exception {
        Building building = new Building().setId(1L).setName("test_name").setAddress("address_building").setSectorCode("sector-test").setIndex("CU89").setEnergyUnits(400).setEnergyUnitMax(4000);
        BuildingResponseDto buildingResponseDto = new BuildingResponseDto().setId(1L).setName("test_name").setAddress("address_building").setSectorCode("sector-test").setIndex("CU89").setEnergyUnits(400).setEnergyUnitMax(4000);

        when(buildingService.deleteBuildingById(anyLong())).thenReturn(building);
        when(buildingResponseDtoMapper.map(any(Building.class))).thenReturn(buildingResponseDto);

        mockMvc.perform(delete("/api/v1/mega/building/{buildingId}", 1L)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("test_name")))
                .andExpect(jsonPath("$.address", is("address_building")))
                .andExpect(jsonPath("$.sectorCode", is("sector-test")))
                .andExpect(jsonPath("$.index", is("CU89")))
                .andExpect(jsonPath("$.energyUnits", is(400)))
                .andExpect(jsonPath("$.energyUnitMax", is(4000)));

        verify(buildingService, times(1)).deleteBuildingById(anyLong());
        verify(buildingResponseDtoMapper, times(1)).map(any(Building.class));
        verifyNoMoreInteractions(buildingService);
        verifyNoMoreInteractions(buildingResponseDtoMapper);

    }


}