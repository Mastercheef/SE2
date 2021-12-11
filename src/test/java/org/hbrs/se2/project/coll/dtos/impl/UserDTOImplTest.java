package org.hbrs.se2.project.coll.dtos.impl;

import org.hbrs.se2.project.coll.entities.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;


class UserDTOImplTest {


    private UserDTOImpl userDTO =  new UserDTOImpl();


    private Address address = new Address();

    @BeforeEach
    private  void setUp() {
        address.setCountry("DE");
        address.setCity("Musterstadt");
        address.setStreet("Musterstraße");
        address.setId(1234);
        address.setPostalCode("12345");
        address.setHouseNumber("1");
        userDTO.setSalutation("Herr");
        userDTO.setTitle("Dr.");
        userDTO.setFirstName("Max");
        userDTO.setLastName("Mustermann");
        userDTO.setDateOfBirth(LocalDate.of(1971,1,1));
        userDTO.setAddress(address);
        userDTO.setType("st");
        userDTO.setEmail("max@mustermann.de");
        userDTO.setId(12);
        userDTO.setPhone("0123456789");
    }
    @Test
    void setType() {

        assertEquals(userDTO.getType(), userDTO.getType());


    }

    @Test
    void testToString() {
        assertEquals("id=12', salutation='Herr', title='Dr.', firstname='Max', lastname='Mustermann', dateOfBirth=1971-01-01, address=Musterstraße 1\n" +
                "12345 Musterstadt\n" +
                "DE', email='max@mustermann.de', phone='0123456789', type='st'", userDTO.toString());
    }
}