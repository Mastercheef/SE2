package org.hbrs.se2.project.coll.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class JobAdvertisementTest {

    int contactId;
    int studentId;
    StudentUser studentUser = new StudentUser();
    JobAdvertisement jobAdvertisement = new JobAdvertisement();
    JobApplication jobApplication = new JobApplication();

    int id;
    boolean temporaryEmployment;
    short workingHours;
    String typeOfEmployment;
    LocalDate startOfWork;
    LocalDate endOfWork;
    String jobDescription;
    int salary;
    String jobTitle;
    ContactPerson contactPerson = new ContactPerson();
    Set<JobApplication> applicants = new HashSet<>();

    @BeforeEach
    void setup(){
        id = 10;
        temporaryEmployment = true;
        workingHours = 6;
        typeOfEmployment = "basic";
        startOfWork = LocalDate.of(2021,12, 29);
        endOfWork = LocalDate.of(2022, 3, 20);
        contactId = 5;
        contactPerson.setId(5);
        jobDescription = "die";
        salary = 450;
        jobTitle = "Assistent";
        studentId = 20;
        studentUser.setId(studentId);
        applicants.add(jobApplication);
    }

    @Test
    void testGetId(){
        jobAdvertisement.setId(id);
        assertEquals(10, jobAdvertisement.getId());
    }

    @Test
    void testGetTemporaryEmployment(){
        jobAdvertisement.setTemporaryEmployment(temporaryEmployment);
        assertTrue(jobAdvertisement.getTemporaryEmployment());
    }

    @Test
    void testGetWorkingHours(){
        jobAdvertisement.setWorkingHours(workingHours);
        assertEquals(6, jobAdvertisement.getWorkingHours());
    }

    @Test
    void testGetTypeOfEmployment(){
        jobAdvertisement.setTypeOfEmployment(typeOfEmployment);
        assertEquals("basic", jobAdvertisement.getTypeOfEmployment());
    }

    @Test
    void testGetStartOfWork(){
        jobAdvertisement.setStartOfWork(startOfWork);
        assertEquals(LocalDate.of(2021, 12, 29), jobAdvertisement.getStartOfWork());
    }

    @Test
    void testGetEndOfWork(){
        jobAdvertisement.setEndOfWork(endOfWork);
        assertEquals(LocalDate.of(2022, 3, 20), jobAdvertisement.getEndOfWork());
    }

    @Test
    void testGetJobDescription(){
        jobAdvertisement.setJobDescription(jobDescription);
        assertEquals("die", jobAdvertisement.getJobDescription());
    }

    @Test
    void testGetSalary(){
        jobAdvertisement.setSalary(salary);
        assertEquals(450, jobAdvertisement.getSalary());
    }

    @Test
    void testGetJobTitle(){
        jobAdvertisement.setJobTitle(jobTitle);
        assertEquals("Assistent", jobAdvertisement.getJobTitle());
    }

    @Test
    void testGetContactPerson(){
        jobAdvertisement.setContactPerson(contactPerson);
        assertEquals(contactPerson, jobAdvertisement.getContactPerson());
    }

    @Test
    void testGetApplicants(){
        jobAdvertisement.setApplicants(applicants);
        assertEquals(applicants, jobAdvertisement.getApplicants());
        assertEquals(studentUser, jobAdvertisement.getApplicants().toArray()[0]);
    }
}
