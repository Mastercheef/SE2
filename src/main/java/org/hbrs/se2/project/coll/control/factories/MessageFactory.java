package org.hbrs.se2.project.coll.control.factories;

import org.hbrs.se2.project.coll.dtos.CompanyDTO;
import org.hbrs.se2.project.coll.dtos.MessageDTO;
import org.hbrs.se2.project.coll.entities.Company;
import org.hbrs.se2.project.coll.entities.Message;

public class MessageFactory {
    public static Message createMessage(MessageDTO messageDTO) {
        Message message = new Message();
        message.setId(messageDTO.getId());
        message.setRecipient(messageDTO.getRecipient());
        message.setSender(messageDTO.getSender());
        message.setContent(messageDTO.getContent());
        return message;
    }
}