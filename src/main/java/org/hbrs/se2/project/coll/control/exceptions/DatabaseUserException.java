package org.hbrs.se2.project.coll.control.exceptions;

import org.hbrs.se2.project.coll.dtos.RegistrationResultDTO;


public class DatabaseUserException extends Exception {

    private static final long serialVersionUID = -8460356990632230194L;

    private RegistrationResultDTO.ReasonType reasonType = RegistrationResultDTO.ReasonType.DATABASE_USER_EXCEPTION;
    public DatabaseUserException(String message) {
        super(message);
    }

    public DatabaseUserException(String message, Throwable cause) {
        super(message,cause);
    }

    public DatabaseUserException(String message, Throwable cause, RegistrationResultDTO.ReasonType reasonType) {
        super(message, cause);
        this.reasonType = reasonType;
    }

    public RegistrationResultDTO.ReasonType getErrorCode() {
        return reasonType;
    }


}
