package org.hbrs.se2.project.coll.control.builder;

import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.dtos.impl.UserDTOImpl;
import org.hbrs.se2.project.coll.entities.Address;

import java.time.LocalDate;

public class UserDTOBuilder {

    private UserDTOImpl userDTO;

    public static UserDTOBuilder please() {
        return new UserDTOBuilder();
    }

    public UserDTOBuilder createEmptyUser() {
        userDTO = new UserDTOImpl();
        userDTO.setAddress(new Address());
        return this;
    }

    public UserDTOBuilder createDefaultTestStudentUserWithFullData() {
        userDTO = new UserDTOImpl();
        userDTO.setSalutation("Herr");
        userDTO.setTitle("Dr.");
        userDTO.setFirstName("Hans");
        userDTO.setLastName("Meier");
        userDTO.setDateOfBirth(LocalDate.of(200,01,01));
        userDTO.setPhone("1234567890");
        Address address = new Address();
        address.setStreet("Straße");
        address.setHouseNumber("10");
        address.setPostalCode("12345");
        address.setCity("Bonn");
        address.setCountry("Deutschland");
        userDTO.setAddress(address);
        userDTO.setEmail("hans.meier@hbrs.de");
        userDTO.setPassword("Aa12#abcd");
        userDTO.setType("st");
        return this;
    }

    public UserDTOBuilder withSalutation(String salutation) {
        this.userDTO.setSalutation(salutation);
        return this;
    }

    public UserDTOBuilder withTitle(String title) {
        this.userDTO.setTitle(title);
        return this;
    }

    public UserDTOBuilder withFirstName(String firstname) {
        this.userDTO.setFirstName(firstname);
        return this;
    }

    public UserDTOBuilder withLastName(String lastname) {
        this.userDTO.setLastName(lastname);
        return this;
    }

    public UserDTOBuilder withAddress(String street, String housenumber, String postalcode, String city, String country) {
        Address address = new Address();
        address.setStreet(street);
        address.setHouseNumber(housenumber);
        address.setPostalCode(postalcode);
        address.setCity(city);
        address.setCountry(country);
        this.userDTO.setAddress(address);
        return this;
    }

    public UserDTOBuilder withStreet(String street) {
        this.userDTO.getAddress().setStreet(street);
        return this;
    }

    public UserDTOBuilder withHouseNumber(String nr) {
        this.userDTO.getAddress().setHouseNumber(nr);
        return this;
    }

    public UserDTOBuilder withPostalCode(String postalCode) {
        this.userDTO.getAddress().setPostalCode(postalCode);
        return this;
    }

    public UserDTOBuilder withCity(String city) {
        this.userDTO.getAddress().setCity(city);
        return this;
    }

    public UserDTOBuilder withCountry(String country) {
        this.userDTO.getAddress().setCountry(country);
        return this;
    }

    public UserDTOBuilder withPhone(String phone) {
        this.userDTO.setPhone(phone);
        return this;
    }

    public UserDTOBuilder withDateOfBirth(LocalDate birthdate) {
        this.userDTO.setDateOfBirth(birthdate);
        return this;
    }

    public UserDTOBuilder withEmail(String email) {
        this.userDTO.setEmail(email);
        return this;
    }

    public UserDTOBuilder withPassword(String password) {
        this.userDTO.setPassword(password);
        return this;
    }

    public UserDTOBuilder withId(int id) {
        this.userDTO.setId(id);
        return this;
    }

    public UserDTOBuilder withType(String type) {
        this.userDTO.setType(type);
        return this;
    }

    public UserDTOBuilder withEmptySalutation() {
        this.userDTO.setSalutation("");
        return this;
    }

    public UserDTOBuilder withEmptyTitle() {
        this.userDTO.setTitle("");
        return this;
    }

    public UserDTOBuilder withEmptyName() {
        this.userDTO.setFirstName("");
        this.userDTO.setLastName("");
        return this;
    }

    public UserDTOBuilder withEmptyFirstName() {
        this.userDTO.setFirstName("");
        return this;
    }

    public UserDTOBuilder withEmptyLastName() {
        this.userDTO.setLastName("");
        return this;
    }

    public UserDTOBuilder withoutAddress() {
        this.userDTO.setAddress(null);
        return this;
    }

    public UserDTOBuilder withEmptyStreet() {
        this.userDTO.getAddress().setStreet("");
        return this;
    }

    public UserDTOBuilder withEmptyHouseNumber() {
        this.userDTO.getAddress().setHouseNumber("");
        return this;
    }

    public UserDTOBuilder withEmptyPostalCode() {
        this.userDTO.getAddress().setPostalCode("");
        return this;
    }

    public UserDTOBuilder withEmptyCity() {
        this.userDTO.getAddress().setCity("");
        return this;
    }

    public UserDTOBuilder withEmptyCountry() {
        this.userDTO.getAddress().setCountry("");
        return this;
    }

    public UserDTOBuilder withEmptyPhone() {
        this.userDTO.setPhone("");
        return this;
    }

    public UserDTOBuilder withoutDateOfBirth() {
        this.userDTO.setDateOfBirth(null);
        return this;
    }

    public UserDTOBuilder withEmptyEmail() {
        this.userDTO.setEmail("");
        return this;
    }

    public UserDTOBuilder withEmptyPassword() {
        this.userDTO.setPassword("");
        return this;
    }

    public UserDTOBuilder withEmptyType() {
        this.userDTO.setType("");
        return this;
    }

    public UserDTO done() {
        return this.userDTO;
    }

}
