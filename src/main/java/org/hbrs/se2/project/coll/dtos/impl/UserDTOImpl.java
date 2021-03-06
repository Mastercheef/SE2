package org.hbrs.se2.project.coll.dtos.impl;

import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.Address;

import java.time.LocalDate;


public class UserDTOImpl implements UserDTO {

    private int id;
    private String salutation;
    private String title;
    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;
    private Address address;
    private String email;
    private String phone;
    private String password;
    private String type;

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public void setTitle(String title) { this.title = title; }

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public void setAddress(Address address) { this.address = address; }

    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password; }

    public void setType(String type) { this.type = type; }

    public void setId(int id) { this.id = id; }

    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String getSalutation() {
        return salutation;
    }

    @Override
    public String getTitle() { return title; }

    @Override
    public String getFirstName() {
        return this.firstname;
    }

    @Override
    public String getLastName() {
        return this.lastname;
    }

    @Override
    public LocalDate getDateOfBirth() { return this.dateOfBirth; }

    @Override
    public Address getAddress() { return this.address; }

    @Override
    public String getEmail() { return this.email; }

    @Override
    public String getPhone() { return this.phone; }

    @Override
    public String getPassword() { return this.password; }

    @Override
    public String getType() { return this.type; }

    @Override
    public int getId() { return this.id; }

    @Override
    public String toString() {
        return
                "id=" + id + '\'' +
                ", salutation='" + salutation + '\'' +
                ", title='" + title + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address=" + address + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", type='" + type + '\'' ;
    }
}
