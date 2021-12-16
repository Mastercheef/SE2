package org.hbrs.se2.project.coll.control.factories;

import org.hbrs.se2.project.coll.dtos.CompanyDTO;
import org.hbrs.se2.project.coll.entities.Company;

public class CompanyFactory {



    public static Company createCompany(CompanyDTO companyDTO) {

        // Erzeuge eine CompanyProfileEntity; ID wird intern hochgezählt (@GeneratedValue auf ID)
        Company companyProfile = new Company();

        // Übernehme Grundparameter aus dem DTO, die in der UI eingegeben wurden:
        companyProfile.setId( companyDTO.getId() );
        companyProfile.setCompanyName( companyDTO.getCompanyName() );
        companyProfile.setAddress( companyDTO.getAddress() );
        companyProfile.setDescription( companyDTO.getDescription() );
        companyProfile.setWebsite( companyDTO.getWebsite());
        companyProfile.setEmail( companyDTO.getEmail());
        companyProfile.setPhoneNumber( companyDTO.getPhoneNumber() );
        companyProfile.setFaxNumber( companyDTO.getFaxNumber() );

        return companyProfile;
    }
}
