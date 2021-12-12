package org.hbrs.se2.project.coll.dtos.impl;

import org.hbrs.se2.project.coll.dtos.MessageDTO;

public class MessageDTOImpl implements MessageDTO {
    private int id;
    private int sender;
    private int recipient;
    private String content;

    public void setId(int id) { this.id = id; }

    public void setSender(int sender) { this.sender = sender; }

    public void setRecipient(int recipient) { this.recipient = recipient; }

    public void setContent(String content) { this.content = content; }

    @Override
    public int getId() { return this.id; }

    @Override
    public int getSender() { return this.sender; }

    @Override
    public int getRecipient() { return this.recipient; }

    @Override
    public String getContent() { return this.content; }
}
