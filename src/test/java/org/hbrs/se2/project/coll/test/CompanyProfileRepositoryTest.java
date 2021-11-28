package org.hbrs.se2.project.coll.test;

import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.CompanyProfile;
import org.hbrs.se2.project.coll.repository.CompanyProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CompanyProfileRepositoryTest {

    @Autowired
    private CompanyProfileRepository companyProfileRepository;

    @Test
    void createReadAndDeleteACompanyProfile(){

        Address address = new Address();
        address.setId(10000000);

        CompanyProfile companyProfile = new CompanyProfile();
        companyProfile.setCompanyName("Fancy Testing Company");
        companyProfile.setDescription("We are a fancy Company!");
        companyProfile.setWebsite("www.a-fancy-company.com");
        companyProfile.setFaxNumber(1093049);
        companyProfile.setAddress(address);
        companyProfileRepository.save(companyProfile);

        int tmpId = companyProfile.getId();

        Optional<CompanyProfile> wrapper = companyProfileRepository.findById(tmpId);
        assertTrue(wrapper.isPresent());
        CompanyProfile companyProfileAfterCreate = wrapper.get();
        assertEquals(companyProfile.getCompanyName(), companyProfileAfterCreate.getCompanyName());
        assertEquals(companyProfile.getDescription(), companyProfileAfterCreate.getDescription());
        assertEquals(companyProfile.getDescription(), companyProfileAfterCreate.getDescription());

        assertNotSame(companyProfile, companyProfileAfterCreate);

        companyProfileRepository.deleteById(tmpId);

        wrapper = companyProfileRepository.findById(tmpId);
        assertFalse(wrapper.isPresent());
    }

}
