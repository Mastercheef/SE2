package org.hbrs.se2.project.coll.dtos.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class JobApplicationDTOImplTest {

    JobApplicationDTOImpl jobApplicationDTO;


    StudentUserDTOImpl studentUserDTO;

    JobAdvertisementDTOimpl jobAdvertisementDTOimpl;

    String headline = "Headline 1";
    String applicationText = "Lorem Ipsum";
    @BeforeEach
    void setUp() {
        jobApplicationDTO = new JobApplicationDTOImpl();

        jobApplicationDTO.setStudentUserDTO(studentUserDTO);
    }

    @Test
    void getStudentUserDTO() {
        studentUserDTO = new StudentUserDTOImpl();
        jobApplicationDTO.setStudentUserDTO(studentUserDTO);
        assertNotNull(jobApplicationDTO.getStudentUserDTO());

    }

    @Test
    void getJobAdvertisementDTO() {
        jobAdvertisementDTOimpl = new JobAdvertisementDTOimpl();
        jobApplicationDTO.setJobAdvertisementDTO(jobAdvertisementDTOimpl);
        assertNotNull(jobApplicationDTO.getJobAdvertisementDTO());
    }

    @Test
    void getHeadline() {
        jobApplicationDTO.setHeadline(headline);
        assertEquals( headline , jobApplicationDTO.getHeadline());
    }

    @Test
    void getApplicationText() {
        jobApplicationDTO.setApplicationText(applicationText);
        assertEquals(applicationText , jobApplicationDTO.getApplicationText());
    }
}