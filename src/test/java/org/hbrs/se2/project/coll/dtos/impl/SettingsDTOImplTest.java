package org.hbrs.se2.project.coll.dtos.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SettingsDTOImplTest {

    SettingsDTOImpl settingsDTO;

    @BeforeEach
    void setup() {
        settingsDTO = new SettingsDTOImpl();
    }

    @Test
    void getId() {
        settingsDTO.setId(100);
        assertEquals(100 , settingsDTO.getId());
    }

    @Test
    void getNotificationIsEnabledTrue() {
        settingsDTO.setNotificationIsEnabled(true);
        assertTrue(settingsDTO.getNotificationIsEnabled());
    }
    @Test
    void getNotificationIsEnabledFalse() {
        settingsDTO.setNotificationIsEnabled(false);
        assertFalse(settingsDTO.getNotificationIsEnabled());
    }
}