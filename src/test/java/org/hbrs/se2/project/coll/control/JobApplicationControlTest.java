package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.dtos.JobApplicationDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.dtos.impl.JobApplicationDTOImpl;
import org.hbrs.se2.project.coll.entities.*;
import org.hbrs.se2.project.coll.repository.JobApplicationRepository;
import org.hbrs.se2.project.coll.util.Globals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobApplicationControlTest {

    @InjectMocks
    JobApplicationControl jobApplicationControl;

    @Mock
    JobApplicationRepository jobApplicationRepository;

    @Mock
    JobApplicationDTO jobApplicationDTO;

    @Mock
    ContactPersonControl contactPersonControl;

    UserDTO userDTO = Mockito.mock(UserDTO.class);
    StudentUser studentUser = Mockito.mock(StudentUser.class);
    JobAdvertisement jobAdvertisement = Mockito.mock(JobAdvertisement.class);
    ContactPerson contactPerson = Mockito.mock(ContactPerson.class);
    Company company = Mockito.mock(Company.class);

    List<JobApplicationDTO> applications = new ArrayList<>();
    List<JobApplicationDTO> resultListDateRange = new ArrayList<>();
    List<JobApplicationDTO> resultListUsername = new ArrayList<>();
    List<JobApplicationDTO> resultListHeadline= new ArrayList<>();

    @BeforeEach
    void setUp() {
        StudentUser studentUser1 = new StudentUser();
        studentUser1.setFirstName("student");
        studentUser1.setLastName("match");
        JobAdvertisement jobAd1 = new JobAdvertisement();
        JobApplicationDTOImpl job1 = new JobApplicationDTOImpl();
        job1.setHeadline("Job1");
        job1.setStudentUser(studentUser1);
        job1.setDate(LocalDate.of(2021,1,1));
        job1.setJobAdvertisementDTO(jobAd1);

        StudentUser studentUser2 = new StudentUser();
        studentUser2.setFirstName("student");
        studentUser2.setLastName("match");
        JobAdvertisement jobAd2 = new JobAdvertisement();
        JobApplicationDTOImpl job2 = new JobApplicationDTOImpl();
        job2.setHeadline("Job2");
        job2.setStudentUser(studentUser2);
        job2.setDate(LocalDate.now());
        job2.setJobAdvertisementDTO(jobAd2);

        StudentUser studentUser3 = new StudentUser();
        studentUser3.setFirstName("student");
        studentUser3.setLastName("drei");
        JobAdvertisement jobAd3 = new JobAdvertisement();
        JobApplicationDTOImpl job3 = new JobApplicationDTOImpl();
        job3.setHeadline("Job3");
        job3.setStudentUser(studentUser3);
        job3.setDate(LocalDate.now());
        job3.setJobAdvertisementDTO(jobAd3);

        applications.add(job1);
        applications.add(job2);
        applications.add(job3);
        resultListDateRange.add(job2);
        resultListDateRange.add(job3);
        resultListUsername.add(job1);
        resultListUsername.add(job2);
        resultListHeadline.add(job1);
    }

    @Test
    void loadJobApplication() {
        when(jobApplicationRepository.findJobApplicationById(100)).thenReturn(jobApplicationDTO);
        assertEquals(jobApplicationDTO , jobApplicationControl.loadJobApplication(100));
        assertTrue(jobApplicationControl.loadJobApplication(100) instanceof  JobApplicationDTO);
    }

    @Test
    void testSsUserAllowedToAccessJobApplications() {
        when(jobApplicationRepository.findJobApplicationById(100)).thenReturn(jobApplicationDTO);
        when(studentUser.getId()).thenReturn(1);
        when(jobApplicationDTO.getStudentUser()).thenReturn(studentUser);
        jobApplicationControl.loadJobApplication(100);

        when(userDTO.getType()).thenReturn("st");
        when(userDTO.getId()).thenReturn(1);
        assertTrue(jobApplicationControl.isUserAllowedToAccessJobApplications(userDTO));

        when(userDTO.getType()).thenReturn("st");
        when(userDTO.getId()).thenReturn(10);
        assertFalse(jobApplicationControl.isUserAllowedToAccessJobApplications(userDTO));

        when(jobApplicationDTO.getJobAdvertisement()).thenReturn(jobAdvertisement);
        when(jobAdvertisement.getContactPerson()).thenReturn(contactPerson);
        when(contactPerson.getCompany()).thenReturn(company);
        when(company.getId()).thenReturn(10);
        when(userDTO.getType()).thenReturn("cp");
        when(jobApplicationControl.getContactPersonFromSessionUser(userDTO)).thenReturn(contactPerson);
        assertTrue(jobApplicationControl.isUserAllowedToAccessJobApplications(userDTO));
    }

    @Test
    void testGetContactPersonFromSessionUser() {
        ContactPerson contactPersonReturn = Mockito.mock(ContactPerson.class);
        when(userDTO.getId()).thenReturn(100);
        when(contactPersonControl.findContactPersonById(anyInt())).thenReturn(contactPersonReturn);
        assertEquals(contactPersonReturn, jobApplicationControl.getContactPersonFromSessionUser(userDTO));
    }

    @Test
    void testFilterApplicationsByHeadline() {
        List<JobApplicationDTO> filtered = jobApplicationControl.filterApplicationsByHeadline(null, "Gibts nicht");
        assertTrue(filtered.size() == 0);

        List<JobApplicationDTO> empty = new ArrayList<>();
        filtered = jobApplicationControl.filterApplicationsByHeadline(empty, "Headline");
        assertTrue(filtered.size() == 0);

        filtered = jobApplicationControl.filterApplicationsByHeadline(applications, "Job1");
        assertTrue(filtered.size() == 1);
        assertEquals(resultListHeadline, filtered);
    }

    @Test
    void testFilterApplicationsByUsername() {
        List<JobApplicationDTO> filtered = jobApplicationControl.filterApplicationsByUsername(null, "Gibts nicht");
        assertTrue(filtered.size() == 0);

        List<JobApplicationDTO> empty = new ArrayList<>();
        filtered = jobApplicationControl.filterApplicationsByUsername(empty, "Name");
        assertTrue(filtered.size() == 0);

        filtered = jobApplicationControl.filterApplicationsByUsername(applications, "match");
        assertTrue(filtered.size() == 2);
        assertEquals(resultListUsername, filtered);
    }

    @Test
    void testFilterApplicationsByDateRange() {
        List<JobApplicationDTO> filtered = jobApplicationControl.filterApplicationsByDateRange(null, Globals.DateRanges.DAY);
        assertTrue(filtered.size() == 0);

        List<JobApplicationDTO> empty = new ArrayList<>();
        filtered = jobApplicationControl.filterApplicationsByDateRange(empty, Globals.DateRanges.DAY);
        assertTrue(filtered.size() == 0);

        filtered = jobApplicationControl.filterApplicationsByDateRange(applications, Globals.DateRanges.DAY);
        assertTrue(filtered.size() == 2);
        assertEquals(resultListDateRange, filtered);

        filtered = jobApplicationControl.filterApplicationsByDateRange(applications, Globals.DateRanges.ALL);
        assertTrue(filtered.size() == 3);
        assertEquals(applications, filtered);
    }

    @Test
    void testMapDateRange() {
        assertEquals(1, jobApplicationControl.mapDateRange(Globals.DateRanges.DAY));
        assertEquals(7, jobApplicationControl.mapDateRange(Globals.DateRanges.WEEK));
        assertEquals(31, jobApplicationControl.mapDateRange(Globals.DateRanges.MONTH));
        assertEquals(31, jobApplicationControl.mapDateRange(Globals.DateRanges.ALL));
    }

    @Test
    void testDateIsInRange() {
        LocalDate date = LocalDate.of(2022,1,10);
        boolean dayInRangeResult = jobApplicationControl.dateIsInRange(date, 5);
        assertTrue(dayInRangeResult);
    }
}