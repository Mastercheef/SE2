package org.hbrs.se2.project.coll.dtos.impl;

import org.hbrs.se2.project.coll.dtos.CompanyProfileDTO;

import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

public class CompanyProfileDTOImpl implements CompanyProfileDTO {

    private String    ID;
    private String companyName;
    private String address;
    private String telephone;
    private String email;
    private String website;
    private String description;

    // TODO: Annotation klären
//    @OneToMany
    private List<StellenausschreibungDTO>  offers;

    public String      getID() { return this.ID; }
    public void     setID(String id) { this.ID = id; }

    public String   getCompanyName() { return this.companyName; }
    public void     setCompanyName(String name) { this.companyName = name; }
    public String   getAddress() { return this.address; }
    public void     setAddress(String address) { this.address = address; }
    public String   getTelephone() { return this.telephone; }
    public void     setTelephone(String phone) { this.telephone = phone; }
    public String   getEmail() { return this.email; }
    public void     setEmail(String email) { this.email = email; }
    public String   getWebsite() { return this.website; }
    public void     setWebsite(String website) { this.website = website; }
    public String   getDescription() { return this.description; }
    public void     setDescription(String descr) {this.description = descr; }

    public List<StellenausschreibungDTO>    getOffers() { return this.offers; }
    public void     setOffers(List<StellenausschreibungDTO> offers) { this.offers = offers; }

}
