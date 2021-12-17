package org.hbrs.se2.project.coll.dtos.impl;

import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.ContactPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class JobAdvertisementDTOImplTest {

    JobAdvertisementDTOimpl jobAdvertisementDTOimpl = new JobAdvertisementDTOimpl();


    @Mock
    private Address address = new Address();

    @Mock
    private ContactPerson contactPerson = new ContactPerson();
    private RecruitmentAdvertisingDTOImpl recruitmentAdvertisingDTO;
    private String companyName = "CompanyName";
    private String formOfEmployment = "Vollzeit";
    private byte workingHours = 40;
    private String beginnOfJob = "1.1.2000";
    private String workingLocation = "Home Office";
    private String jobDescription = "Job Beschreibung";
    private String requirementForApplicants = "Viele!";
    private String businessAddress = "MustermannStraße";
    private boolean temporaryEmployment = false;
    private String dateOfTemporaryEmployment =  "2.2.2002";
    private String contactPersonString = "Herr Kontakt Person";
    private String emailAddress = "max@emailhbrs.de";

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

    @Test
    void companyName() {
        jobAdvertisementDTOimpl.setCompanyName(companyName);
        assertEquals(companyName , jobAdvertisementDTOimpl.getCompanyName());
    }

    @Test
    void FormOfEmployment() {
        jobAdvertisementDTOimpl.setFormOfEmployment(formOfEmployment);
        assertEquals(formOfEmployment , jobAdvertisementDTOimpl.getFormOfEmployment());
    }

    @Test
    void workingHours() {
        jobAdvertisementDTOimpl.setWorkingHours(workingHours);
        assertEquals(workingHours , jobAdvertisementDTOimpl.getWorkingHours());
    }

    @Test
    void beginnOfJob() {
        jobAdvertisementDTOimpl.setBeginnOfJob(beginnOfJob);
        assertEquals(beginnOfJob , jobAdvertisementDTOimpl.getBeginnOfJob());
    }
    @Test
    void WorkingLocation() {
        jobAdvertisementDTOimpl.setWorkingLocation(workingLocation);
        assertEquals(workingLocation , jobAdvertisementDTOimpl.getWorkingLocation());
    }
    @Test
    void jobDescription() {
        jobAdvertisementDTOimpl.setJobDescription(jobDescription);
        assertEquals(jobDescription , jobAdvertisementDTOimpl.getJobDescription());
    }

    @Test
    void requirementForApplicants() {
        jobAdvertisementDTOimpl.setRequirementForApplicants(requirementForApplicants);
        assertEquals(requirementForApplicants , jobAdvertisementDTOimpl.getRequirementForApplicants());
    }

    @Test
    void businessAdress() {
        jobAdvertisementDTOimpl.setBusinessAdress(businessAddress);
        assertEquals(businessAddress , jobAdvertisementDTOimpl.getBusinessAddress());
    }

    @Test
    void temporaryEmployment() {
        jobAdvertisementDTOimpl.setTemporaryEmployment(temporaryEmployment);
        assertEquals(temporaryEmployment , jobAdvertisementDTOimpl.getTemporaryEmployment());
    }

    @Test
    void DateOfTemporaryEmployment() {
        jobAdvertisementDTOimpl.setDateOfTemporaryEmployment(dateOfTemporaryEmployment);
        assertEquals(dateOfTemporaryEmployment, jobAdvertisementDTOimpl.getDateOfTemporaryEmployment());
    }
    @Test
    void contactPerson() {
        jobAdvertisementDTOimpl.setContactPerson(contactPersonString);
        assertEquals(contactPersonString , jobAdvertisementDTOimpl.getContactPerson());
    }
    @Test
    void emailAddress() {
        jobAdvertisementDTOimpl.setEmailAddress(emailAddress);
        assertEquals(emailAddress , jobAdvertisementDTOimpl.getEmailAddress());
    }
}