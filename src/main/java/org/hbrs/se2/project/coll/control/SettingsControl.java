package org.hbrs.se2.project.coll.control;

import com.vaadin.flow.component.checkbox.CheckboxGroup;
import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.control.factories.UserFactory;
import org.hbrs.se2.project.coll.dtos.SettingsDTO;
import org.hbrs.se2.project.coll.entities.*;
import org.hbrs.se2.project.coll.repository.SettingsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class SettingsControl {

    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsControl.class);

    @Autowired
    private SettingsRepository settingsRepository;



    public SettingsDTO getUserSettings(int id) {
        return settingsRepository.findSettingsById(id);
    }


    public Settings updateUserSettings(SettingsDTO settingsDTO, CheckboxGroup<String> checkboxGroup) throws DatabaseUserException {
        Set<String> settingsValues = checkboxGroup.getValue();
        Settings settings = UserFactory.createSettings(settingsDTO);

        settings.setNotificationIsEnabled(settingsValues.contains("Benachrichtigungen aktivieren"));

        return saveUserSettings(settings);
    }

    protected Settings saveUserSettings(Settings settings) throws DatabaseUserException {
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
