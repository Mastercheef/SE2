package org.hbrs.se2.project.coll.dtos.impl;


public class StellenausschreibungDTOimpl implements StellenausschreibungDTO {



    private String companyName;
    private String formOfEmployment;
    private byte workingHours;
    private String beginnOfJob;
    private String workingLocation;
    private String jobDescription;
    private String requirementForApplicants;
    private String businessAdress;
    private boolean temporaryEmployment;
    private String dateOfTemporaryEmployment;
    private String contactPerson;
    private String emailAdress;

    @Override
    public String getCompanyName() {
        return companyName;
    }

    @Override
    public String getFormOfEmployment() {
        return formOfEmployment;
    }

    @Override
    public byte getWorkingHours() {
        return workingHours;
    }

    @Override
    public String getBeginnOfJob() {
        return beginnOfJob;
    }

    @Override
    public String getWorkingLocation() {
        return workingLocation;
    }

    @Override
    public String getJobDescription() {
        return jobDescription;
    }

    @Override
    public String getRequirementForApplicants() {
        return requirementForApplicants;
    }

    @Override
    public String getBusinessAdress() {
        return businessAdress;
    }



    @Override
    public boolean getTemporaryEmployment() {
        return temporaryEmployment;
    }

    @Override
    public String getDateOfTemporaryEmployment() {
        return dateOfTemporaryEmployment;
    }

    @Override
    public String getContactPerson() {
        return contactPerson;
    }

    @Override
    public String getEmailAdress() {
        return emailAdress;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setFormOfEmployment(String formOfEmployment) {
        this.formOfEmployment = formOfEmployment;
    }

    public void setWorkingHours(byte workingHours) {
        this.workingHours = workingHours;
    }

    public void setBeginnOfJob(String beginnOfJob) {
        this.beginnOfJob = beginnOfJob;
    }

    public void setWorkingLocation(String workingLocation) {
        this.workingLocation = workingLocation;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public void setRequirementForApplicants(String requirementForApplicants) {
        this.requirementForApplicants = requirementForApplicants;
    }

    public void setBusinessAdress(String businessAdress) {
        this.businessAdress = businessAdress;
    }

    public void setTemporaryEmployment(boolean temporaryEmployment) {
        this.temporaryEmployment = temporaryEmployment;
    }

    public void setDateOfTemporaryEmployment(String dateOfTemporaryEmployment) {
        this.dateOfTemporaryEmployment = dateOfTemporaryEmployment;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }
}
