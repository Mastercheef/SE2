package org.hbrs.se2.project.coll.dtos;

import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.hbrs.se2.project.coll.entities.StudentUser;

import java.time.LocalDate;


public interface JobApplicationDTO {
    public int getId();
    public StudentUser getStudentUser();
    public JobAdvertisement getJobAdvertisement();
    public String getHeadline();
    public String getText();
    public LocalDate getDate();
}
