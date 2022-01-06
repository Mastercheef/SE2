package org.hbrs.se2.project.coll.dtos.impl;

import org.hbrs.se2.project.coll.dtos.JobApplicationDTO;
import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.hbrs.se2.project.coll.entities.StudentUser;

import java.time.LocalDate;
public class JobApplicationDTOImpl implements JobApplicationDTO {
    private int id;
    private StudentUser studentUser;
    private JobAdvertisement jobAdvertisement;
    private String headline;
    private String text;
    private LocalDate date;

    public void setStudentUser(StudentUser studentUser) {
        this.studentUser = studentUser;
    }

    public void setJobAdvertisementDTO(JobAdvertisement jobAdvertisement) {
        this.jobAdvertisement = jobAdvertisement;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public int getId() { return id; }

    @Override
    public StudentUser getStudentUser() { return studentUser; }

    @Override
    public JobAdvertisement getJobAdvertisement() { return jobAdvertisement; }

    @Override
    public String getHeadline() { return headline; }

    @Override
    public String getText() { return text; }

    @Override
    public LocalDate getDate() { return this.date; }
}
