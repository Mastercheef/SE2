package org.hbrs.se2.project.coll.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

public class JobApplication {

    private int id;

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
}
