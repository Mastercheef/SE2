package org.hbrs.se2.project.coll.dtos.impl;

import org.hbrs.se2.project.coll.dtos.JobAdvertisementDTO;
import org.hbrs.se2.project.coll.dtos.JobApplicationDTO;
import org.hbrs.se2.project.coll.dtos.StudentUserDTO;

public class JobApplicationDTOImpl implements JobApplicationDTO {
    private StudentUserDTO studentUserDTO;
    private JobAdvertisementDTO jobAdvertisementDTO;
    private String headline;
    private String applicationText;

    public void setStudentUserDTO(StudentUserDTO studentUserDTO) {
        this.studentUserDTO = studentUserDTO;
    }

    public void setJobAdvertisementDTO(JobAdvertisementDTO jobAdvertisementDTO) {
        this.jobAdvertisementDTO = jobAdvertisementDTO;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public void setApplicationText(String applicationText) {
        this.applicationText = applicationText;
    }

    @Override
    public StudentUserDTO getStudentUserDTO() { return studentUserDTO; }

    @Override
    public JobAdvertisementDTO getJobAdvertisementDTO() { return jobAdvertisementDTO; }

    @Override
    public String getHeadline() { return headline; }

    @Override
    public String getApplicationText() { return applicationText; }
}
