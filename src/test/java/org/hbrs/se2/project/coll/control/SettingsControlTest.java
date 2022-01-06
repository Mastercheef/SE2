package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.dtos.SettingsDTO;
import org.hbrs.se2.project.coll.repository.SettingsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith({MockitoExtension.class})
class SettingsControlTest {

    @InjectMocks
    SettingsControl settingsControl;

    @Mock
    private SettingsRepository settingsRepository;

    @Mock
    SettingsDTO settingsDTO;

    @Test
    void getUserSettings() {
        when(settingsRepository.findSettingsById(100)).thenReturn(settingsDTO);
        assertEquals(settingsDTO , settingsControl.getUserSettings(100));
        assertTrue(settingsControl.getUserSettings(100) instanceof  SettingsDTO);
    }
}
