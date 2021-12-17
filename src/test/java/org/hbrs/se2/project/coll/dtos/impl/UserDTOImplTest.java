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
        userDTO.setDateOfBirth(LocalDate.of(1971,2,1));
        userDTO.setAddress(address);
        userDTO.setType("st");
        userDTO.setEmail("max@mustermann.de");
        userDTO.setId(12);
        userDTO.setPhone("0123456789");
        userDTO.setPassword("password");

    }
    @Test
    void getType() {

        assertEquals("st", userDTO.getType());
    }

    @Test
    void testToString() {
        assertEquals("id=12', salutation='Herr', title='Dr.', firstname='Max', lastname='Mustermann', dateOfBirth=1971-02-01, address=Musterstraße 1\n" +
                "12345 Musterstadt\n" +
                "DE', email='max@mustermann.de', phone='0123456789', type='st'", userDTO.toString());
    }

    @Test
    void getSalutation() {
        assertEquals("Herr" , userDTO.getSalutation());

    }

    @Test
    void getTitle() {
        assertEquals("Dr." , userDTO.getTitle());
    }

    @Test
    void getFirstName() {
        assertEquals("Max" ,userDTO.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals("Mustermann" , userDTO.getLastName());
    }

    @Test
    void getDateOfBirth() {
        assertEquals(1, userDTO.getDateOfBirth().getDayOfMonth());
        assertEquals(2, userDTO.getDateOfBirth().getMonthValue());
        assertEquals(1971, userDTO.getDateOfBirth().getYear());
    }

    @Test
    void getAddress() {
        assertEquals("Musterstraße" ,userDTO.getAddress().getStreet());
        assertEquals("1" ,userDTO.getAddress().getHouseNumber());
        assertEquals("12345" ,userDTO.getAddress().getPostalCode());
        assertEquals("Musterstadt" ,userDTO.getAddress().getCity());



    }

    @Test
    void getEmail() {
        assertEquals("max@mustermann.de" ,userDTO.getEmail());

    }

    @Test
    void getPhone() {
        assertEquals("0123456789" ,userDTO.getPhone());
    }

    @Test
    void getPassword() {
        assertEquals("password" ,userDTO.getPassword());
    }

    @Test
    void getId() {
        assertEquals("12" ,userDTO.getId());
    }
}