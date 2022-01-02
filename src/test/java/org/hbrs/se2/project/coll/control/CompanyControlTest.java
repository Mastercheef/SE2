package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.dtos.JobAdvertisementDTO;
import org.hbrs.se2.project.coll.dtos.impl.CompanyDTOImpl;
import org.hbrs.se2.project.coll.dtos.impl.JobAdvertisementDTOimpl;
import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.hbrs.se2.project.coll.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
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
    @Mock
    JobAdvertisement jobAdvertisement;

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
        when(jobAdvertisementControl.createJobDTO(jobAdvertisement)).thenReturn(new JobAdvertisementDTOimpl());
        assertNotNull(companyControl.createJobDTO(jobAdvertisement));
        assertEquals(new JobAdvertisementDTOimpl().getClass() , companyControl.createJobDTO(jobAdvertisement).getClass());
    }
}