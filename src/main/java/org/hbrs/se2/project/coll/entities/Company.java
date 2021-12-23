package org.hbrs.se2.project.coll.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table( name= "col_tab_company", schema = "collhbrs")
public class Company {
    private int id;
    private String companyName;
    private Address address;
    private String phoneNumber;
    private String faxNumber;
    private String email;
    private String website;
    private String description;
    private Set<ContactPerson> contactPersons;

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
    public String getFaxNumber() {
        return faxNumber;
    }
    public void setFaxNumber(String faxNumber) {
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
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phone) { this.phoneNumber = phone; }

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
    public Set<ContactPerson> getContactPersons() {
        return contactPersons;
    }
    public void setContactPersons(Set<ContactPerson> contactPersons) {
        this.contactPersons = contactPersons;
    }
}
