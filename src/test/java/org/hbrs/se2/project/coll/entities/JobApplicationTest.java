package org.hbrs.se2.project.coll.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JobApplicationTest {

    private JobApplication jobApplication = new JobApplication();
    int id;

    @Test
    void testGetId() {
        id = 20;
        jobApplication.setId(id);
        assertEquals(20, jobApplication.getId());
    }
}
