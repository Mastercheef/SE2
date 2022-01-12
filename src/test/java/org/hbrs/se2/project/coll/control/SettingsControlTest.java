package org.hbrs.se2.project.coll.control;

import com.vaadin.flow.component.checkbox.CheckboxGroup;
import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.control.factories.UserFactory;
import org.hbrs.se2.project.coll.dtos.SettingsDTO;
import org.hbrs.se2.project.coll.entities.Settings;
import org.hbrs.se2.project.coll.repository.SettingsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class SettingsControlTest {

    @InjectMocks
    SettingsControl settingsControl;

    @Mock
    private SettingsRepository settingsRepository;

    @Mock
    SettingsDTO settingsDTO;

    @Mock
    CheckboxGroup<String> checkboxGroup;
    @Mock
    Settings settings;
    @Test
    void getUserSettings() {
        when(settingsRepository.findSettingsById(100)).thenReturn(settingsDTO);
        assertEquals(settingsDTO , settingsControl.getUserSettings(100));
        assertTrue(settingsControl.getUserSettings(100) instanceof  SettingsDTO);

    }

    @Test
    void updateUserSettings() {
        try (MockedStatic<UserFactory> classMock = mockStatic(UserFactory.class)) {
            Set<String> stringHashSet = new HashSet<>();
            when(checkboxGroup.getValue()).thenReturn(stringHashSet);
            doNothing().when(settings).setNotificationIsEnabled(true);
            classMock.when(() -> UserFactory.createSettings(settingsDTO)).thenReturn(settings);
            assertEquals(settings, UserFactory.createSettings(settingsDTO));
            when(settingsRepository.save(settings)).thenReturn(settings);
            when(settings.getId()).thenReturn(100);

            assertEquals(settings , settingsControl.updateUserSettings(settingsDTO , checkboxGroup));


        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }
    }
    @Test
    void saveUserSettings() throws DatabaseUserException {
        when(settingsRepository.save(settings)).thenReturn(settings);
        when(settings.getId()).thenReturn(100);

        assertEquals(settings , settingsControl.saveUserSettings(settings));

        when(settings.getId()).thenReturn(0);
        when(settings.getId()).thenReturn(0);
        assertEquals(settings , settingsControl.saveUserSettings(settings));
    }

    @Test
    void saveUserSettingsDataAccessResourceFailureException(){
        when(settingsRepository.save(settings)).thenThrow(DataAccessResourceFailureException.class);
        try {
            assertThrows(DataAccessResourceFailureException.class , (Executable) settingsControl.saveUserSettings(settings));
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }
    }

    @Test
    void saveUserSettingsOtherError() {
        when(settingsRepository.save(settings)).thenThrow(DataIntegrityViolationException.class);
        try {
            assertThrows(DataIntegrityViolationException.class , (Executable) settingsControl.saveUserSettings(settings));
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }
    }
}
