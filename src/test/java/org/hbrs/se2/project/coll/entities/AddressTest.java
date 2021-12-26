package org.hbrs.se2.project.coll.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    private Address address = new Address();

    @Test
    void testGetId(){
        address.setId(20);
        assertEquals(20, address.getId());
    }

    @Test
    void testGetPostal(){
        address.setPostalCode("12345");
        assertEquals("12345", address.getPostalCode());
    }

    @Test
    void testGetCountry(){
        address.setCountry("Deutschland");
        assertEquals("Deutschland", address.getCountry());
    }

    @Test
    void testGetStreet(){
        address.setStreet("Musterstrasse");
        assertEquals("Musterstrasse", address.getStreet());
    }

    @Test
    void testGetHouseNumber(){
        address.setHouseNumber("1");
        assertEquals("1", address.getHouseNumber());
    }

    @Test
    void testToString() {
        address.setPostalCode("12345");
        address.setStreet("Musterstrasse");
        address.setCity("Musterstadt");
        address.setCountry("Deutschland");
        address.setHouseNumber("1");

        assertEquals(
                "Musterstrasse 1\n" +
                "12345 Musterstadt\n" +
                "Deutschland",address.toString());
    }
}