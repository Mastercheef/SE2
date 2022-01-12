package org.hbrs.se2.project.coll.entities;

import org.hbrs.se2.project.coll.dtos.SettingsDTO;
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

    private Settings settings;
    int id;
    int userId;
    @Mock
    private User user;

    private Settings settingsTwo;


    boolean notificationIsEnabled;

    @BeforeEach
    void setup(){
        settings = new Settings();
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

    @Test
    void testEqualsSameObject() {
        assertTrue(settings.equals(settings));
        assertFalse(settings.equals(settingsTwo));
    }

    @Test
    void testEqualNull() {
        assertFalse(settings.equals(null));
    }

    @Test
    void testFalseClass() {
        assertFalse(settings.equals(SettingsDTO.class));
    }

    @Test
    void equalFields() {
        settingsTwo = new Settings();
        settingsTwo.setUser(new User());
        settingsTwo.setNotificationIsEnabled(true);
        settingsTwo.setId(5);
        assertFalse(settings.equals(settingsTwo));
        settings.setId(5);
        assertFalse(settings.equals(settingsTwo));
        assertEquals(settings.getId() , settings.getId());
        assertFalse(settings.equals(settingsTwo));
        settings.setUser(user);
        settingsTwo.setUser(user);
        assertEquals(settings.getUser(), settingsTwo.getUser());
        assertFalse(settings.getNotificationIsEnabled());
        assertTrue(settingsTwo.getNotificationIsEnabled());
        assertFalse(settings.equals(settingsTwo));
        settingsTwo.setNotificationIsEnabled(false);
        assertTrue(settings.equals(settingsTwo));
    }
}
