package org.hbrs.se2.project.coll.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table( name ="col_tab_message" , schema = "collhbrs" )
public class Message {
    private int         id;
    private int         sender;
    private int         recipient;
    private String      content;
    private String      subject;
    private LocalDate   date;
    private boolean     read;
    private String      type;

    @Id
    @GeneratedValue(
            generator = "message_id"
    )
    @SequenceGenerator(
            name = "message_id",
            sequenceName = "collhbrs.col_seq_message_id"
    )
    @Column(name = "message_id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "sender_id")
    public int getSender() {
        return sender;
    }
    public void setSender(int sender) {
        this.sender = sender;
    }

    @Basic
    @Column(name = "recipient_id")
    public int getRecipient() { return recipient; }
    public void setRecipient(int recipient) { this.recipient = recipient; }

    @Basic
    @Column(name = "content")
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    @Basic
    @Column(name = "subject")
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    @Basic
    @Column(name = "date")
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    @Basic
    @Column(name = "read")
    public boolean getRead() { return read; }
    public void setRead(boolean read) { this.read = read; }

    @Basic
    @Column(name = "type")
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

}
