package org.hbrs.se2.project.coll.control.factories;

import org.hbrs.se2.project.coll.dtos.CompanyDTO;
import org.hbrs.se2.project.coll.dtos.impl.CompanyDTOImpl;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.Company;

public class CompanyFactory {


    private CompanyFactory() {
        throw new IllegalStateException("Factory Class");
    }

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

    public static CompanyDTOImpl createTestCompany() {
        CompanyDTOImpl companyDTO = new CompanyDTOImpl();
        companyDTO.setCompanyName("Firma");
        Address address = new Address();
        address.setStreet("Straße");
        address.setHouseNumber("10");
        address.setPostalCode("56789");
        address.setCity("Bonn");
        address.setCountry("Deutschland");
        companyDTO.setAddress(address);
        companyDTO.setPhoneNumber("12345");
        companyDTO.setFaxNumber("54321");
        companyDTO.setEmail("valid@email.de");
        companyDTO.setWebsite("www.website.com");
        companyDTO.setDescription("Beschreibung");
        return companyDTO;
    }
}
