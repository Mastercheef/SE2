package org.hbrs.se2.project.coll.entities;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class JobApplicationTest {

    private JobApplication jobApplication = new JobApplication();
    int id;

    StudentUser studentUser= new StudentUser();
    JobAdvertisement jobAdvertisement = new JobAdvertisement();

    private final String headline = "Headline";
    private final String text = "Text";
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
        jobApplication.setHeadline(headline);
        assertEquals(headline,jobApplication.getHeadline());
    }

    @Test
    void setHeadline() {
        assertNull(jobApplication.getHeadline());
        jobApplication.setHeadline(headline);
        assertNotNull(jobApplication.getHeadline());
    }

    @Test
    void getText() {
        jobApplication.setText(text);
        assertEquals(text ,  jobApplication.getText());
    }

    @Test
    void setText() {
        assertNull(jobApplication.getText());
        jobApplication.setText(text);
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
