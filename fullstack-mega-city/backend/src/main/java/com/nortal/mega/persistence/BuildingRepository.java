package com.nortal.mega.persistence;

import com.nortal.mega.persistence.entity.BuildingDbo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.swing.plaf.OptionPaneUI;
import java.util.List;
import java.util.Optional;

@Repository
public interface BuildingRepository extends CrudRepository<BuildingDbo, Long> {

    Optional<BuildingDbo> findByName(String name);

    List<BuildingDbo> findAll();
}
