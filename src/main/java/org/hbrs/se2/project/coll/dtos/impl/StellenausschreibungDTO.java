package org.hbrs.se2.project.coll.dtos.impl;



public interface StellenausschreibungDTO {

    String getCompanyName();

    String getFormOfEmployment();
    byte getWorkingHours();
    String getBeginnOfJob();
    String getWorkingLocation();

    String getJobDescription();

    String getRequirementForApplicants();
    String getBusinessAdress();
    boolean getTemporaryEmployment();
    String getDateOfTemporaryEmployment();
    String getContactPerson();
    String getEmailAdress();
}
