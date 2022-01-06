package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.control.factories.CompanyFactory;
import org.hbrs.se2.project.coll.control.factories.UserFactory;
import org.hbrs.se2.project.coll.dtos.impl.CompanyDTOImpl;
import org.hbrs.se2.project.coll.dtos.impl.JobAdvertisementDTOimpl;
import org.hbrs.se2.project.coll.entities.Company;
import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.hbrs.se2.project.coll.repository.CompanyRepository;
import org.hbrs.se2.project.coll.repository.JobAdvertisementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CompanyControlTest {


    @InjectMocks
    private CompanyControl companyControl = new CompanyControl();

    @Mock
    private CompanyRepository repository;
    @Mock
    private AddressControl addressControl;
    @Mock
    private JobAdvertisementControl jobAdvertisementControl;
    @Mock
    JobAdvertisementRepository jobAdvertisementRepository;

    CompanyDTOImpl companyDTO;
    @Mock
    JobAdvertisement jobAdvertisement;
    @Mock
    Company company;

    @BeforeEach
    void setup() {
        companyDTO = new CompanyDTOImpl();
        companyDTO.setId(100);
    }

    @Test
    void loadCompanyProfileDataById() {
        MockitoAnnotations.openMocks(this);
        doReturn(companyDTO).when(repository).findCompanyProfileById(100);
        assertNotNull(repository.findCompanyProfileById(100));
        assertNotNull(companyControl.loadCompanyProfileDataById(100));
        assertEquals(companyDTO , companyControl.loadCompanyProfileDataById(100));
    }


    @Test
    void findCompanyProfileByCompanyId() {

        MockitoAnnotations.openMocks(this);
        doReturn(companyDTO).when(repository).findCompanyProfileById(100);
        assertNotNull(repository.findCompanyProfileById(100));
        assertNotNull(companyControl.findCompanyProfileByCompanyId(100));
        assertEquals(companyDTO , companyControl.findCompanyProfileByCompanyId(100));
    }


    @Test
    void createJobDTO() {
        when(jobAdvertisementControl.createJobDTO(jobAdvertisement)).thenReturn(new JobAdvertisementDTOimpl());
        assertNotNull(companyControl.createJobDTO(jobAdvertisement));
        assertEquals(new JobAdvertisementDTOimpl().getClass() , companyControl.createJobDTO(jobAdvertisement).getClass());
    }

    @Test
    void testDeleteAdvertisement() throws DatabaseUserException {
        Mockito.doNothing().when(jobAdvertisementRepository).delete(jobAdvertisement);
        companyControl.deleteAdvertisement( jobAdvertisement);
        verify(jobAdvertisementControl).deleteAdvertisement(jobAdvertisement);
    }

    @Test
    void saveCompany() {
        try (MockedStatic<CompanyFactory> classMock = mockStatic(CompanyFactory.class)) {

            classMock.when(() -> CompanyFactory.createCompany(companyDTO)).thenReturn(company);
        }
    }
}