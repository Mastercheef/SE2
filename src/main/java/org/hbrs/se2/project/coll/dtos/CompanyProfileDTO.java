package org.hbrs.se2.project.coll.dtos;

import com.vaadin.flow.component.html.Label;
import org.hbrs.se2.project.coll.dtos.StellenausschreibungDTO;
import org.hbrs.se2.project.coll.entities.Address;

import javax.persistence.OneToMany;
import java.util.List;

public interface CompanyProfileDTO {

    public int      getID();

    public String   getCompanyName();
    public Address  getAddress();
    public String   getEmail();
    public int      getPhoneNumber();
    public int      getFaxNumber();
    public String   getWebsite();
    public String   getDescription();

    @OneToMany
    public List<StellenausschreibungDTO> getOffers();

}
