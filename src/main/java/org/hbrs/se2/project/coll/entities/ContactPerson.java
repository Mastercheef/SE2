package org.hbrs.se2.project.coll.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@DiscriminatorValue("cp")
@Table( name ="col_tab_contact_person" , schema = "collhbrs" )
public class ContactPerson extends User {

    //TODO Maybe change String to a custom Datatype or enum
    private String role;
    private CompanyProfile company;
    private Set<JobAdvertisement> advertisements;

    @Basic
    @Column(name = "role")
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    @ManyToOne
    @JoinColumn(name = "company_id")
    public CompanyProfile getCompany() {
        return this.company;
    }
    public void setCompany(CompanyProfile company) {
        this.company = company;
    }

    @OneToMany(mappedBy = "contactPerson")
    public Set<JobAdvertisement> getAdvertisements() {
        return advertisements;
    }
    public void setAdvertisements(Set<JobAdvertisement> advertisements) {
        this.advertisements = advertisements;
    }
}
