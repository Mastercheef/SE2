package org.hbrs.se2.project.coll.dtos.impl;

import org.hbrs.se2.project.coll.dtos.RegistrationResultDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationResultDTOImplTest {

    RegistrationResultDTOImpl registrationResultDTO= new RegistrationResultDTOImpl();
    @BeforeEach
    void setUp() {
        registrationResultDTO.addReason(RegistrationResultDTO.ReasonType.CITY_MISSING);
        registrationResultDTO.addReason(RegistrationResultDTO.ReasonType.COMPANY_CITY_MISSING);
        registrationResultDTO.addReason(RegistrationResultDTO.ReasonType.COMPANY_WEBSITE_MISSING);
    }
    @Test
    void getResult() {
        registrationResultDTO.setResult(true);
        assertTrue(registrationResultDTO.getResult());
    }

    @Test
    void getReasons() {

        List<RegistrationResultDTO.ReasonType> list = registrationResultDTO.getReasons();
        assertEquals(RegistrationResultDTO.ReasonType.CITY_MISSING , list.get(0));
        assertEquals(RegistrationResultDTO.ReasonType.COMPANY_CITY_MISSING , list.get(1));
        assertEquals(RegistrationResultDTO.ReasonType.COMPANY_WEBSITE_MISSING , list.get(2));

    }

    @Test
    void addReason() {
        assertEquals(registrationResultDTO.getReasons().size() , 3);
    }

    @Test
    void testToString() {
        assertEquals("[CITY_MISSING, COMPANY_CITY_MISSING, COMPANY_WEBSITE_MISSING]" , registrationResultDTO.toString());
    }

    @Test
    void getReasonList() {
        List<RegistrationResultDTO.ReasonType> reasonTypeList = new LinkedList<>();
        reasonTypeList.add(RegistrationResultDTO.ReasonType.CITY_MISSING);
        reasonTypeList.add(RegistrationResultDTO.ReasonType.COMPANY_EMAIL_INVALID);
        reasonTypeList.add(RegistrationResultDTO.ReasonType.COMPANY_CITY_MISSING);
        registrationResultDTO.setReasons(reasonTypeList);

        assertEquals( 3 , registrationResultDTO.getReasons().size());
        assertEquals(RegistrationResultDTO.ReasonType.CITY_MISSING , registrationResultDTO.getReasons().get(0));
    }
}