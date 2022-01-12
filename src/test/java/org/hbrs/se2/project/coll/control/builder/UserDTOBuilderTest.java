package org.hbrs.se2.project.coll.control.builder;

import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.dtos.impl.UserDTOImpl;
import org.hbrs.se2.project.coll.entities.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDTOBuilderTest {

    UserDTOImpl userDTOFullData;

    String street = "Straße";
    String postalcode = "12345";
    String country = "Deutschland";

    @BeforeEach
    void setUp() {
        userDTOFullData = new UserDTOImpl();
        userDTOFullData.setId(1);
        userDTOFullData.setSalutation("Herr");
        userDTOFullData.setTitle("Dr.");
        userDTOFullData.setFirstName("Hans");
        userDTOFullData.setLastName("Meier");
        Address address = new Address();
        address.setStreet(street);
        address.setHouseNumber("10");
        address.setPostalCode(postalcode);
        address.setCity("Bonn");
        address.setCountry(country);
        userDTOFullData.setAddress(address);
        userDTOFullData.setPhone("1234567890");
        userDTOFullData.setDateOfBirth(LocalDate.of(2000,01,01));
        userDTOFullData.setEmail("hans.meier@hbrs.de");
        userDTOFullData.setPassword("password");
        userDTOFullData.setType("st");

    }

    @Test
    void checkUserBuilderWithSetter() {
        UserDTO dtoFromBuilder = UserDTOBuilder
                .please()
                .createEmptyUser()
                .withId(1)
                .withSalutation("Herr")
                .withTitle("Dr.")
                .withFirstName("Hans")
                .withLastName("Meier")
                .withAddress(street, "10", postalcode, "Bonn", country)
                .withPhone("1234567890")
                .withDateOfBirth(LocalDate.of(2000,01,01))
                .withEmail("hans.meier@hbrs.de")
                .withPassword("password")
                .withType("st")
                .done();

        assertEquals(userDTOFullData.getSalutation(), dtoFromBuilder.getSalutation());
        assertEquals(userDTOFullData.getTitle(), dtoFromBuilder.getTitle());
        assertEquals(userDTOFullData.getFirstName(), dtoFromBuilder.getFirstName());
        assertEquals(userDTOFullData.getLastName(), dtoFromBuilder.getLastName());
        assertEquals(userDTOFullData.getAddress().getStreet(), dtoFromBuilder.getAddress().getStreet());
        assertEquals(userDTOFullData.getAddress().getHouseNumber(), dtoFromBuilder.getAddress().getHouseNumber());
        assertEquals(userDTOFullData.getAddress().getPostalCode(), dtoFromBuilder.getAddress().getPostalCode());
        assertEquals(userDTOFullData.getAddress().getCity(), dtoFromBuilder.getAddress().getCity());
        assertEquals(userDTOFullData.getAddress().getCountry(), dtoFromBuilder.getAddress().getCountry());
        assertEquals(userDTOFullData.getPhone(), dtoFromBuilder.getPhone());
        assertEquals(userDTOFullData.getDateOfBirth(), dtoFromBuilder.getDateOfBirth());
        assertEquals(userDTOFullData.getEmail(), dtoFromBuilder.getEmail());
        assertEquals(userDTOFullData.getPassword(), dtoFromBuilder.getPassword());
        assertEquals(userDTOFullData.getType(), dtoFromBuilder.getType());
    }

    @Test
    void checkUserBuilderWithSetterAddress() {

        UserDTO dtoFromBuilder = UserDTOBuilder
                .please()
                .createEmptyUser()
                .withStreet(street)
                .withHouseNumber("10")
                .withPostalCode(postalcode)
                .withCity("Bonn")
                .withCountry(country)
                .done();

        assertEquals(userDTOFullData.getAddress().getStreet(), dtoFromBuilder.getAddress().getStreet());
        assertEquals(userDTOFullData.getAddress().getHouseNumber(), dtoFromBuilder.getAddress().getHouseNumber());
        assertEquals(userDTOFullData.getAddress().getPostalCode(), dtoFromBuilder.getAddress().getPostalCode());
        assertEquals(userDTOFullData.getAddress().getCity(), dtoFromBuilder.getAddress().getCity());
        assertEquals(userDTOFullData.getAddress().getCountry(), dtoFromBuilder.getAddress().getCountry());
    }

    @Test
    void checkUserBuilderWithoutSetter() {

        UserDTOImpl emptyUser = new UserDTOImpl();
        emptyUser.setSalutation("");
        emptyUser.setTitle("");
        emptyUser.setFirstName("");
        emptyUser.setLastName("");
        emptyUser.setPhone("");
        emptyUser.setEmail("");
        emptyUser.setPassword("");
        emptyUser.setType("");

        UserDTO dtoFromBuilder = UserDTOBuilder
                .please()
                .createDefaultTestUserWithFullData()
                .withEmptySalutation()
                .withEmptyTitle()
                .withEmptyFirstName()
                .withEmptyLastName()
                .withoutAddress()
                .withEmptyPhone()
                .withoutDateOfBirth()
                .withEmptyEmail()
                .withEmptyPassword()
                .withEmptyType()
                .done();

        assertEquals(emptyUser.getSalutation(), dtoFromBuilder.getSalutation());
        assertEquals(emptyUser.getTitle(), dtoFromBuilder.getTitle());
        assertEquals(emptyUser.getFirstName(), dtoFromBuilder.getFirstName());
        assertEquals(emptyUser.getLastName(), dtoFromBuilder.getLastName());
        assertEquals(emptyUser.getAddress(), dtoFromBuilder.getAddress());
        assertEquals(emptyUser.getPhone(), dtoFromBuilder.getPhone());
        assertEquals(emptyUser.getDateOfBirth(), dtoFromBuilder.getDateOfBirth());
        assertEquals(emptyUser.getEmail(), dtoFromBuilder.getEmail());
        assertEquals(emptyUser.getPassword(), dtoFromBuilder.getPassword());
        assertEquals(emptyUser.getType(), dtoFromBuilder.getType());
    }


    @Test
    void checkUserBuilderWithEmptySetter() {

        UserDTOImpl emptyUser = new UserDTOImpl();
        emptyUser.setFirstName("");
        emptyUser.setLastName("");
        Address address = new Address();
        address.setStreet("");
        address.setHouseNumber("");
        address.setPostalCode("");
        address.setCity("");
        address.setCountry("");
        emptyUser.setAddress(address);

        UserDTO dtoFromBuilder = UserDTOBuilder
                .please()
                .createDefaultTestUserWithFullData()
                .withEmptySalutation()
                .withEmptyTitle()
                .withEmptyName()
                .withEmptyStreet()
                .withEmptyHouseNumber()
                .withEmptyPostalCode()
                .withEmptyCity()
                .withEmptyCountry()
                .withEmptyPhone()
                .withoutDateOfBirth()
                .withEmptyEmail()
                .withEmptyPassword()
                .withEmptyType()
                .done();

        assertEquals(emptyUser.getFirstName(), dtoFromBuilder.getFirstName());
        assertEquals(emptyUser.getLastName(), dtoFromBuilder.getLastName());
        assertEquals(emptyUser.getAddress().getStreet(), dtoFromBuilder.getAddress().getStreet());
        assertEquals(emptyUser.getAddress().getHouseNumber(), dtoFromBuilder.getAddress().getHouseNumber());
        assertEquals(emptyUser.getAddress().getPostalCode(), dtoFromBuilder.getAddress().getPostalCode());
        assertEquals(emptyUser.getAddress().getCity(), dtoFromBuilder.getAddress().getCity());
        assertEquals(emptyUser.getAddress().getCountry(), dtoFromBuilder.getAddress().getCountry());
    }

}
