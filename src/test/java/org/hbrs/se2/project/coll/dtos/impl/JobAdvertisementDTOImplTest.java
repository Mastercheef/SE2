package org.hbrs.se2.project.coll.dtos.impl;

import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.ContactPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class JobAdvertisementDTOImplTest {

    @Mock
    private Address address = new Address();

    @Mock
    private ContactPerson contactPerson = new ContactPerson();
    private RecruitmentAdvertisingDTOImpl recruitmentAdvertisingDTO;
    @BeforeEach
    void setUp() {

        recruitmentAdvertisingDTO = new RecruitmentAdvertisingDTOImpl();
        recruitmentAdvertisingDTO.setTemporaryEmployment(true);
        recruitmentAdvertisingDTO.setTypeOfEmployment("Vollzeit");
        recruitmentAdvertisingDTO.setWorkingHours((short) 40);
        recruitmentAdvertisingDTO.setRequirements("Lorem Ipsum");
        recruitmentAdvertisingDTO.setAddress(address);
        recruitmentAdvertisingDTO.setStartOfWork(LocalDate.of(1999,1,1));
        recruitmentAdvertisingDTO.setEndOfWork(LocalDate.of(2000,12,31));
        recruitmentAdvertisingDTO.setJobDescription("Lorem Ipsum");
        recruitmentAdvertisingDTO.setJobTitle("Datenbank-Experte");
        recruitmentAdvertisingDTO.setId(111);
        recruitmentAdvertisingDTO.setContactPerson(contactPerson);
    }

    @Test
    void getTemporaryEmployment() {
        assertTrue(recruitmentAdvertisingDTO.getTemporaryEmployment());
    }

    @Test
    void getTypeOfEmployment() {
        assertEquals("Vollzeit" , recruitmentAdvertisingDTO.getTypeOfEmployment());
    }

    @Test
    void getWorkingHours() {
        assertEquals(Integer.valueOf(40) , Integer.valueOf(recruitmentAdvertisingDTO.getWorkingHours()));
    }

    @Test
    void getRequirements() {
        assertEquals("Lorem Ipsum" , recruitmentAdvertisingDTO.getRequirements());
    }

    @Test
    void getAddress() {
        assertNotNull(recruitmentAdvertisingDTO.getAddress());
    }

    @Test
    void getStartOfWork() {
        assertEquals(LocalDate.of(1999,1,1) , recruitmentAdvertisingDTO.getStartOfWork() );
    }

    @Test
    void getEndOfWork() {
        assertEquals(LocalDate.of(2000,12,31) , recruitmentAdvertisingDTO.getEndOfWork());
    }

    @Test
    void getContactPerson() {
        assertNotNull(recruitmentAdvertisingDTO.getContactPerson());
    }

    @Test
    void getJobDescription() {
        assertEquals("Lorem Ipsum" ,recruitmentAdvertisingDTO.getJobDescription());
    }

    @Test
    void getJobTitle() {
        assertEquals("Datenbank-Experte" , recruitmentAdvertisingDTO.getJobTitle());
    }

    @Test
    void getId() {
        assertEquals(111 , recruitmentAdvertisingDTO.getId());
    }
}