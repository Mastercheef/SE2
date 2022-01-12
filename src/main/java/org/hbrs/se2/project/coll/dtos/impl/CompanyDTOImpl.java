package org.hbrs.se2.project.coll.dtos.impl;

import org.hbrs.se2.project.coll.dtos.CompanyDTO;
import org.hbrs.se2.project.coll.dtos.JobAdvertisementDTO;
import org.hbrs.se2.project.coll.entities.Address;

import java.util.Set;

public class CompanyDTOImpl implements CompanyDTO {

    private int id;
    private String  companyName;
    private Address address;
    private String  phoneNumber;
    private String  faxNumber;
    private String  email;
    private String  website;
    private String  description;

    private Set<JobAdvertisementDTO>  offers;

    public int      getId() { return this.id; }
    public void     setId(int id) { this.id = id; }

    public String   getCompanyName()                { return this.companyName; }
    public void     setCompanyName(String name)     { this.companyName = name; }
    public Address  getAddress()                    { return this.address; }
    public void     setAddress(Address address)     { this.address = address; }
    public String   getPhoneNumber()                { return this.phoneNumber; }
    public void     setPhoneNumber(String phone)       { this.phoneNumber = phone; }
    public String   getFaxNumber()                  { return this.faxNumber; }
    public void     setFaxNumber(String faxNumber)     { this.faxNumber = faxNumber; }
    public String   getEmail()                      { return this.email; }
    public void     setEmail(String email)          { this.email = email; }
    public String   getWebsite()                    { return this.website; }
    public void     setWebsite(String website)      { this.website = website; }
    public String   getDescription()                { return this.description; }
    public void     setDescription(String description)    {this.description = description; }

    public Set<JobAdvertisementDTO>    getAdvertisements() { return this.offers; }
    public void     setAdvertisements(Set<JobAdvertisementDTO> offers) { this.offers = offers; }

}
