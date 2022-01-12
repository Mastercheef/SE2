package org.hbrs.se2.project.coll.entities;

import org.junit.jupiter.api.Test;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class JobApplicationTest {

    private JobApplication jobApplication = new JobApplication();
    int id;

    StudentUser studentUser= new StudentUser();
    JobAdvertisement jobAdvertisement = new JobAdvertisement();

    private static final String HEADLINE = "Headline";
    private static final String TEXT = "Text";
    private final LocalDate localDate = LocalDate.of(2000,1,2);
    @Test
    void testGetId() {
        id = 20;
        jobApplication.setId(id);
        assertEquals(20, jobApplication.getId());
    }


    @Test
    void setStudentUser() {
        assertNull(jobApplication.getStudentUser());
        jobApplication.setStudentUser(studentUser);
        assertNotNull(jobApplication.getStudentUser());
    }



    @Test
    void setJobAdvertisement() {
        assertNull(jobApplication.getJobAdvertisement());
        jobApplication.setJobAdvertisement(jobAdvertisement);
        assertNotNull(jobApplication.getJobAdvertisement());
    }

    @Test
    void getHeadline() {
        jobApplication.setHeadline(HEADLINE);
        assertEquals(HEADLINE,jobApplication.getHeadline());
    }

    @Test
    void setHeadline() {
        assertNull(jobApplication.getHeadline());
        jobApplication.setHeadline(HEADLINE);
        assertNotNull(jobApplication.getHeadline());
    }

    @Test
    void getText() {
        jobApplication.setText(TEXT);
        assertEquals(TEXT,  jobApplication.getText());
    }

    @Test
    void setText() {
        assertNull(jobApplication.getText());
        jobApplication.setText(TEXT);
        assertNotNull(jobApplication.getText());
    }

    @Test
    void getDate() {
        jobApplication.setDate(localDate);
        assertEquals(localDate , jobApplication.getDate());
    }

    @Test
    void setDate() {
        assertNull(jobApplication.getDate());
        jobApplication.setDate(localDate);
        assertNotNull(jobApplication.getDate());
    }
}
