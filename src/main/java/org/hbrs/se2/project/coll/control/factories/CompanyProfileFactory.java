package org.hbrs.se2.project.coll.control.factories;

import org.hbrs.se2.project.coll.dtos.CompanyProfileDTO;
import org.hbrs.se2.project.coll.entities.CompanyProfile;

public class CompanyProfileFactory {



    public static CompanyProfile createCompanyProfile(CompanyProfileDTO companyProfileDTO) {

        // Erzeuge eine CompanyProfileEntity; ID wird intern hochgezählt (@GeneratedValue auf ID)
        CompanyProfile companyProfile = new CompanyProfile();

        // Übernehme Grundparameter aus dem DTO, die in der UI eingegeben wurden:
        companyProfile.setId( companyProfileDTO.getId() );
        companyProfile.setCompanyName( companyProfileDTO.getCompanyName() );
        companyProfile.setAddress( companyProfileDTO.getAddress() );
        companyProfile.setDescription( companyProfileDTO.getDescription() );
        companyProfile.setWebsite( companyProfileDTO.getWebsite());
        companyProfile.setEmail( companyProfileDTO.getEmail());
        companyProfile.setPhoneNumber( companyProfileDTO.getPhoneNumber() );
        companyProfile.setFaxNumber( companyProfileDTO.getFaxNumber() );

        return companyProfile;
    }
}
