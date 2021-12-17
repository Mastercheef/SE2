package org.hbrs.se2.project.coll.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User userOne = new User();
    private User userTwo = new User();
    private Address addressOne = new Address();
    private Address addressTwo = new Address();
    @BeforeEach
    void setUp() {
        userOne.setId(197);
        userOne.setSalutation("Herr");
        userOne.setTitle("Dr.");
        userOne.setFirstName("Max");
        userOne.setLastName("Mustermann");
        addressOne.setPostalCode("12345");
        addressOne.setStreet("Musterstrasse");
        addressOne.setCity("Musterstadt");
        addressOne.setCountry("Deutschland");
        addressOne.setHouseNumber("1");
        userOne.setAddress(addressOne);
        userOne.setPhone("0123456789");
        userOne.setDateOfBirth(LocalDate.of(2000,1,1));
        userOne.setEmail("max@gmx.de");
        userOne.setType("st");
        userOne.setPassword("passwd");

        userTwo.setId(198);
        userTwo.setSalutation("Frau");
        userTwo.setTitle("Dr.");
        userTwo.setFirstName("Maxina");
        userTwo.setLastName("Testermann");
        addressTwo.setPostalCode("54321");
        addressTwo.setStreet("Testerstraße");
        addressTwo.setCity("Testerstadt");
        addressTwo.setCountry("Schweiz");
        addressTwo.setHouseNumber("2");
        userTwo.setAddress(addressTwo);
        userTwo.setPhone("9876543210");
        userTwo.setDateOfBirth(LocalDate.of(2001,1,1));
        userTwo.setEmail("maxina@gmx.de");
        userTwo.setType("!st");
        userTwo.setPassword("passwd2");


    }
    @Test
    void testEqualsSameUser() {

        assertEquals(userOne, userOne);
    }

    @Test
    void testEqualsDifferentUser() {

        assertNotEquals(userOne, userTwo);
    }

    @Test
    void testEqualNull() {
        assertFalse(userOne.equals(null));
        assertTrue(userOne.equals(Object.class));
    }

    @Test
    void testEqualClass() {
        assertFalse(userOne.equals(StudentUser.class));
        assertFalse(userOne.equals(User.class));
    }

    @Test
    void testEqualsMethod() {
        assertNotEquals(userOne.getAddress(),userTwo.getAddress());
        assertFalse(userOne.equals(userTwo));
        userTwo.setFirstName(userOne.getFirstName());
        assertFalse(userOne.equals(userTwo));
        userTwo.setLastName(userOne.getLastName());
        assertFalse(userOne.equals(userTwo));
        userTwo.setAddress(userOne.getAddress());
        assertFalse(userOne.equals(userTwo));
        userTwo.setPhone(userOne.getPhone());
        assertFalse(userOne.equals(userTwo));
        userTwo.setDateOfBirth(userOne.getDateOfBirth());
        assertFalse(userOne.equals(userTwo));
        userTwo.setEmail(userOne.getEmail());
        assertFalse(userOne.equals(userTwo));
        userTwo.setId(userOne.getId());
        assertTrue(userOne.equals(userTwo));

    }
    @Test
    void testToString() {
        assertEquals("197, Herr, Dr., Max, Mustermann, 2000-01-01, 0123456789, Testerstraße 2\n" +
                "54321 Testerstadt\n" +
                "Schweiz, max@gmx.de, st", userOne.toString());
    }
}