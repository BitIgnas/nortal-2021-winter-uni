package com.nortal.mega.rest;

import com.nortal.mega.service.BuildingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/mega/building")
public class BuildingController {

    private final BuildingService buildingService;
    private final BuildingResponseDtoMapper buildingResponseDtoMapper;

    @GetMapping(value = "", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BuildingResponseDto>> getAll() {
        return ResponseEntity.ok(buildingService.findAll().stream().map(buildingResponseDtoMapper::map).collect(Collectors.toList()));
    }

    @GetMapping(value = "/{buildingId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BuildingResponseDto> getBuildingById(@PathVariable("buildingId") Long buildingId) {
        return ResponseEntity.ok(buildingResponseDtoMapper.map(buildingService.findBuildingById(buildingId)));
    }

    @PostMapping(value = "", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<BuildingResponseDto> createBuilding(@Valid  @RequestBody BuildingRequestDto buildingRequestDto) {
        URI location = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/v1/mega/building").toUriString());
        return ResponseEntity.created(location).body(buildingResponseDtoMapper.map(buildingService.saveBuilding(buildingRequestDto)));
    }

    @PutMapping(value = "", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<BuildingResponseDto> updateBuilding(@Valid @RequestBody BuildingRequestDto buildingRequestDto) {
        return ResponseEntity.ok().body(buildingResponseDtoMapper.map(buildingService.updateBuilding(buildingRequestDto)));
    }

    @DeleteMapping(value = "/{buildingId}")
    public ResponseEntity<BuildingResponseDto> deleteBuildingById(@PathVariable("buildingId") Long id) {
        return ResponseEntity.ok().body(buildingResponseDtoMapper.map(buildingService.deleteBuildingById(id)));
    }
}
