package org.hbrs.se2.project.coll.repository;

import org.hbrs.se2.project.coll.dtos.MessageDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;
    private MessageDTO messageDTO;
    private List<MessageDTO> messages;

    @Test
    void findMessageByIdTest() {
        messageDTO = messageRepository.findMessageById(700000000);
        assertEquals(messageDTO.getContent(), "Hallo, hierbei handelt es sich um eine Testnachricht. Großartig.");
        assertEquals(messageDTO.getSender(), 20000016);
        assertEquals(messageDTO.getRecipient(), 20000012);
    }

    @Test
    void findMessageBySenderTest() {
        messageDTO = messageRepository.findMessageBySender(20000016);
        assertEquals(messageDTO.getId(), 700000000);
        assertEquals(messageDTO.getContent(), "Hallo, hierbei handelt es sich um eine Testnachricht. Großartig.");
        assertEquals(messageDTO.getRecipient(), 20000012);
    }

    @Test
    void findMessageByRecipientTest() {
        messageDTO = messageRepository.findMessageByRecipient(20000012);
        assertEquals(messageDTO.getId(), 700000000);
        assertEquals(messageDTO.getSender(), 20000016);
        assertEquals(messageDTO.getContent(), "Hallo, hierbei handelt es sich um eine Testnachricht. Großartig.");
    }

    @Test
    void findMessagesByRecipientTest() {
        messages = messageRepository.findMessagesByRecipient(20000012);
        assertEquals(messages.get(0).getId(), 700000000);
        assertEquals(messages.get(0).getSender(), 20000016);
        assertEquals(messages.get(0).getContent(), "Hallo, hierbei handelt es sich um eine " +
                "Testnachricht. Großartig.");
    }

}