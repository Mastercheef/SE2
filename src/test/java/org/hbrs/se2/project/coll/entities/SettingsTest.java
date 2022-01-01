package org.hbrs.se2.project.coll.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SettingsTest {

    private Settings settings = new Settings();
    int id;
    int userId;
    @Mock
    private User user;


    boolean notificationIsEnabled;

    @BeforeEach
    void setup(){
        id = 1;

        notificationIsEnabled = true;
        when(user.getId()).thenReturn(5);
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

    @Test
    void testHashCode() {
        assertEquals(31028 , settings.hashCode());
    }

    @Test
    void testToString() {

        settings.setId(id);
        settings.setUser(user);
        settings.setNotificationIsEnabled(notificationIsEnabled);
        assertEquals("1 5 true" , settings.toString());
    }
}
