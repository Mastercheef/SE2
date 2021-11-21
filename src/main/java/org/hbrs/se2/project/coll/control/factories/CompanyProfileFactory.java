package org.hbrs.se2.project.coll.control.factories;

import org.hbrs.se2.project.coll.dtos.CompanyProfileDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.CompanyProfile;

public class CompanyProfileFactory {
    public static CompanyProfile createCompanyProfile(CompanyProfileDTO companyProfileDTO, UserDTO userDTO) {

        // Erzeuge eine CompanyProfileEntity; ID wird intern hochgezählt (@GeneratedValue auf ID)
        CompanyProfile companyProfile = new CompanyProfile();

        // Übernehme Grundparameter aus dem DTO, die in der UI eingegeben wurden:
        companyProfile.setCompanyName( companyProfileDTO.getCompanyName() );
        companyProfile.setAddress( companyProfileDTO.getAddress() );
        companyProfile.setEmail( companyProfileDTO.getEmail() );
        companyProfile.setTelephone( companyProfileDTO.getTelephone() );
        companyProfile.setDescription( companyProfileDTO.getDescription() );
        companyProfile.setWebsite( companyProfileDTO.getWebsite());

        // TODO: Anpassen
        //companyProfile.setOffers( companyProfileDTO.getOffers() );

        // TODO: Ändern
        companyProfile.setID( companyProfile.getID() );

        return companyProfile;
    }
}
