package org.hbrs.se2.project.coll.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table( name= "col_tab_company", schema = "collhbrs")
public class CompanyProfile {
    private int id;
    private String companyName;
    private Address address;
    private int phoneNumber;
    private int faxNumber;
    private String email;
    private String website;
    private String description;
    private Set<ContactPerson> contactPersons;
    private Set<JobAdvertisement> advertisements;
    //Wird in der Entity nicht benötigt
    //Sollten wird besser über den Controler beim laden dem DTO hinzufügen
    //private List<StellenausschreibungDTO> offers;


    @Basic
    @Column(name = "company_description")
    public String getDescription() {
        return description;
    }
    public void setDescription(String descr) {
        this.description = descr;
    }

    @Id
    @GeneratedValue(
            generator = "company_id"
    )
    @SequenceGenerator(
            name = "company_id",
            sequenceName = "collhbrs.col_seq_company_id"
    )
    @Column(name = "company_id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "company_name")
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String name) {
        this.companyName = name;
    }

    @ManyToOne
    @JoinColumn(name = "address_id")
    public Address getAddress() { return address; }
    public void setAddress(Address addr) { this.address = addr; }

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
    @Column(name = "mail_address")
    public String getEmail() { return email; }
    public void setEmail(String mail) { this.email = mail; }

    @Basic
    @Column(name = "phone_number")
    public int getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(int phone) { this.phoneNumber = phone; }

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
    public Set<ContactPerson> getContactPersons() {
        return contactPersons;
    }
    public void setContactPersons(Set<ContactPerson> contactPersons) {
        this.contactPersons = contactPersons;
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
