package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.dtos.CompanyDTO;
import org.hbrs.se2.project.coll.entities.ContactPerson;
import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.hbrs.se2.project.coll.repository.CompanyRepository;
import org.hbrs.se2.project.coll.repository.ContactPersonRepository;
import org.hbrs.se2.project.coll.repository.JobAdvertisementRepository;
import org.hbrs.se2.project.coll.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class ContactingControlTest {

    @InjectMocks
    ContactingControl contactingControl;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private JobAdvertisementRepository jobAdvertisementRepository;

    @Mock
    private ContactPersonRepository contactPersonRepository;

    @Mock
    JobAdvertisement jobAdvertisement;
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private ContactPerson contactPerson;
    @Mock
    CompanyDTO companyDTO;

    private String jobtitle = "Datenbankexperte";
    private String companName = "Mustermann GMBH";

    @Test
    void getJobTitle() {
        when(jobAdvertisementRepository.findJobAdvertisementById(100)).thenReturn(jobAdvertisement);
        when(jobAdvertisementRepository.findJobAdvertisementById(100).getJobTitle()).thenReturn(jobtitle);
        assertEquals(jobtitle , contactingControl.getJobTitle(100));
    }

    @Test
    void getCompanyName() {
        when(companyRepository.findCompanyProfileById(100)).thenReturn(companyDTO);
        when(companyRepository.findCompanyProfileById(100).getCompanyName()).thenReturn(companName);
        assertEquals(companName , contactingControl.getCompanyName(100));
    }

    @Test
    void getContactPerson() {
        when(contactPersonRepository.findContactPersonByCompanyId(100)).thenReturn(contactPerson);
        when(contactPersonRepository.findContactPersonByCompanyId(100).getId()).thenReturn(200);
        assertEquals(200 , contactingControl.getContactPersonId(100));
    }
}