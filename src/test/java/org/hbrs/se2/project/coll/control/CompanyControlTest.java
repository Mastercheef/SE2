package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.dtos.impl.CompanyDTOImpl;
import org.hbrs.se2.project.coll.dtos.impl.StudentUserDTOImpl;
import org.hbrs.se2.project.coll.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

class CompanyControlTest {


    @InjectMocks
    private CompanyControl companyControl = new CompanyControl();

    @Mock
    private CompanyRepository repository;
    @Mock
    private AddressControl addressControl;
    @Mock
    private JobAdvertisementControl jobAdvertisementControl;

    CompanyDTOImpl companyDTO;

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
    void saveCompany() {
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
    void deleteAdvertisement() {
    }

    @Test
    void createJobDTO() {
    }
}