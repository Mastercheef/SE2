package org.hbrs.se2.project.coll.test;

import org.hbrs.se2.project.coll.dtos.CompanyProfileDTO;
import org.hbrs.se2.project.coll.entities.CompanyProfile;
import org.hbrs.se2.project.coll.repository.CompanyProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class CompanyProfileRepositoryTest {

    @Autowired
    private CompanyProfileRepository companyProfileRepository;

    @Test
    void createReadAndDeleteACompanyProfile(){

        CompanyProfile companyProfile = new CompanyProfile();
        companyProfile.setCompanyName("Fancy Testing Company");
        companyProfile.setDescription("We are a fancy Company!");
        companyProfile.setWebsite("www.a-fancy-company.com");
        companyProfile.setFaxNumber(1093049);
        companyProfile.setAddress(10000000);
        companyProfileRepository.save(companyProfile);

        int tmpId = companyProfile.getID();

        //TODO Change this from CompanyProfileDTO to Optional<CompanyProfile>
        CompanyProfileDTO companyProfileAfterCreate = companyProfileRepository.findCompanyProfileByID(tmpId);
        assertEquals(companyProfile.getCompanyName(), companyProfileAfterCreate.getCompanyName());
        assertEquals(companyProfile.getDescription(), companyProfileAfterCreate.getDescription());
        assertEquals(companyProfile.getDescription(), companyProfileAfterCreate.getDescription());

        assertNotSame(companyProfile, companyProfileAfterCreate);

        companyProfileRepository.deleteById(tmpId);

        //TODO also here...
        CompanyProfileDTO wrapper = companyProfileRepository.findCompanyProfileByID(tmpId);
        assertNull(wrapper);
    }

}
