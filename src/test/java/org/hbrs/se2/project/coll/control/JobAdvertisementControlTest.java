package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.factories.JobFactory;
import org.hbrs.se2.project.coll.dtos.CompanyDTO;
import org.hbrs.se2.project.coll.dtos.JobAdvertisementDTO;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.ContactPerson;
import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.hbrs.se2.project.coll.repository.CompanyRepository;
import org.hbrs.se2.project.coll.repository.ContactPersonRepository;
import org.hbrs.se2.project.coll.repository.JobAdvertisementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@MockitoSettings(strictness = Strictness.LENIENT)
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
    ContactPerson contactPerson;
    @Mock
    JobAdvertisementDTO jobAdvertisementDTOReturn;

    @Mock
    CompanyDTO companyDTO;

    private String companyName = "companyName";
    private String phoneNumber = "0123456789";
    private String email = "email.gmx.de";
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
    void getCompanyNameStringReturn() {
        Mockito.spy(jobAdvertisementControl);
        doReturn(100).when(jobAdvertisementControl).getCompanyId(jobAdvertisement);
        when(companyRepository.findCompanyProfileById(100)).thenReturn(companyDTO);
        when(companyDTO.getCompanyName()).thenReturn(companyName);
        when(companyRepository.findCompanyProfileById(100).getCompanyName()).thenReturn(companyName);
        assertEquals(companyName , jobAdvertisementControl.getCompanyName(jobAdvertisement));
    }

    @Test
    void getCompanyNameAddressReturn() {
        Mockito.spy(jobAdvertisementControl);
        doReturn(100).when(jobAdvertisementControl).getCompanyId(jobAdvertisement);
        when(companyRepository.findCompanyProfileById(100)).thenReturn(companyDTO);
        when(companyDTO.getCompanyName()).thenReturn(companyName);
        when(companyRepository.findCompanyProfileById(100).getAddress()).thenReturn(addressReturn);
        assertEquals(addressReturn , jobAdvertisementControl.getCompanyAddress(jobAdvertisement));
    }
    @Test
    void getCompanyNamePhoneNumber() {
        Mockito.spy(jobAdvertisementControl);
        doReturn(100).when(jobAdvertisementControl).getCompanyId(jobAdvertisement);
        when(companyRepository.findCompanyProfileById(100)).thenReturn(companyDTO);
        when(companyDTO.getCompanyName()).thenReturn(companyName);
        when(companyRepository.findCompanyProfileById(100).getPhoneNumber()).thenReturn(phoneNumber);
        assertEquals(phoneNumber , jobAdvertisementControl.getCompanyPhoneNumber(jobAdvertisement));
    }

    @Test
    void getContactPersonEmail() {
        Mockito.spy(jobAdvertisementControl);
        doReturn(100).when(jobAdvertisementControl).getCompanyId(jobAdvertisement);
        when(contactPersonRepository.findContactPersonByCompanyId(100)).thenReturn(contactPerson);
        when(companyDTO.getCompanyName()).thenReturn(companyName);
        when(contactPersonRepository.findContactPersonByCompanyId(100).getEmail()).thenReturn(email);
        assertEquals(email , jobAdvertisementControl.getContactPersonEmail(jobAdvertisement));
    }
}