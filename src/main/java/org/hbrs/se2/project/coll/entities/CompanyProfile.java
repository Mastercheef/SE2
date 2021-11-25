package org.hbrs.se2.project.coll.entities;

import org.hbrs.se2.project.coll.dtos.StellenausschreibungDTO;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table( name= "col_tab_company", schema = "collhbrs")
public class CompanyProfile {
    private int ID;
    private String companyName;
    private int address;
    private int faxNumber;
    private String website;
    private String description;
    private List<StellenausschreibungDTO> offers;

    @Id
    @GeneratedValue(
            generator = "company_id"
    )
    @SequenceGenerator(
            name = "company_id",
            sequenceName = "collhbrs.col_seq_company_id"
    )
    @Column(name = "company_id")
    public int getID() {
        return ID;
    }

    public void setID(int id) {
        this.ID = id;
    }

    @Basic
    @Column(name = "company_name")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String name) {
        this.companyName = name;
    }

    @Basic
    @Column(name = "address_id")
    public int getAddress() {
        return address;
    }

    public void setAddress(int addr) {
        this.address = addr;
    }

    @Basic
    @Column(name = "fax_number")
    public int getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(int faxNumber) {
        this.faxNumber = faxNumber;
    }

    @Basic
    @Column(name = "homepage")
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Basic
    @Column(name = "company_description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String descr) {
        this.description = descr;
    }

    // TODO: Jobangebote der passenden Firma rauspicken und anhängen (wenn Stellenausschreibung funktioniert? anpassen)
/*
    @ElementCollection
    @Column(name = "Offers")
    public List<StellenausschreibungDTO> getOffers() {
        return offers;
    }

    public void setOffers(List<StellenausschreibungDTO> dto) {
        this.offers = dto;
    }*/
/*
    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        // TODO: Offers anhängen
        CompanyProfile company = (CompanyProfile) o;
        return ID == company.ID &&
                Objects.equals(companyName, company.companyName) &&
                Objects.equals(address, company.address) &&
                Objects.equals(telephone, company.telephone) &&
                Objects.equals(email, company.email) &&
                Objects.equals(website, company.website) &&
                Objects.equals(description, company.description); //&&
                //Objects.equals(offers, company.offers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, companyName, address, telephone, email, website, description);//, offers);
    }*/
}
