package org.hbrs.se2.project.coll.dtos;

import org.hbrs.se2.project.coll.entities.Company;

public interface ContactPersonDTO extends UserDTO {
    public Company getCompany();
    public String getRole();
}
