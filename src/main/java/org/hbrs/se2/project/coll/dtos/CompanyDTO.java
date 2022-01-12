package org.hbrs.se2.project.coll.dtos;

import org.hbrs.se2.project.coll.entities.Address;

import javax.persistence.OneToMany;
import java.util.Set;

public interface CompanyDTO {

    public int      getId();

    public String   getCompanyName();
    public Address  getAddress();
    public String   getEmail();
    public String   getPhoneNumber();
    public String   getFaxNumber();
    public String   getWebsite();
    public String   getDescription();

    @OneToMany
    public Set<JobAdvertisementDTO> getAdvertisements();

}
