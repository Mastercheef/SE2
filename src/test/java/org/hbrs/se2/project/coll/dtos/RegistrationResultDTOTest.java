package org.hbrs.se2.project.coll.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationResultDTOTest {

    @Test
    void enumRegistrationResult() {
        assertEquals("SUCCESS" , RegistrationResultDTO.ReasonType.SUCCESS.toString());
        assertEquals("SALUTATION_MISSING" , RegistrationResultDTO.ReasonType.SALUTATION_MISSING.toString());
        assertEquals("TITLE_MISSING", RegistrationResultDTO.ReasonType.TITLE_MISSING.toString());
        assertEquals("FIRSTNAME_MISSING", RegistrationResultDTO.ReasonType.FIRSTNAME_MISSING.toString());
        assertEquals("LASTNAME_MISSING", RegistrationResultDTO.ReasonType.LASTNAME_MISSING.toString());
        assertEquals("DATEOFBIRTH_MISSING", RegistrationResultDTO.ReasonType.DATEOFBIRTH_MISSING.toString());
        assertEquals("PHONE_MISSING", RegistrationResultDTO.ReasonType.PHONE_MISSING.toString());
        assertEquals("PASSWORD_MISSING", RegistrationResultDTO.ReasonType.PASSWORD_MISSING.toString());
        assertEquals("STREET_MISSING", RegistrationResultDTO.ReasonType.STREET_MISSING.toString());
        assertEquals("HOUSENUMBER_MISSING", RegistrationResultDTO.ReasonType.HOUSENUMBER_MISSING.toString());
        assertEquals("POSTALCODE_MISSING", RegistrationResultDTO.ReasonType.POSTALCODE_MISSING.toString());
        assertEquals("CITY_MISSING", RegistrationResultDTO.ReasonType.CITY_MISSING.toString());
        assertEquals("COUNTRY_MISSING", RegistrationResultDTO.ReasonType.COUNTRY_MISSING.toString());
        assertEquals("EMAIL_ALREADY_IN_USE", RegistrationResultDTO.ReasonType.EMAIL_ALREADY_IN_USE.toString());
        assertEquals("FIRSTNAME_INVALID", RegistrationResultDTO.ReasonType.FIRSTNAME_INVALID.toString());
        assertEquals("LASTNAME_INVALID", RegistrationResultDTO.ReasonType.LASTNAME_INVALID.toString());
        assertEquals("EMAIL_INVALID", RegistrationResultDTO.ReasonType.EMAIL_INVALID.toString());
        assertEquals("PASSWORD_INVALID", RegistrationResultDTO.ReasonType.PASSWORD_INVALID.toString());
        assertEquals("EMAIL_UNEQUAL", RegistrationResultDTO.ReasonType.EMAIL_UNEQUAL.toString());
        assertEquals("PASSWORD_UNEQUAL", RegistrationResultDTO.ReasonType.PASSWORD_UNEQUAL.toString());
        assertEquals("UNEXPECTED_ERROR", RegistrationResultDTO.ReasonType.UNEXPECTED_ERROR.toString());
        assertEquals("COMPANY_NAME_MISSING", RegistrationResultDTO.ReasonType.COMPANY_NAME_MISSING.toString());
        assertEquals("COMPANY_EMAIL_MISSING", RegistrationResultDTO.ReasonType.COMPANY_EMAIL_MISSING.toString());
        assertEquals("COMPANY_PHONE_MISSING", RegistrationResultDTO.ReasonType.COMPANY_PHONE_MISSING.toString());
        assertEquals("COMPANY_FAX_MISSING", RegistrationResultDTO.ReasonType.COMPANY_FAX_MISSING.toString());
        assertEquals("COMPANY_WEBSITE_MISSING", RegistrationResultDTO.ReasonType.COMPANY_WEBSITE_MISSING.toString());
        assertEquals("COMPANY_DESCRIPTION_MISSING", RegistrationResultDTO.ReasonType.COMPANY_DESCRIPTION_MISSING.toString());
        assertEquals("COMPANY_HOUSENUMBER_MISSING", RegistrationResultDTO.ReasonType.COMPANY_HOUSENUMBER_MISSING.toString());
        assertEquals("COMPANY_POSTALCODE_MISSING", RegistrationResultDTO.ReasonType.COMPANY_POSTALCODE_MISSING.toString());
        assertEquals("COMPANY_CITY_MISSING", RegistrationResultDTO.ReasonType.COMPANY_CITY_MISSING.toString());
        assertEquals("COMPANY_COUNTRY_MISSING", RegistrationResultDTO.ReasonType.COMPANY_COUNTRY_MISSING.toString());
        assertEquals("COMPANY_EMAIL_INVALID", RegistrationResultDTO.ReasonType.COMPANY_EMAIL_INVALID.toString());
        assertEquals("COMPANY_ALREADY_REGISTERED", RegistrationResultDTO.ReasonType.COMPANY_ALREADY_REGISTERED.toString());
    }
}