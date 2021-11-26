package org.hbrs.se2.project.coll.dtos.impl;

import org.hbrs.se2.project.coll.dtos.CompanyProfileDTO;
import org.hbrs.se2.project.coll.dtos.StellenausschreibungDTO;
import org.hbrs.se2.project.coll.entities.Address;

import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

public class CompanyProfileDTOImpl implements CompanyProfileDTO {

    private int ID;
    private String companyName;
    private Address address;
    private int phoneNumber;
    private int faxNumber;
    private String email;
    private String website;
    private String description;

    // TODO: Annotation klären
//    @OneToMany
    private List<StellenausschreibungDTO>  offers;

    public int      getID() { return this.ID; }
    public void     setID(int id) { this.ID = id; }

    public String   getCompanyName()                { return this.companyName; }
    public void     setCompanyName(String name)     { this.companyName = name; }
    public Address  getAddress()                    { return this.address; }
    public void     setAddress(Address address)     { this.address = address; }
    public int      getPhoneNumber()                { return this.phoneNumber; }
    public void     setPhoneNumber(int phone)       { this.phoneNumber = phone; }
    public int      getFaxNumber()                  { return this.faxNumber; }
    public void     setFaxNumber(int faxNumber)     { this.faxNumber = faxNumber; }
    public String   getEmail()                      { return this.email; }
    public void     setEmail(String email)          { this.email = email; }
    public String   getWebsite()                    { return this.website; }
    public void     setWebsite(String website)      { this.website = website; }
    public String   getDescription()                { return this.description; }
    public void     setDescription(String descr)    {this.description = descr; }

    public List<StellenausschreibungDTO>    getOffers() { return this.offers; }
    public void     setOffers(List<StellenausschreibungDTO> offers) { this.offers = offers; }

}
