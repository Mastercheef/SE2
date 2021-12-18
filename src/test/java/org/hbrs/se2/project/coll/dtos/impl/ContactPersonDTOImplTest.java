package org.hbrs.se2.project.coll.dtos.impl;

import org.hbrs.se2.project.coll.entities.Company;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class ContactPersonDTOImplTest {

    ContactPersonDTOImpl contactPersonDTO = new ContactPersonDTOImpl();
    private String role = "st";
    @Mock
    Company company = new Company();
    @Test
    void getCompany() {
        contactPersonDTO.setCompanyDTO(company);
        assertNotNull(contactPersonDTO.getCompany());
    }

    @Test
    void getRole() {
        contactPersonDTO.setRole(role);
        assertEquals(role , contactPersonDTO.getRole());
    }
}