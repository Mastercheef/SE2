package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.control.factories.MessageFactory;
import org.hbrs.se2.project.coll.dtos.MessageDTO;
import org.hbrs.se2.project.coll.entities.Message;
import org.hbrs.se2.project.coll.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InboxControl {

    @Autowired
    private MessageRepository messageRepository;

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
}
