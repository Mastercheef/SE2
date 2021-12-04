package org.hbrs.se2.project.coll.dtos.impl;


import org.hbrs.se2.project.coll.dtos.RecruitmentAdvertisingDTO;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.ContactPerson;

import java.time.LocalDate;

public class RecruitmentAdvertisingDTOImpl implements RecruitmentAdvertisingDTO {

    private boolean         temporaryEmployment;
    private String          typeOfEmployment;
    private short           workingHours;
    private String          requirements;
    private Address         address;
    private LocalDate       startOfWork;
    private LocalDate       endOfWork;
    private ContactPerson   contactPerson;
    private String          jobDescription;
    private String          jobTitle;
    private int             id;

    @Override
    public boolean getTemporaryEmployment() { return temporaryEmployment; }

    @Override
    public String getTypeOfEmployment() { return typeOfEmployment; }

    @Override
    public short getWorkingHours() { return workingHours; }

    @Override
    public String getRequirements() { return requirements; }

    @Override
    public Address getAddress() { return address; }

    @Override
    public LocalDate getStartOfWork() { return startOfWork; }

    @Override
    public LocalDate getEndOfWork() { return endOfWork; }

    @Override
    public ContactPerson getContactPerson() { return contactPerson; }

    @Override
    public String getJobDescription() { return jobDescription; }

    @Override
    public String getJobTitle() { return jobTitle; }

    @Override
    public int getId() { return id; }


    public void setTemporaryEmployment(boolean temporaryEmployment) {
        this.temporaryEmployment = temporaryEmployment;
    }

    public void setTypeOfEmployment(String type) { this.typeOfEmployment = type; }

    public void setWorkingHours(short hours) { this.workingHours = hours; }

    public void setRequirements(String req) { this.requirements = req; }

    public void setAddress(Address address) { this.address = address; }

    public void setStartOfWork(LocalDate date) { this.startOfWork = date; }

    public void setEndOfWork(LocalDate date) { this.endOfWork = date; }

    public void setContactPerson(ContactPerson contactPerson) { this.contactPerson = contactPerson; }

    public void setJobDescription(String description) { this.jobDescription = description; }

    public void setJobTitle(String title) { this.jobTitle = title; }

    public void setId(int id) { this.id = id; }
}
