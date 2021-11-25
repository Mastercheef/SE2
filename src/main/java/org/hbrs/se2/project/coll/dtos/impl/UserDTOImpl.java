package org.hbrs.se2.project.coll.dtos.impl;

//import org.hbrs.se2.project.coll.dtos.RolleDTO;
import org.hbrs.se2.project.coll.dtos.RoleDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.Address;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

//import java.util.List;

public class UserDTOImpl implements UserDTO {

    private int id;
    private String firstname;
    private String lastname;
    private String graduation;
    private LocalDate dateOfBirth;
    private Address address;
    private String email;
    private String phone;
    private String password;
    private int userid;
    //private List<RoleDTO> roles;

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public void setAddress(Address address) { this.address = address; }

    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password; }

    public void setUserId(int userid) { this.userid = userid; }

    public void setPhone(String phone) { this.phone = phone; }


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
    public int getUserId() { return this.userid; }

    @Override
    public String toString() {
        return "UserDTOImpl{" +
                "id=" + userid +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                //", roles=" + roles +
                '}';
    }
}
