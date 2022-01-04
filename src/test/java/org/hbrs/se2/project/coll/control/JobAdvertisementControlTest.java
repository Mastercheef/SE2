package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.factories.JobFactory;
import org.hbrs.se2.project.coll.dtos.JobAdvertisementDTO;
import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.hbrs.se2.project.coll.repository.CompanyRepository;
import org.hbrs.se2.project.coll.repository.ContactPersonRepository;
import org.hbrs.se2.project.coll.repository.JobAdvertisementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobAdvertisementControlTest {

    @InjectMocks
    JobAdvertisementControl jobAdvertisementControl;

    @Mock
    JobAdvertisementRepository jobAdvertisementRepository;
    @Mock
    CompanyRepository companyRepository;
    @Mock
    ContactPersonRepository contactPersonRepository;
    @Mock
    JobAdvertisementDTO jobAdvertisementDTO;
    @Mock
    JobAdvertisement jobAdvertisement;



    @Test
    void getJob() {
        when(jobAdvertisementRepository.findJobAdvertisementById(100)).thenReturn(jobAdvertisement);
        assertTrue(jobAdvertisementControl.getJob(100) instanceof JobAdvertisement);
        assertEquals(jobAdvertisement, jobAdvertisementControl.getJob(100));
    }

    @Test
    void createJobDTO() {

    }

    @Test
    void getCompanyName() {
    }

    @Test
    void getCompanyAddress() {
    }

    @Test
    void getCompanyPhoneNumber() {
    }

    @Test
    void getContactPersonName() {
    }

    @Test
    void getContactPersonEmail() {
    }

    @Test
    void getCompanyId() {
    }

    @Test
    void getAllJobs() {
    }

    @Test
    void filterJobs() {
    }

    @Test
    void testFilterJobs() {
    }

    @Test
    void testFilterJobs1() {
    }

    @Test
    void testFilterJobs2() {
    }

    @Test
    void filterCompanies() {
    }
}