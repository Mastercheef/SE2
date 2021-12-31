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
    public void setupAddress() {
        address.setCountry("DE");
        address.setCity("Musterstadt");
        address.setStreet("Musterstraße");
        address.setId(1234);
        address.setPostalCode("12345");
        address.setHouseNumber("1");
    }

    String type = "st";
    String salutation = "Herr";
    String title = "Dr.";
    String firstName = "Max";
    String lastName = "Mustermann";
    LocalDate date = LocalDate.of(1971,2,1);
    String eMail = "max@mustermann.de";
    String phoneNumber = "0123456789";
    String password = "password";
    int id = 12;


    @Test
    void getType() {
        userDTO.setType(type);
        assertEquals("st", userDTO.getType());
    }

    @Test
    void getSalutation() {
        userDTO.setSalutation(salutation);
        assertEquals("Herr" , userDTO.getSalutation());

    }

    @Test
    void getTitle() {
        userDTO.setTitle(title);
        assertEquals("Dr." , userDTO.getTitle());
    }

    @Test
    void getFirstName() {
        userDTO.setFirstName(firstName);
        assertEquals("Max" ,userDTO.getFirstName());
    }

    @Test
    void getLastName() {
        userDTO.setLastName(lastName);
        assertEquals("Mustermann" , userDTO.getLastName());
    }

    @Test
    void getDateOfBirth() {
        userDTO.setDateOfBirth(date);
        assertEquals(1, userDTO.getDateOfBirth().getDayOfMonth());
        assertEquals(2, userDTO.getDateOfBirth().getMonthValue());
        assertEquals(1971, userDTO.getDateOfBirth().getYear());
    }

    @Test
    void getAddress() {
        userDTO.setAddress(address);
        assertEquals("Musterstraße" ,userDTO.getAddress().getStreet());
        assertEquals("1" ,userDTO.getAddress().getHouseNumber());
        assertEquals("12345" ,userDTO.getAddress().getPostalCode());
        assertEquals("Musterstadt" ,userDTO.getAddress().getCity());
    }

    @Test
    void getEmail() {
        userDTO.setEmail(eMail);
        assertEquals("max@mustermann.de" ,userDTO.getEmail());

    }

    @Test
    void getPhone() {
        userDTO.setPhone(phoneNumber);
        assertEquals("0123456789" ,userDTO.getPhone());
    }

    @Test
    void getPassword() {
        userDTO.setPassword(password);
        assertEquals("password" ,userDTO.getPassword());
    }

    @Test
    void getId() {
        userDTO.setId(id);
        assertEquals(12 ,userDTO.getId());
    }

    @Test
    void testToString() {
        userDTO.setType(type);
        userDTO.setSalutation(salutation);
        userDTO.setTitle(title);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setDateOfBirth(date);
        userDTO.setAddress(address);
        userDTO.setEmail(eMail);
        userDTO.setPhone(phoneNumber);
        userDTO.setPassword(password);
        userDTO.setId(id);
        assertEquals("id=12', salutation='Herr', title='Dr.', firstname='Max', lastname='Mustermann', dateOfBirth=1971-02-01, address=Musterstraße 1\n" +
                "12345 Musterstadt\n" +
                "DE', email='max@mustermann.de', phone='0123456789', type='st'", userDTO.toString());
    }
}