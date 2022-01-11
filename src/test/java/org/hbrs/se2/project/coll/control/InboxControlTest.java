package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.control.factories.MessageFactory;
import org.hbrs.se2.project.coll.dtos.MessageDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.Company;
import org.hbrs.se2.project.coll.entities.ContactPerson;
import org.hbrs.se2.project.coll.entities.Message;
import org.hbrs.se2.project.coll.repository.ContactPersonRepository;
import org.hbrs.se2.project.coll.repository.JobAdvertisementRepository;
import org.hbrs.se2.project.coll.repository.MessageRepository;
import org.hbrs.se2.project.coll.repository.UserRepository;
import org.hbrs.se2.project.coll.util.Globals;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class InboxControlTest {

    @InjectMocks
    InboxControl inboxControl;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JobAdvertisementRepository jobAdvertisementRepository;

    @Mock
    private ContactPersonRepository contactPersonRepository;


    @Mock
    MessageDTO messageDTO;
    @Mock
    Message sentMessage;

    MessageDTO messageDTOMethod;
    Message resultMessage;

    @Mock
    UserDTO userDTO;

    @Mock
    ContactPerson contactPerson;

    @Mock
    Company company;


    private String message = "message";
    private String subject = "Subject";
    private LocalDate date = LocalDate.of(2000,1 ,12);

    @Test
    void getMessages() {
        ArrayList<MessageDTO> messageDTOArrayList = new ArrayList<>();
        messageDTOArrayList.add(messageDTO);
        when(messageRepository.findMessagesByRecipient(100)).thenReturn(messageDTOArrayList);
        assertEquals(1 , inboxControl.getMessages(100).size());
        messageDTOArrayList.add(messageDTO);
        assertEquals(2 , inboxControl.getMessages(100).size());
    }

    @Test
    void prepareSending() {
        when(messageDTO.getRecipient()).thenReturn(200);
        when(messageDTO.getSender()).thenReturn(400);
        when(messageDTO.getSubject()).thenReturn(subject);
        when(messageDTO.getDate()).thenReturn(date);

        messageDTOMethod = inboxControl.prepareSending(messageDTO,message);

        assertEquals(message , messageDTOMethod.getContent());
        assertEquals(400 , messageDTOMethod.getRecipient());
        assertEquals(200 , messageDTOMethod.getSender());
        assertEquals(subject, messageDTOMethod.getSubject());
        assertEquals(message , messageDTOMethod.getContent());
        assertEquals(LocalDate.now() , messageDTOMethod.getDate());

    }

    @Test
    void getUserName() throws DatabaseUserException {
        when(userRepository.findUserById(100)).thenReturn(userDTO);
        when(userRepository.findUserById(100).getFirstName()).thenReturn("Max");
        when(userRepository.findUserById(100).getLastName()).thenReturn("Mustermann");
        assertEquals("Max Mustermann" , inboxControl.getUserName(100));
    }

    @Test
    void getUserNameDataAccessResourceFailureException(){
        when(userRepository.findUserById(100)).thenThrow(DataAccessResourceFailureException.class);
        DatabaseUserException thrown = Assertions.assertThrows( DatabaseUserException.class, () ->
                inboxControl.getUserName(100));
        Assertions.assertEquals(Globals.LogMessage.CONNECTED, thrown.getMessage());
    }

    @Test
    void getUserNameDatabaseUserException(){
        when(userRepository.findUserById(100)).thenThrow(DataIntegrityViolationException.class);
        DatabaseUserException thrown = Assertions.assertThrows( DatabaseUserException.class, () ->
                inboxControl.getUserName(100));
        Assertions.assertEquals(Globals.LogMessage.ERROR, thrown.getMessage());
    }

    @Test
    void getSubject() throws DatabaseUserException {
        when(messageRepository.findMessageById(200)).thenReturn(messageDTO);
        when(messageRepository.findMessageById(200).getSubject()).thenReturn(subject);
        assertEquals(subject, inboxControl.getSubject(200));
    }

    @Test
    void getSubjectDataAccessResourceFailureException() {
        when(messageRepository.findMessageById(200)).thenThrow(DataAccessResourceFailureException.class);
        DatabaseUserException thrown = Assertions.assertThrows(DatabaseUserException.class, () ->
                inboxControl.getSubject(200));
        Assertions.assertEquals(Globals.LogMessage.CONNECTED, thrown.getMessage());
    }

    @Test
    void getSubjectDataBaseUserException() {
        when(messageRepository.findMessageById(200)).thenThrow(DataIntegrityViolationException.class);
        DatabaseUserException thrown = Assertions.assertThrows(DatabaseUserException.class, () ->
                inboxControl.getSubject(200));
        Assertions.assertEquals(Globals.LogMessage.ERROR, thrown.getMessage());
    }

    @Test
    void callProfileRouteForStudent() throws DatabaseUserException {
        when(userRepository.findUserById(100)).thenReturn(userDTO);
        when(userRepository.findUserById(100).getType()).thenReturn("st");
        assertEquals(Globals.Pages.PROFILE_VIEW + 100, inboxControl.callProfileRoute(100));
    }

    @Test
    void callProfileRouteForCompany() throws DatabaseUserException {
        when(userRepository.findUserById(100)).thenReturn(userDTO);
        when(userRepository.findUserById(100).getType()).thenReturn("cp");
        when(contactPersonRepository.findContactPersonById(100)).thenReturn(contactPerson);
        when(contactPersonRepository.findContactPersonById(100).getCompany()).thenReturn(company);
        when(contactPersonRepository.findContactPersonById(100).getCompany().getId()).thenReturn(300);
        assertEquals(Globals.Pages.COMPANYPROFILE_VIEW + 300, inboxControl.callProfileRoute(100));

    }

    @Test
    void callProfileRouteDataAccessResourceFailureException() {
        when(userRepository.findUserById(100)).thenThrow(DataAccessResourceFailureException.class);
        DatabaseUserException thrown = Assertions.assertThrows(DatabaseUserException.class, () ->
                inboxControl.callProfileRoute(100));
        Assertions.assertEquals(Globals.LogMessage.CONNECTED, thrown.getMessage());
    }

    @Test
    void callProfileRouteDataBaseUserException() {
        when(userRepository.findUserById(100)).thenThrow(DataIntegrityViolationException.class);
        DatabaseUserException thrown = Assertions.assertThrows(DatabaseUserException.class, () ->
                inboxControl.callProfileRoute(100));
        Assertions.assertEquals(Globals.LogMessage.ERROR, thrown.getMessage());
    }

    @Test
    void sendMessage() throws DatabaseUserException {
        try (MockedStatic<MessageFactory> classMock = mockStatic(MessageFactory.class)) {
            classMock.when(() -> MessageFactory.createMessage(messageDTO)).thenReturn(sentMessage);
            doReturn(sentMessage).when(messageRepository).save(sentMessage);
            doReturn(200).when(sentMessage).getId();
            assertEquals(sentMessage, inboxControl.sendMessage(messageDTO));
        }
    }

    @Test
    void sentMessageDataAccessResourceFailureException() {
        try (MockedStatic<MessageFactory> classMock = mockStatic(MessageFactory.class)) {
            classMock.when(() -> MessageFactory.createMessage(messageDTO)).thenReturn(sentMessage);
            when(messageRepository.save(sentMessage)).thenThrow(DataAccessResourceFailureException.class);
            DatabaseUserException thrown = Assertions.assertThrows(DatabaseUserException.class, () ->
                    inboxControl.sendMessage(messageDTO));
            Assertions.assertEquals(Globals.LogMessage.CONNECTED, thrown.getMessage());
        }
    }

    @Test
    void sentMessageDataBaseUserException() {
        try (MockedStatic<MessageFactory> classMock = mockStatic(MessageFactory.class)) {
            classMock.when(() -> MessageFactory.createMessage(messageDTO)).thenReturn(sentMessage);
            when(messageRepository.save(sentMessage)).thenThrow(DataIntegrityViolationException.class);
            DatabaseUserException thrown = Assertions.assertThrows(DatabaseUserException.class, () ->
                    inboxControl.sendMessage(messageDTO));
            Assertions.assertEquals(Globals.LogMessage.ERROR, thrown.getMessage());
        }
    }

    @Test
    void deleteMessage() throws DatabaseUserException {
        try (MockedStatic<MessageFactory> classMock = mockStatic(MessageFactory.class)) {
            classMock.when(() -> MessageFactory.createMessage(messageDTO)).thenReturn(sentMessage);
            doNothing().when(messageRepository).delete(sentMessage);
            doReturn(200).when(sentMessage).getId();
            assertEquals(sentMessage, inboxControl.deleteMessage(messageDTO));
        }
    }

    @Test
    void deleteMessageDataAccessResourceFailureException() {
        try (MockedStatic<MessageFactory> classMock = mockStatic(MessageFactory.class)) {
            classMock.when(() -> MessageFactory.createMessage(messageDTO)).thenReturn(sentMessage);
            doThrow(DataAccessResourceFailureException.class).when(messageRepository).delete(sentMessage);
            DatabaseUserException thrown = Assertions.assertThrows(DatabaseUserException.class, () ->
                    inboxControl.deleteMessage(messageDTO));
            Assertions.assertEquals(Globals.LogMessage.CONNECTED, thrown.getMessage());
        }
    }

    @Test
    void deleteMessageDataBaseUserException() {
        try (MockedStatic<MessageFactory> classMock = mockStatic(MessageFactory.class)) {
            classMock.when(() -> MessageFactory.createMessage(messageDTO)).thenReturn(sentMessage);
            doThrow(DataIntegrityViolationException.class).when(messageRepository).delete(sentMessage);
            DatabaseUserException thrown = Assertions.assertThrows(DatabaseUserException.class, () ->
                    inboxControl.deleteMessage(messageDTO));
            Assertions.assertEquals(Globals.LogMessage.ERROR, thrown.getMessage());
        }
    }

}