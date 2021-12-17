package org.hbrs.se2.project.coll.dtos.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginResultDTOImplTest {

    private String reason = "Succesful";

    LoginResultDTOImpl loginResultDTO = new LoginResultDTOImpl();
    @Test
    void getResult() {
        loginResultDTO.setResult(true);
        assertTrue(loginResultDTO.getResult());
    }

    @Test
    void getReason() {
        loginResultDTO.setReason(reason);
        assertNotNull(loginResultDTO.getReason());
        assertEquals(reason , loginResultDTO.getReason());
    }
}