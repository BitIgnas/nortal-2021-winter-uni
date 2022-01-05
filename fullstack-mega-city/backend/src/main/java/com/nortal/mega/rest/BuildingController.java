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

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/mega/building")
public class BuildingController {

    private final BuildingService buildingService;
    private final BuildingResponseDtoMapper buildingResponseDtoMapper;

    @GetMapping
    public ResponseEntity<List<BuildingResponseDto>> getAll() {
        return ResponseEntity.ok(buildingService.findAll().stream().map(buildingResponseDtoMapper::map).collect(Collectors.toList()));
    }

    @GetMapping("/{buildingId}")
    public ResponseEntity<BuildingResponseDto> getBuildingById(@PathVariable Long buildingId) {
        return ResponseEntity.ok(buildingResponseDtoMapper.map(buildingService.findBuildingById(buildingId)));
    }

    @PostMapping
    public ResponseEntity<BuildingResponseDto> createBuilding(@RequestBody @Valid BuildingRequestDto buildingRequestDto) {
        URI location = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/v1/mega/building").toUriString());
        return ResponseEntity.created(location).body(buildingResponseDtoMapper.map(buildingService.saveBuilding(buildingRequestDto)));
    }

    @PutMapping
    public ResponseEntity<BuildingResponseDto> updateBuilding(@RequestBody @Valid BuildingRequestDto buildingRequestDto) {
        return ResponseEntity.ok().body(buildingResponseDtoMapper.map(buildingService.updateBuilding(buildingRequestDto)));
    }
}
