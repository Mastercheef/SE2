package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.control.factories.MessageFactory;
import org.hbrs.se2.project.coll.dtos.MessageDTO;
import org.hbrs.se2.project.coll.entities.Message;
import org.hbrs.se2.project.coll.repository.JobAdvertisementRepository;
import org.hbrs.se2.project.coll.repository.MessageRepository;
import org.hbrs.se2.project.coll.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InboxControl {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobAdvertisementRepository jobAdvertisementRepository;

    public List<MessageDTO> getMessages(int recipient) {
        return messageRepository.findMessagesByRecipient(recipient);
    }

    // Löschen einer Nachricht aus der Datenbank
    public void deleteMessage(MessageDTO messageDTO) throws DatabaseUserException {
        try {
            Message message = MessageFactory.createMessage(messageDTO);
            this.messageRepository.delete(message);

        } catch (Exception exception) {
            System.out.println("LOG: " + exception);
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
            System.out.println("LOG: " + exception);
            if (exception instanceof org.springframework.dao.DataAccessResourceFailureException) {
                throw new DatabaseUserException("Während der Verbindung zur Datenbank mit JPA ist " +
                        "ein Fehler aufgetreten.");
            } else {
                throw new DatabaseUserException("Es ist ein unerwarteter Fehler aufgetreten.");
            }
        }
    }

    public Object getSubject(int subject) throws DatabaseUserException{
        try {
            return this.jobAdvertisementRepository.findJobAdvertisementById(subject).getJobTitle();

        } catch (Exception exception) {
            System.out.println("LOG: " + exception);
            if (exception instanceof org.springframework.dao.DataAccessResourceFailureException) {
                throw new DatabaseUserException("Während der Verbindung zur Datenbank mit JPA ist " +
                        "ein Fehler aufgetreten.");
            } else {
                throw new DatabaseUserException("Es ist ein unerwarteter Fehler aufgetreten.");
            }
        }
    }
}
