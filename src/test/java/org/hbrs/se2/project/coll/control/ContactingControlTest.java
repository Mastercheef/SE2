package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.builder.UserDTOBuilder;
import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
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
import org.springframework.dao.DataAccessResourceFailureException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
    private UserRepository userRepository;

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
    private String content = "content";
    private int sender = 10;
    private int recipient = 20;
    private String subject = "subject";
    private LocalDate date = LocalDate.now();

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
    void testSendMessage() throws DatabaseUserException {
        assertTrue(contactingControl.sendMessage(content, sender, recipient, subject, date));
    }

    @Test
    void testSendMessageDARFE() {
        when(messageRepository.save(any())).thenThrow(DataAccessResourceFailureException.class);
        assertThrows( DatabaseUserException.class, () -> contactingControl.sendMessage(content, sender, recipient, subject, date));
    }

    @Test
    void testSendMessageException() {
        when(messageRepository.save(any())).thenThrow(RuntimeException.class);
        assertThrows( DatabaseUserException.class, () -> contactingControl.sendMessage(content, sender, recipient, subject, date));
    }

    @Test
    void testCheckIfUserIsAllowedToSendMessageCpToCp() {
        UserDTO userDTO = UserDTOBuilder
                .please()
                .createEmptyUser()
                .withId(10)
                .withType("cp")
                .done();
        UserDTO userReceiver = UserDTOBuilder
                .please()
                .createEmptyUser()
                .withId(100)
                .withType("cp")
                .done();
        when(userRepository.findUserById(100)).thenReturn(userReceiver);

        assertTrue(contactingControl.checkIfUserIsAllowedToSendMessage(userDTO, 100));
    }

    @Test
    void testCheckIfUserIsAllowedToSendMessageCpToSt() {
        UserDTO userDTO = UserDTOBuilder
                .please()
                .createEmptyUser()
                .withId(10)
                .withType("cp")
                .done();
        UserDTO userReceiver = UserDTOBuilder
                .please()
                .createEmptyUser()
                .withId(100)
                .withType("st")
                .done();
        when(userRepository.findUserById(100)).thenReturn(userReceiver);

        assertTrue(contactingControl.checkIfUserIsAllowedToSendMessage(userDTO, 100));
    }

    @Test
    void testCheckIfUserIsAllowedToSendMessageStToSt() {
        UserDTO userDTO = UserDTOBuilder
                .please()
                .createEmptyUser()
                .withId(10)
                .withType("st")
                .done();
        UserDTO userReceiver = UserDTOBuilder
                .please()
                .createEmptyUser()
                .withId(100)
                .withType("st")
                .done();
        when(userRepository.findUserById(100)).thenReturn(userReceiver);

        assertFalse(contactingControl.checkIfUserIsAllowedToSendMessage(userDTO, 100));
    }

    @Test
    void testCheckIfUserIsAllowedToSendMessageStToCp() {
        UserDTO userDTO = UserDTOBuilder
                .please()
                .createEmptyUser()
                .withId(10)
                .withType("st")
                .done();
        UserDTO userReceiver = UserDTOBuilder
                .please()
                .createEmptyUser()
                .withId(100)
                .withType("cp")
                .done();
        when(userRepository.findUserById(100)).thenReturn(userReceiver);

        assertTrue(contactingControl.checkIfUserIsAllowedToSendMessage(userDTO, 100));
    }

    @Test
    void testCheckUrlParameterInvalid() {
        assertTrue(contactingControl.checkUrlParameterInvalid(0,0,0));
        assertTrue(contactingControl.checkUrlParameterInvalid(0,0,10));
        assertTrue(contactingControl.checkUrlParameterInvalid(0,10,0));
        assertFalse(contactingControl.checkUrlParameterInvalid(10,0,0));
        assertFalse(contactingControl.checkUrlParameterInvalid(10,10,10));
        assertTrue(contactingControl.checkUrlParameterInvalid(10,0,10));
        assertTrue(contactingControl.checkUrlParameterInvalid(0,10,10));
        assertTrue(contactingControl.checkUrlParameterInvalid(10,10,0));
        assertFalse(contactingControl.checkUrlParameterInvalid(20000002,40000000,30000000));
    }
}