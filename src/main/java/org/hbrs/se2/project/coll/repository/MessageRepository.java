package org.hbrs.se2.project.coll.repository;

import org.hbrs.se2.project.coll.dtos.MessageDTO;
import org.hbrs.se2.project.coll.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public interface MessageRepository extends JpaRepository<Message, Integer> {

    MessageDTO findMessageById(int id);

    List<MessageDTO> findMessagesBySender(int sender);
    List<MessageDTO> findMessagesByRecipient(int recipient);
    List<MessageDTO> findMessagesBySubject(String subject);
    List<MessageDTO> findMessagesBySubjectAndDate(String subject, LocalDate date);
    List<MessageDTO> findMessagesByRecipientAndRead(int recipient, boolean read);
}
