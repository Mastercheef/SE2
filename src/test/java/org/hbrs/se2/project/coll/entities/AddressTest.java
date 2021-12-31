package org.hbrs.se2.project.coll.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    private Address address = new Address();

    private static final String POSTAL_CODE = "12345";
    private static final int ID = 20;
    private static final String COUNTRY = "Deutschland";
    private static final String STREET = "Musterstrasse";
    private static final String HOUSE_NUMBER = "1";
    @Test
    void testGetId(){
        address.setId(ID);
        assertEquals(ID, address.getId());
    }

    @Test
    void testGetPostal(){
        address.setPostalCode(POSTAL_CODE);
        assertEquals(POSTAL_CODE, address.getPostalCode());
    }

    @Test
    void testGetCountry(){
        address.setCountry(COUNTRY);
        assertEquals(COUNTRY, address.getCountry());
    }

    @Test
    void testGetStreet(){
        address.setStreet(STREET);
        assertEquals(STREET, address.getStreet());
    }

    @Test
    void testGetHouseNumber(){
        address.setHouseNumber(HOUSE_NUMBER);
        assertEquals(HOUSE_NUMBER, address.getHouseNumber());
    }

    @Test
    void testToString() {
        address.setPostalCode(POSTAL_CODE);
        address.setStreet(STREET);
        address.setCity("Musterstadt");
        address.setCountry(COUNTRY);
        address.setHouseNumber(HOUSE_NUMBER);

        assertEquals(
                "Musterstrasse 1\n" +
                "12345 Musterstadt\n" +
                "Deutschland",address.toString());
    }
}