package org.hbrs.se2.project.coll.repository;

import org.hbrs.se2.project.coll.dtos.MessageDTO;
import org.hbrs.se2.project.coll.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MessageRepository extends JpaRepository<Message, Integer> {

    MessageDTO findMessageById(int id);
    MessageDTO findMessageBySender(int sender);
    MessageDTO findMessageByRecipient(int recipient);

    List<MessageDTO> findMessagesByRecipient(int recipient);
}
