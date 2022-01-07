package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.factories.JobFactory;
import org.hbrs.se2.project.coll.dtos.CompanyDTO;
import org.hbrs.se2.project.coll.dtos.JobAdvertisementDTO;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.hbrs.se2.project.coll.repository.CompanyRepository;
import org.hbrs.se2.project.coll.repository.ContactPersonRepository;
import org.hbrs.se2.project.coll.repository.JobAdvertisementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobAdvertisementControlTest {

    @Spy
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

    @Mock
    Address addressReturn;

    @Mock
    JobAdvertisementDTO jobAdvertisementDTOReturn;

    @Mock
    CompanyDTO companyDTO;

    private String companyName = "companyName";
    @Test
    void getJob() {
        when(jobAdvertisementRepository.findJobAdvertisementById(100)).thenReturn(jobAdvertisement);
        assertTrue(jobAdvertisementControl.getJob(100) instanceof JobAdvertisement);
        assertEquals(jobAdvertisement, jobAdvertisementControl.getJob(100));
    }

    @Test
    void createJob() {
        try (MockedStatic<JobFactory> classMock = mockStatic(JobFactory.class)) {
            classMock.when(() -> JobFactory.createJobDTO(jobAdvertisement)).thenReturn(jobAdvertisementDTOReturn);
            assertEquals(jobAdvertisementDTOReturn , jobAdvertisementControl.createJobDTO(jobAdvertisement));
        }
    }

    @Test
    void getCompanyName() {
        Mockito.spy(jobAdvertisementControl);
        doReturn(100).when(jobAdvertisementControl).getCompanyId(jobAdvertisement);
        when(companyRepository.findCompanyProfileById(100)).thenReturn(companyDTO);
        when(companyDTO.getCompanyName()).thenReturn(companyName);
        when(companyRepository.findCompanyProfileById(100).getCompanyName()).thenReturn(companyName);
        assertEquals(companyName , jobAdvertisementControl.getCompanyName(jobAdvertisement));
    }
}