package org.hbrs.se2.project.coll.dtos.impl;

import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.hbrs.se2.project.coll.entities.StudentUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class JobApplicationDTOImplTest {

    JobApplicationDTOImpl jobApplicationDTO;


    StudentUser studentUser;

    JobAdvertisement jobAdvertisement;

    String headline = "Headline 1";
    String applicationText = "Lorem Ipsum";
    @BeforeEach
    void setUp() {
        jobApplicationDTO = new JobApplicationDTOImpl();

        jobApplicationDTO.setStudentUser(studentUser);
    }

    @Test
    void getStudentUserDTO() {
        studentUser = new StudentUser();
        jobApplicationDTO.setStudentUser(studentUser);
        assertNotNull(jobApplicationDTO.getStudentUser());

    }

    @Test
    void getJobAdvertisementDTO() {
        jobAdvertisement = new JobAdvertisement();
        jobApplicationDTO.setJobAdvertisementDTO(jobAdvertisement);
        assertNotNull(jobApplicationDTO.getJobAdvertisement());
    }

    @Test
    void getHeadline() {
        jobApplicationDTO.setHeadline(headline);
        assertEquals( headline , jobApplicationDTO.getHeadline());
    }

    @Test
    void getApplicationText() {
        jobApplicationDTO.setText(applicationText);
        assertEquals(applicationText , jobApplicationDTO.getText());
    }
}