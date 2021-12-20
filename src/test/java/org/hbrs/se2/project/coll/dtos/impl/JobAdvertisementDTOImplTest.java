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
    private JobAdvertisementDTOimpl job;
    private final LocalDate startOfWork           = LocalDate.of(2022, 1, 1);
    private final LocalDate endOfWork             = LocalDate.of(2022, 4, 1);
    private final ContactPerson contactPerson     = new ContactPerson();

    @BeforeEach
    void setUp() {

        job = new JobAdvertisementDTOimpl();
        job.setTemporaryEmployment(true);
        job.setTypeOfEmployment("Vollzeit");
        job.setWorkingHours((short) 40);
        job.setRequirements("C++, Java");
        job.setAddress(address);
        job.setStartOfWork(LocalDate.of(2025, 1, 1));
        job.setEndOfWork(LocalDate.of(2040, 1, 1));
        job.setContactPerson(contactPerson);
        job.setJobDescription("Eine weitere Beschreibung dieser tollen Stelle");
        job.setJobTitle("Vollzeitmitarbeiter für alles");
        job.setSalary(2500);
        job.setId(111);
    }

    @Test
    void getTemporaryEmployment() {
        assertTrue(job.getTemporaryEmployment());
    }

    @Test
    void getTypeOfEmployment() {
        assertEquals("Vollzeit", job.getTypeOfEmployment());
    }

    @Test
    void getWorkingHours() {
        assertEquals(40, job.getWorkingHours());
    }

    @Test
    void getRequirements() {
        assertEquals("C++, Java", job.getRequirements());
    }

    @Test
    void getAddress() {
        assertNotNull(job.getAddress());
    }

    @Test
    void getStartOfWork() {
        assertEquals(LocalDate.of(2025, 1, 1), job.getStartOfWork() );
    }

    @Test
    void getEndOfWork() {
        assertEquals(LocalDate.of(2040, 1, 1), job.getEndOfWork());
    }

    @Test
    void getContactPerson() {
        assertNotNull(job.getContactPerson());
    }

    @Test
    void getJobDescription() {
        assertEquals("Eine weitere Beschreibung dieser tollen Stelle", job.getJobDescription());
    }

    @Test
    void getJobTitle() {
        assertEquals("Vollzeitmitarbeiter für alles", job.getJobTitle());
    }

    @Test
    void getId() {
        assertEquals(111, job.getId());
    }

    @Test
    void typeOfEmployment() {
        String typeOfEmployment = "Praktikum";
        jobAdvertisementDTOimpl.setTypeOfEmployment(typeOfEmployment);
        assertEquals(typeOfEmployment, jobAdvertisementDTOimpl.getTypeOfEmployment());
    }

    @Test
    void workingHours() {
        short workingHours = (short) 20;
        jobAdvertisementDTOimpl.setWorkingHours(workingHours);
        assertEquals(workingHours, jobAdvertisementDTOimpl.getWorkingHours());
    }

    @Test
    void beginnOfJob() {
        jobAdvertisementDTOimpl.setStartOfWork(startOfWork);
        assertEquals(startOfWork, jobAdvertisementDTOimpl.getStartOfWork());
    }
    @Test
    void address() {
        jobAdvertisementDTOimpl.setAddress(address);
        assertEquals(address, jobAdvertisementDTOimpl.getAddress());
    }
    @Test
    void jobDescription() {
        String jobDescription = "Beschreibung des Jobs";
        jobAdvertisementDTOimpl.setJobDescription(jobDescription);
        assertEquals(jobDescription, jobAdvertisementDTOimpl.getJobDescription());
    }

    @Test
    void jobTitle() {
        String jobTitle = "Praktikum als Stift";
        jobAdvertisementDTOimpl.setJobTitle(jobTitle);
        assertEquals(jobTitle, jobAdvertisementDTOimpl.getJobTitle());
    }

    @Test
    void requirementForApplicants() {
        String requirements = "Viele";
        jobAdvertisementDTOimpl.setRequirements(requirements);
        assertEquals(requirements, jobAdvertisementDTOimpl.getRequirements());
    }

    @Test
    void temporaryEmployment() {
        boolean isTemporaryEmployment = true;
        jobAdvertisementDTOimpl.setTemporaryEmployment(isTemporaryEmployment);
        assertEquals(isTemporaryEmployment, jobAdvertisementDTOimpl.getTemporaryEmployment());
    }

    @Test
    void startOfWork() {
        jobAdvertisementDTOimpl.setStartOfWork(startOfWork);
        assertEquals(startOfWork, jobAdvertisementDTOimpl.getStartOfWork());
    }

    @Test
    void endOfWork() {
        jobAdvertisementDTOimpl.setEndOfWork(endOfWork);
        assertEquals(endOfWork, jobAdvertisementDTOimpl.getEndOfWork());
    }

    @Test
    void contactPerson() {
        jobAdvertisementDTOimpl.setContactPerson(contactPerson);
        assertEquals(contactPerson, jobAdvertisementDTOimpl.getContactPerson());
    }
    @Test
    void salary() {
        int salary = 1500;
        jobAdvertisementDTOimpl.setSalary(salary);
        assertEquals(salary, jobAdvertisementDTOimpl.getSalary());
    }
    @Test
    void id() {
        int id = 111;
        jobAdvertisementDTOimpl.setId(id);
        assertEquals(id, jobAdvertisementDTOimpl.getId());
    }
}
