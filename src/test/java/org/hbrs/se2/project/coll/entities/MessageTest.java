package org.hbrs.se2.project.coll.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    private Message message = new Message();
    int id;
    int sender;
    int recipient;
    String content;
    String subject;
    LocalDate date;
    boolean read;
    String type;

    @BeforeEach
    void setup(){
        id = 1;
        sender = 123;
        recipient = 456;
        content = "Hello World";
        subject = "validation";
        date = LocalDate.of(2021, 12, 27);
        read = true;
        type = "spam";
    }

    @Test
    void testGetId(){
        message.setId(id);
        assertEquals(1, message.getId());
    }

    @Test
    void testGetSender(){
        message.setSender(sender);
        assertEquals(123, message.getSender());
    }

    @Test
    void testGetRecipient(){
        message.setRecipient(recipient);
        assertEquals(456, message.getRecipient());
    }

    @Test
    void testGetContent(){
        message.setContent(content);
        assertEquals("Hello World", message.getContent());
    }

    @Test
    void testGetSubject(){
        message.setSubject(subject);
        assertEquals("validation", message.getSubject());
    }

    @Test
    void testGetDate(){
        message.setDate(date);
        assertEquals(LocalDate.of(2021, 12, 27), message.getDate());
    }

    @Test
    void testGetRead(){
        message.setRead(read);
        assertTrue(message.getRead());
    }

    @Test
    void testGetType(){
        message.setType(type);
        assertEquals("spam", message.getType());
    }
}
