package org.hbrs.se2.project.coll.dtos.impl;

import org.hbrs.se2.project.coll.dtos.MessageDTO;

import java.time.LocalDate;

public class MessageDTOImpl implements MessageDTO {
    private int id;
    private int sender;
    private int recipient;
    private String content;
    private String subject;
    private LocalDate date;
    private boolean read;

    public void setId(int id) { this.id = id; }

    public void setSender(int sender) { this.sender = sender; }

    public void setRecipient(int recipient) { this.recipient = recipient; }

    public void setContent(String content) { this.content = content; }

    public void setSubject(String subject) { this.subject = subject; }

    public void setDate(LocalDate date) { this.date = date; }

    public void setRead(boolean read) { this.read = read; }

    @Override
    public int getId() { return this.id; }

    @Override
    public int getSender() { return this.sender; }

    @Override
    public int getRecipient() { return this.recipient; }

    @Override
    public String getContent() { return this.content; }

    @Override
    public String getSubject() { return this.subject; }

    @Override
    public LocalDate getDate() { return this.date; }

    @Override
    public boolean getRead() { return this.read; }
}
