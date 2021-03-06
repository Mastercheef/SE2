package org.hbrs.se2.project.coll.control.factories;
import org.hbrs.se2.project.coll.dtos.JobAdvertisementDTO;
import org.hbrs.se2.project.coll.dtos.JobApplicationDTO;
import org.hbrs.se2.project.coll.dtos.impl.JobAdvertisementDTOimpl;
import org.hbrs.se2.project.coll.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class JobFactoryTest {

    private static final String ERROR_MESSAGE = "class org.hbrs.se2.project.coll.control.factories.JobFactoryTest cannot access a member of class org.hbrs.se2.project.coll.control.factories.JobFactory with modifiers \"private\"";

    private JobAdvertisementDTOimpl dto;

    private short workingHours = 40;

    private String typeOfEmployment = "Vollzeit";
    private String requirements = "Lorem Ipsum";
    private String jobDescription = "Sed ut perspiciatis";
    private String jobTitle = "Datenbankexperte";
    @Mock
    Address address;

    @Mock
    ContactPerson contactPerson;

    @Mock
    JobApplicationDTO jobApplicationDTO;

    @BeforeEach
    void setUp() {
        address = spy(new Address());
        contactPerson = spy(new ContactPerson());
        MockitoAnnotations.openMocks(this);
        dto = new JobAdvertisementDTOimpl();
        dto.setId(100);
        dto.setTemporaryEmployment(true);
        dto.setTypeOfEmployment(typeOfEmployment);
        dto.setWorkingHours(workingHours);
        dto.setRequirements(requirements);
        dto.setAddress(address);
        dto.setStartOfWork(LocalDate.of(1999,1,23));
        dto.setEndOfWork(LocalDate.of(2000,1,2));
        dto.setJobDescription(jobDescription);
        dto.setJobTitle(jobTitle);
        dto.setContactPerson(contactPerson);

    }

    @Test
    void createJob() {
        JobAdvertisement jobAdvertisement = JobFactory.createJob(dto);
        assertEquals( 100 , jobAdvertisement.getId());
        assertTrue(jobAdvertisement.getTemporaryEmployment());
        assertEquals(typeOfEmployment , jobAdvertisement.getTypeOfEmployment());
        assertEquals(Integer.valueOf(40) , Integer.valueOf(jobAdvertisement.getWorkingHours()));
        assertEquals(requirements , jobAdvertisement.getRequirements());
        assertNotNull(jobAdvertisement.getWorkingLocation());
        assertSame(address, jobAdvertisement.getWorkingLocation());
        assertEquals("1999-01-23" , jobAdvertisement.getStartOfWork().toString());
        assertEquals("2000-01-02" , jobAdvertisement.getEndOfWork().toString());
        assertEquals(jobDescription , jobAdvertisement.getJobDescription());
        assertEquals(jobTitle , jobAdvertisement.getJobTitle());
        assertNotNull(jobAdvertisement.getContactPerson());
        assertSame(contactPerson, jobAdvertisement.getContactPerson());

    }

    @Test
    void itShouldThrowIllegalAccessExceptionWhenInstancingJobFactory() throws NoSuchMethodException {
        Constructor<JobFactory> constructor = JobFactory.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        Throwable exceptionThatWasThrown = assertThrows(IllegalAccessException.class, constructor::newInstance);
        assertEquals(ERROR_MESSAGE, exceptionThatWasThrown.getMessage());
        constructor.setAccessible(true);
        assertThrows(ReflectiveOperationException.class,constructor::newInstance);
    }

    @Test
    void createJobDTO() {
        JobAdvertisement job = new JobAdvertisement();
        job.setId(12345);
        job.setTemporaryEmployment(true);
        job.setTypeOfEmployment("Vollzeit");
        job.setWorkingHours((short) 22);
        job.setRequirements("C++, HTML, CSS, JavaScript, Java");
        job.setStartOfWork(LocalDate.of(2022, 1, 1));
        job.setEndOfWork(LocalDate.of(2022, 3, 1));
        job.setContactPerson(contactPerson);
        job.setJobDescription("Lorem ipsum");
        job.setJobTitle("Lorem");
        job.setSalary(1234);

        JobAdvertisementDTO jobAdvertisementDTO = JobFactory.createJobDTO(job);
        assertEquals(jobAdvertisementDTO.getJobTitle(), job.getJobTitle());
        assertEquals(jobAdvertisementDTO.getId(), job.getId());
        assertEquals(jobAdvertisementDTO.getJobDescription(), job.getJobDescription());
        assertEquals(jobAdvertisementDTO.getTemporaryEmployment(), job.getTemporaryEmployment());
        assertEquals(jobAdvertisementDTO.getTypeOfEmployment(), job.getTypeOfEmployment());
        assertEquals(jobAdvertisementDTO.getWorkingHours(), job.getWorkingHours());
        assertEquals(jobAdvertisementDTO.getRequirements(), job.getRequirements());
        assertEquals(jobAdvertisementDTO.getStartOfWork(), job.getStartOfWork());
        assertEquals(jobAdvertisementDTO.getEndOfWork(), job.getEndOfWork());
        assertEquals(jobAdvertisementDTO.getContactPerson(), job.getContactPerson());
        assertEquals(jobAdvertisementDTO.getSalary(), job.getSalary());
    }



    @Test
    void createJobApplication() {

        String jobApplicationHeadline = "jobApplicationDTO Headline";
        String jobApplicationDTOText = "jobApplicationDTO Text";
        LocalDate dtoDate = LocalDate.of(2000,1,2);

        when(jobApplicationDTO.getStudentUser()).thenReturn(new StudentUser());
        when(jobApplicationDTO.getJobAdvertisement()).thenReturn(new JobAdvertisement());
        when(jobApplicationDTO.getHeadline()).thenReturn(jobApplicationHeadline);
        when(jobApplicationDTO.getText()).thenReturn(jobApplicationDTOText);
        when(jobApplicationDTO.getDate()).thenReturn(dtoDate);

        JobApplication jobApplication = JobFactory.createJobApplication(jobApplicationDTO);
        assertNotNull(jobApplication.getStudentUser());
        assertNotNull(jobApplication.getJobAdvertisement());
        assertEquals( jobApplicationHeadline , jobApplication.getHeadline());
        assertEquals(jobApplicationDTOText , jobApplication.getText());
        assertEquals(dtoDate , jobApplication.getDate());

    }
}