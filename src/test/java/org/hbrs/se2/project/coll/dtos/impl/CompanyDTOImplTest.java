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
    private Address address = new Address();
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
        companyDTO.setAddress(address);
        assertNotNull(companyDTO.getAddress());
    }

    //TODO Does this need to be int?
    @Test
    void getPhoneNumber() {
        companyDTO.setPhoneNumber(012345);
        assertEquals(012345 , companyDTO.getPhoneNumber());

    }


    //TODO Does this need to be int?
    @Test
    void getFaxNumber() {
        companyDTO.setFaxNumber(54321);
        assertEquals(54321 , companyDTO.getFaxNumber());
    }


    @Test
    void getEmail() {
        companyDTO.setEmail("max@mustermann.de");
        assertEquals("max@mustermann.de" , companyDTO.getEmail());
    }

    @Test
    void getWebsite() {
        companyDTO.setWebsite("max.de");
        assertEquals("max.de" , companyDTO.getWebsite());
    }
    @Test
    void getDescription() {
        companyDTO.setDescription("Coole Description");
        assertEquals("Coole Description" , companyDTO.getDescription());
    }

}