package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.dtos.JobApplicationDTO;
import org.hbrs.se2.project.coll.dtos.JobApplicationResultDTO;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobApplicationControlTest {

    @InjectMocks
    JobApplicationControl jobApplicationControl;
    @Mock
    JobApplicationRepository jobApplicationRepository;
    @Mock
    ContactPersonControl contactPersonControl;
    @Mock
    JobAdvertisementControl jobAdvertisementControl;

    UserDTO userDTO = Mockito.mock(UserDTO.class);
    StudentUser studentUser = Mockito.mock(StudentUser.class);
    JobAdvertisement jobAdvertisement = Mockito.mock(JobAdvertisement.class);
    ContactPerson contactPerson = Mockito.mock(ContactPerson.class);
    Company company = Mockito.mock(Company.class);
    JobApplicationDTO jobApplicationDTO = Mockito.mock(JobApplicationDTO.class);

    List<JobApplicationDTO> applications = new ArrayList<>();
    List<JobApplicationDTO> resultListDateRange = new ArrayList<>();
    List<JobApplicationDTO> resultListUsername = new ArrayList<>();
    List<JobApplicationDTO> resultListHeadline= new ArrayList<>();

    String student = "student";
    String match = "match";
    String headline = "headeline";
    String text = "Text";

    @BeforeEach
    void setUp() {
        StudentUser studentUser1 = new StudentUser();
        studentUser1.setFirstName(student);
        studentUser1.setLastName(match);
        JobAdvertisement jobAd1 = new JobAdvertisement();
        JobApplicationDTOImpl job1 = new JobApplicationDTOImpl();
        job1.setHeadline("Job1");
        job1.setStudentUser(studentUser1);
        job1.setDate(LocalDate.of(2021,1,1));
        job1.setJobAdvertisementDTO(jobAd1);

        StudentUser studentUser2 = new StudentUser();
        studentUser2.setFirstName(student);
        studentUser2.setLastName(match);
        JobAdvertisement jobAd2 = new JobAdvertisement();
        JobApplicationDTOImpl job2 = new JobApplicationDTOImpl();
        job2.setHeadline("Job2");
        job2.setStudentUser(studentUser2);
        job2.setDate(LocalDate.now());
        job2.setJobAdvertisementDTO(jobAd2);

        StudentUser studentUser3 = new StudentUser();
        studentUser3.setFirstName(student);
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
    void testCreateJobApplicationPositive() {
        JobApplication savedJobApplication = Mockito.mock(JobApplication.class);
        when(savedJobApplication.getId()).thenReturn(10);
        when(jobApplicationDTO.getHeadline()).thenReturn(headline);
        when(jobApplicationDTO.getText()).thenReturn(text);
        when(jobApplicationRepository.save(any())).thenReturn(savedJobApplication);
        JobApplicationResultDTO result = jobApplicationControl.createJobApplication(jobApplicationDTO);
        assertTrue(result.getResult());
        assertTrue(result.getReasons().contains(JobApplicationResultDTO.ReasonType.SUCCESS));
        assertEquals(savedJobApplication.getId(), result.getApplicationID());
    }

    @Test
    void testCreateJobApplicationNegativeInformationMissing() {
        JobApplicationResultDTO result = jobApplicationControl.createJobApplication(jobApplicationDTO);
        assertFalse(result.getResult());
        assertTrue(result.getReasons().contains(JobApplicationResultDTO.ReasonType.HEADLINE_MISSING));
        assertTrue(result.getReasons().contains(JobApplicationResultDTO.ReasonType.TEXT_MISSING));
    }

    @Test
    void testCreateJobApplicationNegativeException() {
        when(jobApplicationDTO.getHeadline()).thenReturn(headline);
        when(jobApplicationDTO.getText()).thenReturn(text);
        when(jobApplicationRepository.save(any())).thenThrow(RuntimeException.class);
        JobApplicationResultDTO result = jobApplicationControl.createJobApplication(jobApplicationDTO);
        assertFalse(result.getResult());
        assertTrue(result.getReasons().contains(JobApplicationResultDTO.ReasonType.UNEXPECTED_ERROR));
    }

    @Test
    void loadJobApplication() {
        when(jobApplicationRepository.findJobApplicationById(100)).thenReturn(jobApplicationDTO);
        assertEquals(jobApplicationDTO , jobApplicationControl.loadJobApplication(100));
        assertTrue(jobApplicationControl.loadJobApplication(100) instanceof  JobApplicationDTO);
    }

    List<JobAdvertisement> jobAdds;
    List<JobApplicationDTO> jobAppsList1;
    List<JobApplicationDTO> jobAppsList2;
    List<JobApplicationDTO> correctList;

    @Test
    void testLoadJobApplicationsFromCompany() {
        when(contactPerson.getCompany()).thenReturn(company);
        when(company.getId()).thenReturn(10);
        when(contactPersonControl.findContactPersonById(1)).thenReturn(contactPerson);
        JobAdvertisement job1 = Mockito.mock(JobAdvertisement.class);
        JobAdvertisement job2 = Mockito.mock(JobAdvertisement.class);
        jobAdds = new ArrayList<>();
        jobAdds.add(job1);
        jobAdds.add(job2);
        JobApplicationDTO jobApp1 = Mockito.mock(JobApplicationDTO.class);
        JobApplicationDTO jobApp2 = Mockito.mock(JobApplicationDTO.class);
        jobAppsList1 = new ArrayList<>();
        jobAppsList2 = new ArrayList<>();
        jobAppsList1.add(jobApp1);
        jobAppsList2.add(jobApp2);
        when(jobAdvertisementControl.getJobsByCompanyId(10)).thenReturn(jobAdds);
        when(jobApplicationRepository.findJobApplicationsByJobAdvertisement(job1)).thenReturn(jobAppsList1);
        when(jobApplicationRepository.findJobApplicationsByJobAdvertisement(job2)).thenReturn(jobAppsList2);

        when(userDTO.getId()).thenReturn(1);
        correctList = new ArrayList<>();
        correctList.add(jobApp1);
        correctList.add(jobApp2);

        List<JobApplicationDTO> resultList = jobApplicationControl.loadJobApplicationsFromCompany(userDTO);
        assertEquals(correctList, resultList);
    }

    @Test
    void testIsUserAllowedToAccessJobApplicationsPositive() {
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
    void testIsUserAllowedToAccessJobApplicationsNegative() {
        ContactPerson differentContactPerson = Mockito.mock(ContactPerson.class);
        Company differentCompany = Mockito.mock(Company.class);
        when(differentContactPerson.getCompany()).thenReturn(differentCompany);
        when(differentCompany.getId()).thenReturn(1000);

        when(jobApplicationDTO.getJobAdvertisement()).thenReturn(jobAdvertisement);
        when(jobAdvertisement.getContactPerson()).thenReturn(contactPerson);
        when(contactPerson.getCompany()).thenReturn(company);
        when(company.getId()).thenReturn(10);
        when(userDTO.getType()).thenReturn("cp");
        when(jobApplicationControl.getContactPersonFromSessionUser(userDTO)).thenReturn(differentContactPerson);
        assertFalse(jobApplicationControl.isUserAllowedToAccessJobApplications(userDTO));
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
        assertEquals(0 , filtered.size());

        List<JobApplicationDTO> empty = new ArrayList<>();
        filtered = jobApplicationControl.filterApplicationsByHeadline(empty, "Headline");
        assertEquals(0 , filtered.size());

        filtered = jobApplicationControl.filterApplicationsByHeadline(applications, "Job1");
        assertEquals(1 , filtered.size());
        assertEquals(resultListHeadline, filtered);
    }

    @Test
    void testFilterApplicationsByUsername() {
        List<JobApplicationDTO> filtered = jobApplicationControl.filterApplicationsByUsername(null, "Gibts nicht");
        assertEquals(0 , filtered.size());

        List<JobApplicationDTO> empty = new ArrayList<>();
        filtered = jobApplicationControl.filterApplicationsByUsername(empty, "Name");
        assertEquals(0 , filtered.size());

        filtered = jobApplicationControl.filterApplicationsByUsername(applications, match);
        assertEquals(2 , filtered.size());
        assertEquals(resultListUsername, filtered);
    }

    @Test
    void testFilterApplicationsByDateRange() {
        List<JobApplicationDTO> filtered = jobApplicationControl.filterApplicationsByDateRange(null, Globals.DateRanges.DAY);
        assertEquals(0 , filtered.size());

        List<JobApplicationDTO> empty = new ArrayList<>();
        filtered = jobApplicationControl.filterApplicationsByDateRange(empty, Globals.DateRanges.DAY);
        assertEquals(0 , filtered.size());

        filtered = jobApplicationControl.filterApplicationsByDateRange(applications, Globals.DateRanges.DAY);
        assertEquals(2 , filtered.size());
        assertEquals(resultListDateRange, filtered);

        filtered = jobApplicationControl.filterApplicationsByDateRange(applications, Globals.DateRanges.ALL);
        assertEquals(3 , filtered.size());
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