package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.control.factories.MessageFactory;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.dtos.impl.MessageDTOImpl;
import org.hbrs.se2.project.coll.entities.Message;
import org.hbrs.se2.project.coll.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ContactingControl {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactingControl.class);

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private JobAdvertisementRepository jobAdvertisementRepository;

    @Autowired
    private ContactPersonRepository contactPersonRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    public String getJobTitle(int id) {
        return jobAdvertisementRepository.findJobAdvertisementById(id).getJobTitle();
    }

    public String getCompanyName(int id) {
        return companyRepository.findCompanyProfileById(id).getCompanyName();
    }

    public int getContactPersonId(int id) { return contactPersonRepository.findContactPersonByCompanyId(id).getId(); }

    public boolean sendMessage(String content, int sender, int recipient, String subject,
                            LocalDate date) throws DatabaseUserException {
        try {
            // Create new message
            MessageDTOImpl newMessage = new MessageDTOImpl();
            newMessage.setContent(content);
            newMessage.setSender(sender);
            newMessage.setRecipient(recipient);
            newMessage.setSubject(subject);
            newMessage.setDate(date);

            // Send the message / save in DB
            Message message = MessageFactory.createMessage(newMessage);
            messageRepository.save(message);
            return true;
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

    public boolean checkIfUserIsAllowedToSendMessage(UserDTO user, int reveiverId) {
        UserDTO receiver = userRepository.findUserById(reveiverId);
        return (receiver != null && user.getId()  > 0 && !(receiver.getType() == "st" && user.getType() == "st"));
    }

    public boolean checkUrlParameterValid(int userId, int companyId, int jobId) {
        return userId > 0 && companyId > 0 && jobId > 0 || userId > 0 && companyId < 1 && jobId < 1;
    }

}
