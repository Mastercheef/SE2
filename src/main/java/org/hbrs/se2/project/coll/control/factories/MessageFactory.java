package org.hbrs.se2.project.coll.control.factories;

import org.hbrs.se2.project.coll.dtos.MessageDTO;
import org.hbrs.se2.project.coll.entities.Message;

public class MessageFactory {

    private MessageFactory() {
        throw new IllegalStateException("Factory Class");
    }
    public static Message createMessage(MessageDTO messageDTO) {
        Message message = new Message();
        message.setId(messageDTO.getId());
        message.setRecipient(messageDTO.getRecipient());
        message.setSender(messageDTO.getSender());
        message.setContent(messageDTO.getContent());
        message.setSubject(messageDTO.getSubject());
        message.setDate(messageDTO.getDate());
        message.setType(messageDTO.getType());
        return message;
    }
}