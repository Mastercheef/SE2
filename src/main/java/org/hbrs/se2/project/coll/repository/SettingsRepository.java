package org.hbrs.se2.project.coll.repository;

import org.hbrs.se2.project.coll.dtos.SettingsDTO;
import org.hbrs.se2.project.coll.entities.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface SettingsRepository extends JpaRepository<Settings, Integer> {
    SettingsDTO findSettingsById(int id);
}


