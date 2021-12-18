package org.hbrs.se2.project.coll.control.exceptions;

import org.hbrs.se2.project.coll.util.Globals;

import javax.xml.crypto.Data;

public class DatabaseUserException extends Exception {

    private static final long serialVersionUID = -8460356990632230194L;

    private Integer errorCode;

    public DatabaseUserException(String message) {
        super(message);
    }

    public DatabaseUserException(String message, Throwable cause) {
        super(message,cause);
    }

    public DatabaseUserException(String message, Throwable cause, Globals.ErrorCodes errorCode) {
        super(message, cause);
        this.errorCode = errorCode.getCode();
    }

    public Integer getErrorCode() {
        return errorCode;
    }


}
