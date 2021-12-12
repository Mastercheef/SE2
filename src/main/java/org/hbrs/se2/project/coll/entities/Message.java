package org.hbrs.se2.project.coll.entities;

import javax.persistence.*;

@Entity
@Table( name ="col_tab_message" , schema = "collhbrs" )
public class Message {
    private int id;
    private int sender;
    private int recipient;
    private String content;

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

}
