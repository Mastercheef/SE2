package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.control.factories.JobFactory;
import org.hbrs.se2.project.coll.dtos.CompanyDTO;
import org.hbrs.se2.project.coll.dtos.JobAdvertisementDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.Company;
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
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

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
    Company company;
    @Mock
    JobAdvertisementDTO jobAdvertisementDTOReturn;
    @Mock
    UserDTO user;
    @Mock
    CompanyDTO companyDTO;

    private String companyName = "companyName";
    private String phoneNumber = "0123456789";
    private String email = "email.gmx.de";

    private String title = null;
    private String type = null;
    private String requirements = null;
    private boolean temporaryEmployment = false;
    private LocalDate date = null;
    private short hours = 0;
    private int salary = 0;

    @Test
    void testSaveAdvertisement() throws DatabaseUserException {
        jobAdvertisementControl.saveAdvertisement(jobAdvertisementDTO);
    }

    @Test
    void testSaveAdvertisementDARFE() {
        doThrow(DataAccessResourceFailureException.class).when(jobAdvertisementRepository).save(any());
        assertThrows( DatabaseUserException.class, () -> jobAdvertisementControl.saveAdvertisement(jobAdvertisementDTO));
    }

    @Test
    void testSaveAdvertisementException() {
        doThrow(RuntimeException.class).when(jobAdvertisementRepository).save(any());
        assertThrows( DatabaseUserException.class, () -> jobAdvertisementControl.saveAdvertisement(jobAdvertisementDTO));
    }

    @Test
    void testDeleteAdvertisement() throws DatabaseUserException {
        jobAdvertisementControl.deleteAdvertisement(jobAdvertisement);
    }

    @Test
    void testDeleteAdvertisementDARFE() {
        doThrow(DataAccessResourceFailureException.class).when(jobAdvertisementRepository).delete(any());
        assertThrows( DatabaseUserException.class, () -> jobAdvertisementControl.deleteAdvertisement(jobAdvertisement));
    }

    @Test
    void testException() {

        doThrow(RuntimeException.class).when(jobAdvertisementRepository).delete(any());
        assertThrows( DatabaseUserException.class, () -> jobAdvertisementControl.deleteAdvertisement(jobAdvertisement));
    }

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

    @Test
    void filterCompaniesByCompanyIdReturnsEmptyListByCallWithEmptyList() {
        assertNotNull(jobAdvertisementControl.filterCompaniesByCompanyId(new ArrayList<>(), 1));
        assertTrue(jobAdvertisementControl.filterCompaniesByCompanyId(new ArrayList<>(), 1).isEmpty());
    }
    @Test
    void filterCompaniesByCompanyIdReturnsEmptyListByCallWithNotFoundArguments() {
        Mockito.spy(jobAdvertisementControl);
        doReturn(contactPerson).when(jobAdvertisement).getContactPerson();
        when(contactPerson.getCompany()).thenReturn(company);
        when(company.getId()).thenReturn(100);
        List<JobAdvertisement> jobList = new ArrayList<>();
        jobList.add(jobAdvertisement);
        assertTrue(jobAdvertisementControl.filterCompaniesByCompanyId(jobList, 0).isEmpty());
    }
    @Test
    void filterCompaniesByCompanyIdReturnsAllOccurrences() {
        List<JobAdvertisement> jobList;
        Iterator<JobAdvertisement> jobIterator;

        jobIterator = mock(Iterator.class);
        when(jobIterator.hasNext()).thenReturn(true, true, true, false);
        when(jobIterator.next()).thenReturn(jobAdvertisement);

        jobList = mock(List.class);
        when(jobList.iterator()).thenReturn(jobIterator);
        doCallRealMethod().when(jobList).forEach(any(Consumer.class));

        Mockito.spy(jobAdvertisementControl);
        doReturn(contactPerson).when(jobAdvertisement).getContactPerson();
        when(contactPerson.getCompany()).thenReturn(company);
        when(company.getId()).thenReturn(100).thenReturn(0).thenReturn(100);

        assertEquals(2, jobAdvertisementControl.filterCompaniesByCompanyId(jobList, 100).size());
    }

    @Test
    void filterCompaniesByNameReturnsEmptyListByCallWithEmptyList() {
        assertNotNull(jobAdvertisementControl.filterCompanies(new ArrayList<>(), companyName));
        assertTrue(jobAdvertisementControl.filterCompanies(new ArrayList<>(), companyName).isEmpty());
    }
    @Test
    void filterCompaniesByNameReturnsEmptyListByCallWithNotFoundArguments() {
        Mockito.spy(jobAdvertisementControl);
        doReturn(100).when(jobAdvertisementControl).getCompanyId(jobAdvertisement);
        doReturn(companyName).when(jobAdvertisementControl).getCompanyName(jobAdvertisement);

        List<JobAdvertisement> jobList = new ArrayList<>();
        jobList.add(jobAdvertisement);
        assertTrue(jobAdvertisementControl.filterCompanies(jobList, "notTheCompanyName").isEmpty());
    }
    @Test
    void filterCompaniesByNameReturnsAllOccurrences() {
        List<JobAdvertisement> jobList;
        Iterator<JobAdvertisement> jobIterator;

        jobIterator = mock(Iterator.class);
        when(jobIterator.hasNext()).thenReturn(true, true, true, false);
        when(jobIterator.next()).thenReturn(jobAdvertisement);

        jobList = mock(List.class);
        when(jobList.iterator()).thenReturn(jobIterator);
        doCallRealMethod().when(jobList).forEach(any(Consumer.class));

        Mockito.spy(jobAdvertisementControl);
        doReturn(companyName, "differentText", companyName).when(jobAdvertisementControl).getCompanyName(jobAdvertisement);

        assertEquals(2, jobAdvertisementControl.filterCompanies(jobList, companyName).size());
    }

    @Test
    void getContactPersonName() {
        Mockito.spy(jobAdvertisementControl);
        doReturn(100).when(jobAdvertisementControl).getCompanyId(jobAdvertisement);
        doReturn(contactPerson).when(contactPersonRepository).findContactPersonByCompanyId(100);
        when(contactPerson.getFirstName()).thenReturn("FirstName");
        when(contactPerson.getLastName()).thenReturn("LastName");

        assertEquals("FirstName LastName", jobAdvertisementControl.getContactPersonName(jobAdvertisement));
    }

    @Test
    void getCompanyId() {
        doReturn(contactPerson).when(jobAdvertisement).getContactPerson();
        doReturn(100).when(contactPerson).getId();
        doReturn(contactPerson).when(contactPersonRepository).findContactPersonById(100);
        doReturn(company).when(contactPerson).getCompany();
        doReturn(200).when(company).getId();

        assertEquals(200, jobAdvertisementControl.getCompanyId(jobAdvertisement));
    }

    @Test
    void getJobsByCompanyID() {
        List<JobAdvertisement> list;
        list = mock(List.class);
        when(jobAdvertisementRepository.findJobAdvertisementsByCompanyId(100)).thenReturn(list);
        assertEquals(list , jobAdvertisementControl.getJobsByCompanyId(100));
    }

    @Test
    void getCompanyIdFromUser() {
        when(contactPersonRepository.findContactPersonById(100)).thenReturn(contactPerson);
        when(user.getId()).thenReturn(100);
        when(contactPerson.getCompany()).thenReturn(company);
        when(contactPerson.getId()).thenReturn(100);
        when(contactPerson.getCompany().getId()).thenReturn(100);
        when(company.getId()).thenReturn(100);
        assertEquals(100 , jobAdvertisementControl.getCompanyIdFromUser(user));
    }

    @Test
    void getAllJobs() {
        List<JobAdvertisement> list;
        list = mock(List.class);

        when(jobAdvertisementRepository.findAll(Sort.by(Sort.Direction.DESC, "startOfWork"))).thenReturn(list);
        assertEquals(list , jobAdvertisementControl.getAllJobs());
    }
    @Test
    void filterOne() {
        List<JobAdvertisement> list;
        list = mock(List.class);

        when(jobAdvertisementRepository.filterJobs(title, type, requirements, temporaryEmployment, date, hours, salary)).thenReturn(list);
        assertEquals(list , jobAdvertisementControl.filterJobs(title, type, requirements, temporaryEmployment, date, hours, salary));
    }

    @Test
    void filterTwo() {
        List<JobAdvertisement> listTwo;
        listTwo = mock(List.class);

        when(jobAdvertisementRepository.filterJobs(title, type, requirements, date, hours, salary)).thenReturn(listTwo);
        assertEquals(listTwo , jobAdvertisementControl.filterJobs(title, type, requirements, date, hours, salary));
    }

    @Test
    void filterThree() {
        List<JobAdvertisement> listThree;
        listThree = mock(List.class);

        when(jobAdvertisementRepository.filterJobs(title, requirements, temporaryEmployment, date, hours, salary)).thenReturn(listThree);
        assertEquals(listThree , jobAdvertisementControl.filterJobs(title, requirements, temporaryEmployment, date, hours, salary));
    }
    @Test
    void filterFour() {
        List<JobAdvertisement> listFour;
        listFour = mock(List.class);

        when(jobAdvertisementRepository.filterJobs(title, requirements, date, hours, salary)).thenReturn(listFour);
        assertEquals(listFour , jobAdvertisementControl.filterJobs(title, requirements, date, hours, salary));
    }
}