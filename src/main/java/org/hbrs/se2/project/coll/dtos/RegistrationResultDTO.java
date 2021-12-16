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
        UNEXPECTED_ERROR,
        COMPANY_NAME_MISSING,
        COMPANY_EMAIL_MISSING,
        COMPANY_PHONE_MISSING,
        COMPANY_FAX_MISSING,
        COMPANY_WEBSITE_MISSING,
        COMPANY_DESCRIPTION_MISSING,
        COMPANY_STREET_MISSING,
        COMPANY_HOUSENUMBER_MISSING,
        COMPANY_POSTALCODE_MISSING,
        COMPANY_CITY_MISSING,
        COMPANY_COUNTRY_MISSING,
        COMPANY_EMAIL_INVALID,
        COMPANY_ALREADY_REGISTERED
    }

    public List<ReasonType> getReasons();
    public void setReasons(List<ReasonType> reasons);
    public void addReason(ReasonType reason);
}
