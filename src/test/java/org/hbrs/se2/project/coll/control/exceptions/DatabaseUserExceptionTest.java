package org.hbrs.se2.project.coll.control.exceptions;

import org.hbrs.se2.project.coll.dtos.RegistrationResultDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseUserExceptionTest {

    private static final String MESSAGE = "Wrong User";

    @Test
    void registrationResultMessage() {
        DatabaseUserException databaseUserException = new DatabaseUserException(MESSAGE);
        assertEquals(MESSAGE , databaseUserException.getMessage());
        assertEquals(databaseUserException.getErrorCode() , RegistrationResultDTO.ReasonType.DATABASE_USER_EXCEPTION);
    }

    @Test
    void registrationResultMesageThrowable() {
        DatabaseUserException databaseUserException = new DatabaseUserException(MESSAGE, new Throwable());
        assertEquals(MESSAGE , databaseUserException.getMessage());
        assertNotNull(databaseUserException.getCause());
        assertEquals(databaseUserException.getErrorCode() , RegistrationResultDTO.ReasonType.DATABASE_USER_EXCEPTION);
    }
    @Test
    void registrationResultMesageThrowableRegistrationResult() {
        DatabaseUserException databaseUserException = new DatabaseUserException(MESSAGE, new Throwable() , RegistrationResultDTO.ReasonType.CITY_MISSING);
        assertEquals(MESSAGE , databaseUserException.getMessage());
        assertNotNull(databaseUserException.getCause());
        assertEquals(databaseUserException.getErrorCode() , RegistrationResultDTO.ReasonType.CITY_MISSING);
    }


}