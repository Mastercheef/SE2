package org.hbrs.se2.project.coll.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    private Address address = new Address();
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