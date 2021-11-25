package org.hbrs.se2.project.coll.dtos;

import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.Role;

import java.time.LocalDate;
import java.util.*;

public interface UserDTO {
    public int getId();
    public String getFirstName();
    public String getLastName();
    public Address getAddress();
    public String getPhone();
    public LocalDate getDateOfBirth();
    public String getEmail();
    public String getPassword();
    public String getUserId();
    public List<RoleDTO> getRoles();

}
