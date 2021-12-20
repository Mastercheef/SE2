package org.hbrs.se2.project.coll.dtos.impl;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MessageDTOImplTest {

    MessageDTOImpl messageDTO = new MessageDTOImpl();
    @Test
    void getId() {
        messageDTO.setId(100);
        assertEquals(100 , messageDTO.getId());
    }

    @Test
    void getSender() {
        messageDTO.setSender(200);
        assertEquals(200 , messageDTO.getSender());
    }

    @Test
    void getRecipient() {
        messageDTO.setRecipient(300);
        assertEquals(300 , messageDTO.getRecipient());
    }

    @Test
    void getContent() {
        messageDTO.setContent("Hello World");
        assertEquals("Hello World" , messageDTO.getContent());
    }

    @Test
    void getSubject() {
        messageDTO.setSubject("Betreff");
        assertEquals("Betreff" , messageDTO.getSubject());
    }

    @Test
    void getDate() {
        messageDTO.setDate(LocalDate.of(2000,1,2));

    }

    @Test
    void getRead() {
        messageDTO.setRead(true);
        assertTrue(messageDTO.getRead());
    }
}