package org.hbrs.se2.project.coll.control.factories;
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
        dto.setTypeOfEmployment("Vollzeit");
        dto.setWorkingHours(workingHours);
        dto.setRequirements("Lorem Ipsum");
        dto.setAddress(address);
        dto.setStartOfWork(LocalDate.of(1999,1,23));
        dto.setEndOfWork(LocalDate.of(2000,1,2));
        dto.setJobDescription("Sed ut perspiciatis");
        dto.setJobTitle("Datenbankexperte");
        dto.setContactPerson(contactPerson);

    }

    @Test
    void createJob() {

        JobAdvertisement jobAdvertisement = JobFactory.createJob(dto);
        assertEquals( 100 , jobAdvertisement.getId());
        assertTrue(jobAdvertisement.getTemporaryEmployment());
        assertEquals("Vollzeit" , jobAdvertisement.getTypeOfEmployment());
        assertEquals(Integer.valueOf(40) , Integer.valueOf(jobAdvertisement.getWorkingHours()));
        assertEquals("Lorem Ipsum" , jobAdvertisement.getRequirements());
        assertNotNull(jobAdvertisement.getWorkingLocation());
        assertSame(address, jobAdvertisement.getWorkingLocation());
        assertEquals("1999-01-23" , jobAdvertisement.getStartOfWork().toString());
        assertEquals("2000-01-02" , jobAdvertisement.getEndOfWork().toString());
        assertEquals("Sed ut perspiciatis" , jobAdvertisement.getJobDescription());
        assertEquals("Datenbankexperte" , jobAdvertisement.getJobTitle());
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
    }

    @Test
    void createJobApplication() {

        String jobApplicationHeadline = "jobApplicationDTO Headline";
        String jobApplicationDTOText = "jobApplicationDTO Text";
        LocalDate dtoDate = LocalDate.of(2000,1,2);

        when(jobApplicationDTO.getStudentUser()).thenReturn(new StudentUser());
        when(jobApplicationDTO.getJobAdvertisement()).thenReturn(new JobAdvertisement());
        when(jobApplicationDTO.getHeadline()).thenReturn(jobApplicationHeadline);
        when(jobApplicationDTO.getText()).thenReturn("jobApplicationDTO Text");
        when(jobApplicationDTO.getDate()).thenReturn(dtoDate);

        JobApplication jobApplication = JobFactory.createJobApplication(jobApplicationDTO);
        assertNotNull(jobApplication.getStudentUser());
        assertNotNull(jobApplication.getJobAdvertisement());
        assertEquals( jobApplicationHeadline , jobApplication.getHeadline());
        assertEquals(jobApplicationDTOText , jobApplication.getText());
        assertEquals(dtoDate , jobApplication.getDate());

    }
}