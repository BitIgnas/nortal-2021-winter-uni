package com.nortal.mega.service;

import com.nortal.mega.exception.BuildingAlreadyExistsException;
import com.nortal.mega.exception.NoBuildingFoundException;
import com.nortal.mega.persistence.BuildingDboMapper;
import com.nortal.mega.persistence.BuildingRepository;
import com.nortal.mega.persistence.entity.BuildingDbo;
import com.nortal.mega.rest.BuildingRequestDto;
import com.nortal.mega.rest.BuildingRequestDtoMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BuildingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuildingService.class);

    private final BuildingRepository buildingRepository;
    private final BuildingDboMapper buildingDboMapper;
    private final BuildingRequestDtoMapper buildingRequestDtoMapper;

    public List<Building> findAll() {
        LOGGER.info("Finding ALL buildings");
        return buildingRepository.findAll().stream().map(buildingDboMapper::map).collect(Collectors.toList());
    }

    public Building findBuildingById(Long id) {
        LOGGER.info("Finding building by id: {}", id);
        return buildingDboMapper.map(buildingRepository.findById(id)
                .orElseThrow(() -> new NoBuildingFoundException(
                        String.format("No building with id: %d found.", id))));
    }

    public Building saveBuilding(BuildingRequestDto buildingRequestDto) {
        LOGGER.info("Saving building with name: {}, index: {}", buildingRequestDto.getName(), buildingRequestDto.getIndex());
        Optional<BuildingDbo> buildingDboOptional = buildingRepository.findByName(buildingRequestDto.getName());
        buildingDboOptional.ifPresent(buildingDbo -> {
            throw new BuildingAlreadyExistsException(
                    String.format("Building with name: %s already exists", buildingRequestDto.getName()));
        });

        Building building = buildingRequestDtoMapper.map(buildingRequestDto);
        return buildingDboMapper.map(buildingRepository.save(buildingDboMapper.map(building)));
    }

    public Building updateBuilding(BuildingRequestDto buildingRequestDto) {
        LOGGER.info("Updating building by name: {}", buildingRequestDto.getName());
        BuildingDbo buildingDbo = buildingRepository.findByName(buildingRequestDto.getName())
                .orElseThrow(() -> new NoBuildingFoundException(
                        String.format("Building with name: %s already exists", buildingRequestDto.getName())));

        return buildingDboMapper.map(buildingRepository.save(buildingDbo));
    }
}
