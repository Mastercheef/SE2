package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.dtos.CompanyDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.ContactPerson;
import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.hbrs.se2.project.coll.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
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
    @Mock
    UserRepository userRepository;
    @Mock
    UserDTO userDTO;
    @Mock
    UserDTO reciever;

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

    @Test
    void checkIfUserIsAllowedToSendMessage() {

        //Everything true
        when(userRepository.findUserById(100)).thenReturn(reciever);
        when(reciever.getType()).thenReturn("cp");
        when(userDTO.getId()).thenReturn(100);
        assertTrue(contactingControl.checkIfUserIsAllowedToSendMessage(userDTO ,100));

        //ID smaller than 0
        when(userDTO.getId()).thenReturn(0);
        assertFalse(contactingControl.checkIfUserIsAllowedToSendMessage(userDTO ,100));

        //Type is not Company
        when(userDTO.getId()).thenReturn(100);
        when(reciever.getType()).thenReturn("st");
        assertFalse(contactingControl.checkIfUserIsAllowedToSendMessage(userDTO ,100));

        //Reciever is null
        when(userRepository.findUserById(100)).thenReturn(null);
        when(reciever.getType()).thenReturn("cp");
        assertFalse(contactingControl.checkIfUserIsAllowedToSendMessage(userDTO ,100));

    }
}