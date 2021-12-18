package org.hbrs.se2.project.coll.dtos.impl;

import org.hbrs.se2.project.coll.dtos.ContactPersonDTO;
import org.hbrs.se2.project.coll.entities.Company;

public class ContactPersonDTOImpl extends UserDTOImpl implements ContactPersonDTO {
    private Company company;
    private String role;

    public void setCompanyDTO(Company companyDTO) { this.company = companyDTO; }

    public void setRole(String role) { this.role = role; }

    @Override
    public Company getCompany() {
        return company;
    }

    @Override
    public String getRole() {
        return role;
    }
}
