package org.hbrs.se2.project.coll.dtos.impl;

import org.hbrs.se2.project.coll.dtos.JobApplicationResultDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JobApplicationResultDTOImplTest {

    JobApplicationResultDTOImpl jobApplicationResultDTO = new JobApplicationResultDTOImpl();
    @Test
    void getResult() {
        jobApplicationResultDTO.setResult(true);
        assertTrue(jobApplicationResultDTO.getResult());
    }

    @Test
    void getReasons() {
        jobApplicationResultDTO.addReason(JobApplicationResultDTO.ReasonType.HEADLINE_MISSING);
        assertNotNull(jobApplicationResultDTO.getReasons().size());
    }

    @Test
    void getApplicationID() {
        jobApplicationResultDTO.setApplicationID(100);
        assertEquals(100 ,jobApplicationResultDTO.getApplicationID());
    }
}