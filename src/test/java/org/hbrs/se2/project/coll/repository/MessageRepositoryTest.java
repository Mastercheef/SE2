package org.hbrs.se2.project.coll.repository;

import org.hbrs.se2.project.coll.control.factories.MessageFactory;
import org.hbrs.se2.project.coll.dtos.MessageDTO;
import org.hbrs.se2.project.coll.dtos.impl.MessageDTOImpl;
import org.hbrs.se2.project.coll.entities.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Test
    void findMessageByIdTest() {
        Message message = initMessage();
        messageRepository.save(message);

        MessageDTO messageDTO = messageRepository.findMessageById(message.getId());
        assertEquals(messageDTO.getContent(), message.getContent());
        assertEquals(messageDTO.getSender(), message.getSender());
        assertEquals(messageDTO.getRecipient(), message.getRecipient());
        messageRepository.delete(message);
    }

    @Test
    void findMessagesBySenderTest() {
        List<Message> messages = initMessages();
        messageRepository.saveAll(messages);

        List<MessageDTO> messageDTOs = messageRepository.findMessagesBySender(messages.get(0).getSender());

        for(int i = 0; i < messageDTOs.size(); i++) {
            assertEquals(messageDTOs.get(i).getId(), messages.get(i).getId());
            assertEquals(messageDTOs.get(i).getContent(), messages.get(i).getContent());
            assertEquals(messageDTOs.get(i).getRecipient(), messages.get(i).getRecipient());
        }
        messageRepository.deleteAll(messages);
    }

    @Test
    void findMessagesByRecipientTest() {
        List<Message> messages = initMessages();
        messageRepository.saveAll(messages);

        List<MessageDTO> messageDTOs = messageRepository.findMessagesByRecipient(messages.get(0).getRecipient());

        for(int i = 0; i < messageDTOs.size(); i++) {
            assertEquals(messageDTOs.get(i).getId(), messages.get(i).getId());
            assertEquals(messageDTOs.get(i).getContent(), messages.get(i).getContent());
            assertEquals(messageDTOs.get(i).getSender(), messages.get(i).getSender());
        }
        messageRepository.deleteAll(messages);
    }

    Message initMessage() {
        MessageDTOImpl newMessage = new MessageDTOImpl();
        newMessage.setContent("Hallo, hierbei handelt es sich um eine Testnachricht. Großartig.");
        newMessage.setSender(20000012);
        newMessage.setRecipient(20000000);

        return MessageFactory.createMessage(newMessage);
    }

    List<Message> initMessages() {
        List<Message> messages = new ArrayList<>();

        MessageDTOImpl message1 = new MessageDTOImpl();
        message1.setContent("Hallo, hierbei handelt es sich um eine Testnachricht. Großartig.");
        message1.setSender(20000012);
        message1.setRecipient(20000000);

        MessageDTOImpl message2 = new MessageDTOImpl();
        message2.setContent("Eine weitere Nachricht für dich.");
        message2.setSender(20000012);
        message2.setRecipient(20000000);

        messages.add(MessageFactory.createMessage(message1));
        messages.add(MessageFactory.createMessage(message2));
        return messages;
    }

}