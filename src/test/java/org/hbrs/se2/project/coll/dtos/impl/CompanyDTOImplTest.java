package org.hbrs.se2.project.coll.dtos.impl;

import org.hbrs.se2.project.coll.entities.Address;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class CompanyDTOImplTest {

    CompanyDTOImpl companyDTO = new CompanyDTOImpl();

    private int id = 100;
    private String companyName = "Beispielfirma";
    @Mock
    private Address address;
    @Test
    void getId() {
        companyDTO.setId(id);
        assertEquals(id , companyDTO.getId());
    }

    @Test
    void getCompanyName() {
        companyDTO.setCompanyName(companyName);
        assertEquals(companyName , companyDTO.getCompanyName());
    }

    @Test
    void getAddress() {
    }

    @Test
    void setAddress() {
    }

    @Test
    void getPhoneNumber() {
    }

    @Test
    void setPhoneNumber() {
    }

    @Test
    void getFaxNumber() {
    }

    @Test
    void setFaxNumber() {
    }

    @Test
    void getEmail() {
    }
}