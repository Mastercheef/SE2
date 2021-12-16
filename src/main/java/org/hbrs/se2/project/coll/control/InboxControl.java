package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.control.factories.MessageFactory;
import org.hbrs.se2.project.coll.dtos.MessageDTO;
import org.hbrs.se2.project.coll.dtos.impl.MessageDTOImpl;
import org.hbrs.se2.project.coll.entities.Message;
import org.hbrs.se2.project.coll.repository.ContactPersonRepository;
import org.hbrs.se2.project.coll.repository.JobAdvertisementRepository;
import org.hbrs.se2.project.coll.repository.MessageRepository;
import org.hbrs.se2.project.coll.repository.UserRepository;
import org.hbrs.se2.project.coll.util.Globals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Component
public class InboxControl {

    private static final Logger LOGGER = LoggerFactory.getLogger(InboxControl.class);


    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobAdvertisementRepository jobAdvertisementRepository;

    @Autowired
    private ContactPersonRepository contactPersonRepository;

    public List<MessageDTO> getMessages(int recipient) {
        return messageRepository.findMessagesByRecipient(recipient);
    }

    // Aufbereiten eines neuen MessageDTOs zum Senden einer Nachricht
    public MessageDTO prepareSending(MessageDTO selectedMessage, String message) {
        MessageDTOImpl newMessage = new MessageDTOImpl();
        newMessage.setContent(message);
        newMessage.setSender(selectedMessage.getRecipient());
        newMessage.setRecipient(selectedMessage.getSender());
        newMessage.setSubject(selectedMessage.getSubject());
        newMessage.setDate(LocalDate.now());
        return newMessage;
    }

    // Senden einer Nachricht an einen Nutzer
    public void sendMessage(MessageDTO messageDTO) throws  DatabaseUserException {
        try {
            Message message = MessageFactory.createMessage(messageDTO);
            this.messageRepository.save(message);

        } catch (Exception exception) {
            LOGGER.info("LOG: {}" , exception.toString());
            if (exception instanceof org.springframework.dao.DataAccessResourceFailureException) {
                throw new DatabaseUserException("Während der Verbindung zur Datenbank mit JPA ist " +
                        "ein Fehler aufgetreten.");
            } else {
                throw new DatabaseUserException("Es ist ein unerwarteter Fehler aufgetreten.");
            }
        }
    }

    // Löschen einer Nachricht aus der Datenbank
    public void deleteMessage(MessageDTO messageDTO) throws DatabaseUserException {
        try {
            Message message = MessageFactory.createMessage(messageDTO);
            this.messageRepository.delete(message);

        } catch (Exception exception) {
            LOGGER.info("LOG: {}" , exception.toString());
            if (exception instanceof org.springframework.dao.DataAccessResourceFailureException) {
                throw new DatabaseUserException("Während der Verbindung zur Datenbank mit JPA ist " +
                        "ein Fehler aufgetreten.");
            } else {
                throw new DatabaseUserException("Es ist ein unerwarteter Fehler aufgetreten.");
            }
        }
    }

    // Einlesen von Nutzer ID, Rückgabe des Namens
    public String getUserName(int id) throws DatabaseUserException {
        try {
            return (this.userRepository.findUserById(id).getFirstName() + " " +
                    this.userRepository.findUserById(id).getLastName());
        } catch (Exception exception) {
            LOGGER.info("LOG: {}" ,  exception.toString());
            if (exception instanceof org.springframework.dao.DataAccessResourceFailureException) {
                throw new DatabaseUserException("Während der Verbindung zur Datenbank mit JPA ist " +
                        "ein Fehler aufgetreten.");
            } else {
                throw new DatabaseUserException("Es ist ein unerwarteter Fehler aufgetreten.");
            }
        }
    }

    public String getSubject(int subject) throws DatabaseUserException {
        try {
            return this.jobAdvertisementRepository.findJobAdvertisementById(subject).getJobTitle();

        } catch (Exception exception) {
            LOGGER.info("LOG: {}" ,  exception.toString());
            if (exception instanceof org.springframework.dao.DataAccessResourceFailureException) {
                throw new DatabaseUserException("Während der Verbindung zur Datenbank mit JPA ist " +
                        "ein Fehler aufgetreten.");
            } else {
                throw new DatabaseUserException("Es ist ein unerwarteter Fehler aufgetreten.");
            }
        }
    }

    // Checks if Sender is either a student or a contact person.
    // If student: Returns route to student profile
    // Else: Returns route to company profile
    public String callProfileRoute(int id) throws DatabaseUserException {
        try {
            String type = userRepository.findUserById(id).getType();
            if(Objects.equals(type, "st"))
                return (Globals.Pages.PROFILE_VIEW + id);
            else
                return (Globals.Pages.COMPANYPROFILE_VIEW +
                        contactPersonRepository.findContactPersonById(id).getCompany().getId());

        } catch (Exception exception) {
            LOGGER.info("LOG: {}" ,  exception.toString());
            if (exception instanceof org.springframework.dao.DataAccessResourceFailureException) {
                throw new DatabaseUserException("Während der Verbindung zur Datenbank mit JPA ist " +
                        "ein Fehler aufgetreten.");
            } else {
                throw new DatabaseUserException("Es ist ein unerwarteter Fehler aufgetreten.");
            }
        }
    }

    // Update read-status of a message in DB
    public void setMessageAsRead(MessageDTO message) throws DatabaseUserException {
        MessageDTOImpl readMessage = new MessageDTOImpl();
        readMessage.setId(message.getId());
        readMessage.setSender(message.getSender());
        readMessage.setRecipient(message.getRecipient());
        readMessage.setSubject(message.getSubject());
        readMessage.setDate(message.getDate());
        readMessage.setContent(message.getContent());

        try {
            Message updatedMessage = MessageFactory.createMessage(readMessage);
            updatedMessage.setRead(true);
            messageRepository.save(updatedMessage);
        } catch (Exception exception) {
            LOGGER.info("LOG: {}" ,  exception.toString());
            if (exception instanceof org.springframework.dao.DataAccessResourceFailureException) {
                throw new DatabaseUserException("Während der Verbindung zur Datenbank mit JPA ist " +
                        "ein Fehler aufgetreten.");
            } else {
                throw new DatabaseUserException("Es ist ein unerwarteter Fehler aufgetreten.");
            }
        }
    }
}
