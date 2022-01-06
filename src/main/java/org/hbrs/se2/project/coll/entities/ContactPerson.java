package org.hbrs.se2.project.coll.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@DiscriminatorValue("cp")
@Table( name ="col_tab_contact_person" , schema = "collhbrs" )
public class ContactPerson extends User {

    private String role;
    private Company company;
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
    public Company getCompany() {
        return this.company;
    }
    public void setCompany(Company company) {
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
