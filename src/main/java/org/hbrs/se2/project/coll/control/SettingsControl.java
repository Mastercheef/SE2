package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.control.factories.UserFactory;
import org.hbrs.se2.project.coll.dtos.ContactPersonDTO;
import org.hbrs.se2.project.coll.dtos.SettingsDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.*;
import org.hbrs.se2.project.coll.repository.SettingsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SettingsControl {

    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsControl.class);

    @Autowired
    private SettingsRepository settingsRepository;


    public Settings createNewUserSettings(UserDTO userDTO) throws DatabaseUserException {
        Settings settings = UserFactory.createSettingsFromBasicUser(userDTO);
        settings.setNotificationIsEnabled(true);
        return saveUserSettings(settings);
    }

    public Settings updateSettings(SettingsDTO settingsDTO) throws DatabaseUserException {
        Settings settings = UserFactory.createSettings(settingsDTO);
        return saveUserSettings(settings);
    }

    private Settings saveUserSettings(Settings settings) throws DatabaseUserException {
        try {
            // Speichere Entity in DB
            Settings savedSettings = this.settingsRepository.save(settings);

            if (settings.getId() > 0)
                LOGGER.info("LOG : Updated Settings with ID : {}", settings.getId());
            else
                LOGGER.info("LOG: Created new Settings: {}", settings.getId());
            return savedSettings;
        } catch (Exception exception) {
            LOGGER.info("LOG : {}" , exception.toString());
            if (exception instanceof org.springframework.dao.DataAccessResourceFailureException) {
                throw new DatabaseUserException("Während der Verbindung zur Datenbank mit JPA ist ein Fehler aufgetreten.");
            } else {
                throw new DatabaseUserException("Es ist ein unerwarteter Fehler aufgetreten.");
            }
        }
    }
}
