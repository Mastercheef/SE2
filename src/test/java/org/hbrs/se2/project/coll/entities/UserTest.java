package org.hbrs.se2.project.coll.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User userOne = new User();
    private User userTwo = new User();
    private User userThree = new User();
    private Address address = new Address();
    @BeforeEach
    void setUp() {
        userOne.setId(197);
        userOne.setSalutation("Herr");
        userOne.setTitle("Dr.");
        userOne.setFirstName("Max");
        userOne.setLastName("Mustermann");
        address.setPostalCode("12345");
        address.setStreet("Musterstrasse");
        address.setCity("Musterstadt");
        address.setCountry("Deutschland");
        address.setHouseNumber("1");
        userOne.setAddress(address);
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
        address.setPostalCode("54321");
        address.setStreet("Testerstraße");
        address.setCity("Testerstadt");
        address.setCountry("Schweiz");
        address.setHouseNumber("2");
        userTwo.setAddress(address);
        userTwo.setPhone("9876543210");
        userTwo.setDateOfBirth(LocalDate.of(2001,1,1));
        userTwo.setEmail("maxina@gmx.de");
        userTwo.setType("!st");
        userTwo.setPassword("passwd2");

        userOne.setId(199);
        userOne.setSalutation("Herr");
        userOne.setTitle("Dr.");
        userOne.setFirstName("Max");
        userOne.setLastName("Mustermann");
        address.setPostalCode("12345");
        address.setStreet("Musterstrasse");
        address.setCity("Musterstadt");
        address.setCountry("Deutschland");
        address.setHouseNumber("1");
        userOne.setAddress(address);
        userOne.setPhone("0123456789");
        userOne.setDateOfBirth(LocalDate.of(2002,1,1));
        userOne.setEmail("max@gmx.de");
        userOne.setType("st");
        userOne.setPassword("passwd");

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
        assertNotEquals(null, userOne);
    }

    @Test
    void testOtherClass() {
        assertNotEquals(userOne.getClass(), StudentUser.class);
    }

    @Test
    void testToString() {
        assertEquals("199, Herr, Dr., Max, Mustermann, 2002-01-01, 0123456789, Musterstrasse 1\n" +
                "12345 Musterstadt\n" +
                "Deutschland, max@gmx.de, st", userOne.toString());
    }
}