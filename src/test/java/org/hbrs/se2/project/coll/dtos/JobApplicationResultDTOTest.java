package org.hbrs.se2.project.coll.dtos;

import org.hbrs.se2.project.coll.dtos.impl.JobApplicationResultDTOImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JobApplicationResultDTOTest {

    JobApplicationResultDTO jobApplicationResultDTO = new JobApplicationResultDTOImpl();

    @Test
    void reasonTypeTest() {
        assertEquals("HEADLINE_MISSING" , JobApplicationResultDTO.ReasonType.HEADLINE_MISSING.toString() );
        assertEquals("SUCCESS" , JobApplicationResultDTO.ReasonType.SUCCESS.toString() );
        assertEquals("TEXT_MISSING" , JobApplicationResultDTO.ReasonType.TEXT_MISSING.toString() );
        assertEquals("UNEXPECTED_ERROR" , JobApplicationResultDTO.ReasonType.UNEXPECTED_ERROR.toString() );
    }

}