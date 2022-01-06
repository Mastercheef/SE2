package org.hbrs.se2.project.coll.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table( name ="col_tab_application" , schema = "collhbrs" )
public class JobApplication {
    private int id;
    private StudentUser studentUser;
    private JobAdvertisement jobAdvertisement;
    private String headline;
    private String text;
    private LocalDate date;

    @Id
    @GeneratedValue(
            generator = "application_id"
    )
    @SequenceGenerator(
            name = "application_id",
            sequenceName = "collhbrs.col_seq_application_id"
    )
    @Column(name = "application_id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // Man muss den basic user nutzen nicht student user
    @ManyToOne()
    @JoinColumn(name="user_id")
    public StudentUser getStudentUser() { return studentUser; }
    public void setStudentUser(StudentUser studentUser) { this.studentUser = studentUser; }

    @ManyToOne()
    @JoinColumn(name="advertisement_id")
    public JobAdvertisement getJobAdvertisement() { return jobAdvertisement; }
    public void setJobAdvertisement(JobAdvertisement jobAdvertisement) { this.jobAdvertisement = jobAdvertisement; }

    @Basic
    @Column(name = "headline")
    public String getHeadline() {
        return headline;
    }
    public void setHeadline(String headline) {
        this.headline = headline;
    }

    @Basic
    @Column(name = "text")
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "date")
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
}
