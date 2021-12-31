package org.hbrs.se2.project.coll.repository;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureEmbeddedDatabase(provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY )
@Sql( {"/schema.sql" , "/data.sql"})
class MessageRepositoryTest {

    @Autowired
    MessageRepository messageRepository;

    int id = 50000000;
    @Test
    void findMessageByIDNotNull() {
        assertNotNull(messageRepository.findMessageById(id));
    }

    @Test
    void findMessageByIDNull() {
        assertNull(messageRepository.findMessageById(50000123));
    }
    @Test
    void findMessageByID() {
        assertEquals("Frage bzgl. Super Engineer" , messageRepository.findMessageById(id).getSubject());
        assertEquals("hallo" , messageRepository.findMessageById(id).getContent());
        assertEquals( 20000000, messageRepository.findMessageById(id).getSender());
        assertEquals(LocalDate.of(2021,12,29), messageRepository.findMessageById(id).getDate());
        assertEquals(20000001 , messageRepository.findMessageById(id).getRecipient());
        assertEquals(true , messageRepository.findMessageById(id).getRead());
    }

    @Test
    void findMessagesByRecipientNotEmpty() {
        assertFalse(messageRepository.findMessagesByRecipient(20000001).isEmpty());
    }

    @Test
    void findMessagesByRecipientSize() {
        assertTrue(messageRepository.findMessagesByRecipient(20000001).size() == 3);
    }

    @Test
    void findMessagesByRecipientEmpty() {
        assertTrue(messageRepository.findMessagesByRecipient(20009001).isEmpty());
    }

    @Test
    void findMessagesByRecipientAndReadNotEmpty() {
        assertFalse(messageRepository.findMessagesByRecipientAndRead(20000001,true).isEmpty());
    }
    @Test
    void findMessagesByRecipientAndReadEmpty() {
        assertTrue(messageRepository.findMessagesByRecipientAndRead(20000000,true).isEmpty());
    }

    @Test
    void findMessagesByRecipientAndReadSize() {
        assertTrue(messageRepository.findMessagesByRecipientAndRead(20000001,true).size() == 2);
    }
}