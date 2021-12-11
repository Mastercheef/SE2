package org.hbrs.se2.project.coll.dtos;

import java.util.List;

public interface RegistrationResultDTO {
    public boolean getResult();

    public enum ReasonType {
        SUCCESS,
        SALUTATION_MISSING,
        TITLE_MISSING,
        FIRSTNAME_MISSING,
        LASTNAME_MISSING,
        DATEOFBIRTH_MISSING,
        PHONE_MISSING,
        PASSWORD_MISSING,
        EMAIL_MISSING,
        STREET_MISSING,
        HOUSENUMBER_MISSING,
        POSTALCODE_MISSING,
        CITY_MISSING,
        COUNTRY_MISSING,
        EMAIL_ALREADY_IN_USE,
        FIRSTNAME_INVALID,
        LASTNAME_INVALID,
        EMAIL_INVALID,
        PASSWORD_INVALID,
        EMAIL_UNEQUAL,
        PASSWORD_UNEQUAL,
        UNEXPECTED_ERROR
    }

    public List<ReasonType> getReasons();
    public void setReasons(List<ReasonType> reasons);
    public void addReason(ReasonType reason);
}
