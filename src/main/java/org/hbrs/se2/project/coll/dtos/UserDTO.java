package org.hbrs.se2.project.coll.dtos;

import org.hbrs.se2.project.coll.entities.Address;

import java.time.LocalDate;


public interface UserDTO {
    public String getSalutation();
    public String getTitle();
    public String getFirstName();
    public String getLastName();
    public Address getAddress();
    public String getPhone();
    public LocalDate getDateOfBirth();
    public String getEmail();
    public String getPassword();
    public String getType();
    public int getId();
}
