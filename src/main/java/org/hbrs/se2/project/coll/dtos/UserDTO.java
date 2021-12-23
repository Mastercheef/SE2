package org.hbrs.se2.project.coll.dtos;

import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.Settings;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.Set;

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
