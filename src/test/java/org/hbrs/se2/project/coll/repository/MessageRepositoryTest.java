package org.hbrs.se2.project.coll.repository;

import org.hbrs.se2.project.coll.control.factories.MessageFactory;
import org.hbrs.se2.project.coll.dtos.MessageDTO;
import org.hbrs.se2.project.coll.dtos.impl.MessageDTOImpl;
import org.hbrs.se2.project.coll.entities.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MessageRepositoryTest {

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
        assertEquals(messageDTO.getSubject(), message.getSubject());
        assertEquals(messageDTO.getDate(), message.getDate());
        assertEquals(messageDTO.getRead(), message.getRead());
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
            assertEquals(messageDTOs.get(i).getSubject(), messages.get(i).getSubject());
            assertEquals(messageDTOs.get(i).getDate(), messages.get(i).getDate());
            assertEquals(messageDTOs.get(i).getRead(), messages.get(i).getRead());
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
            assertEquals(messageDTOs.get(i).getSubject(), messages.get(i).getSubject());
            assertEquals(messageDTOs.get(i).getDate(), messages.get(i).getDate());
            assertEquals(messageDTOs.get(i).getRead(), messages.get(i).getRead());
        }
        messageRepository.deleteAll(messages);
    }

    @Test
    void findMessagesBySubjectTest() {
        List<Message> messages = initMessages();
        messageRepository.saveAll(messages);

        List<MessageDTO> messageDTOs = messageRepository.findMessagesBySubject(messages.get(0).getSubject());

        for(int i = 0; i < messageDTOs.size(); i++) {
            assertEquals(messageDTOs.get(i).getId(), messages.get(i).getId());
            assertEquals(messageDTOs.get(i).getContent(), messages.get(i).getContent());
            assertEquals(messageDTOs.get(i).getSender(), messages.get(i).getSender());
            assertEquals(messageDTOs.get(i).getRecipient(), messages.get(i).getRecipient());
            assertEquals(messageDTOs.get(i).getDate(), messages.get(i).getDate());
            assertEquals(messageDTOs.get(i).getRead(), messages.get(i).getRead());
        }
        messageRepository.deleteAll(messages);
    }

    @Test
    void findMessagesBySubjectAndDateTest() {
        List<Message> messages = initMessages();
        messageRepository.saveAll(messages);

        List<MessageDTO> messageDTOs = messageRepository.findMessagesBySubjectAndDate(
                messages.get(0).getSubject(),
                messages.get(0).getDate()
        );

        for(int i = 0; i < messageDTOs.size(); i++) {
            assertEquals(messageDTOs.get(i).getId(), messages.get(i).getId());
            assertEquals(messageDTOs.get(i).getContent(), messages.get(i).getContent());
            assertEquals(messageDTOs.get(i).getSender(), messages.get(i).getSender());
            assertEquals(messageDTOs.get(i).getRecipient(), messages.get(i).getRecipient());
            assertEquals(messageDTOs.get(i).getDate(), messages.get(i).getDate());
            assertEquals(messageDTOs.get(i).getRead(), messages.get(i).getRead());
        }
        messageRepository.deleteAll(messages);
    }

    Message initMessage() {
        MessageDTOImpl newMessage = new MessageDTOImpl();
        newMessage.setContent("Hallo, hierbei handelt es sich um eine Testnachricht. Großartig.");
        newMessage.setSender(20000012);
        newMessage.setRecipient(20000000);
        newMessage.setSubject("Testbetreff1");
        newMessage.setDate(LocalDate.now());
        newMessage.setRead(false);

        return MessageFactory.createMessage(newMessage);
    }

    List<Message> initMessages() {
        List<Message> messages = new ArrayList<>();

        MessageDTOImpl message1 = new MessageDTOImpl();
        message1.setContent("Hallo, hierbei handelt es sich um eine Testnachricht. Großartig.");
        message1.setSender(20000012);
        message1.setRecipient(20000000);
        message1.setSubject("Testbetreff1");
        message1.setDate(LocalDate.now());
        message1.setRead(false);

        MessageDTOImpl message2 = new MessageDTOImpl();
        message2.setContent("Eine weitere Nachricht für dich.");
        message2.setSender(20000012);
        message2.setRecipient(20000000);
        message2.setSubject("Testbetreff2");
        message2.setDate(LocalDate.now());
        message2.setRead(false);

        messages.add(MessageFactory.createMessage(message1));
        messages.add(MessageFactory.createMessage(message2));
        return messages;
    }

}
