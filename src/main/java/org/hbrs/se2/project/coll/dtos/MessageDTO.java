package org.hbrs.se2.project.coll.dtos;

import java.time.LocalDate;

public interface MessageDTO {
    public int getId();
    public int getSender();
    public int getRecipient();
    public String getContent();
    public int getSubject();
    public LocalDate getDate();
    public boolean getRead();
}
