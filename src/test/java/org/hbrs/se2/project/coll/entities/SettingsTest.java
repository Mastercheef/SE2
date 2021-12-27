package org.hbrs.se2.project.coll.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SettingsTest {

    private Settings settings = new Settings();
    int id;
    int userId;
    private User user = new User();
    boolean notificationIsEnabled;

    @BeforeEach
    void setup(){
        id = 1;
        userId = 5;
        user.setId(5);
        notificationIsEnabled = true;
    }

    @Test
    void testGetId(){
        settings.setId(id);
        assertEquals(1, settings.getId());
    }

    @Test
    void testGetUser(){
        settings.setUser(user);
        assertEquals(user, settings.getUser());
    }

    @Test
    void testGetNotificationIsEnabled(){
        settings.setNotificationIsEnabled(notificationIsEnabled);
        assertTrue(settings.getNotificationIsEnabled());
    }
}
