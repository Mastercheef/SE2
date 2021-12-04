package org.hbrs.se2.project.coll.dtos;

import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.ContactPerson;

import java.time.LocalDate;

public interface RecruitmentAdvertisingDTO {

    public boolean       getTemporaryEmployment();
    public String        getTypeOfEmployment();
    public short         getWorkingHours();
    public String        getRequirements();
    public Address       getAddress();
    public LocalDate     getStartOfWork();
    public LocalDate     getEndOfWork();
    public ContactPerson getContactPerson();
    public String        getJobDescription();
    public String        getJobTitle();
    public int           getId();
}
