package org.hbrs.se2.project.coll.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationResultDTOTest {

    @Test
    void enumRegistrationResult() {
        assertEquals("SUCCESS" , RegistrationResultDTO.ReasonType.SUCCESS.toString());
        assertEquals("SALUTATION_MISSING" , RegistrationResultDTO.ReasonType.SALUTATION_MISSING.toString());
    }

    @Test
    void getResult() {
    }

    @Test
    void getReasons() {
    }
}