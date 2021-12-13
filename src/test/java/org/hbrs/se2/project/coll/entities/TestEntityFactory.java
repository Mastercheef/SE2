package org.hbrs.se2.project.coll.entities;

import org.hbrs.se2.project.coll.repository.AddressRepository;
import org.hbrs.se2.project.coll.repository.CompanyProfileRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Optional;

@RunWith(SpringRunner.class)
@AutoConfigureTestEntityManager
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class TestEntityFactory {

    @Autowired
    AddressRepository addressRepository;
    CompanyProfileRepository companyProfileRepository;

    public Address createAddress() {
        Optional<Address> wrapper = addressRepository.findById(10000000);
        if (wrapper.isPresent()) {
            return wrapper.get();
        } else {
            Address address = new Address();
            address.setCountry("DE");
            address.setPostalCode("12345");
            address.setStreet("Musterstrasse");
            address.setCity("Musterstadt");
            address.setCountry("Deutschland");
            address.setHouseNumber("1");
            address.setId(10000000);
            return address;
        }
    }
    public CompanyProfile createCompanyProfile() {
        Optional<CompanyProfile> wrapper = companyProfileRepository.findById(40000000);
        if(wrapper.isPresent()) {
            return wrapper.get();
        } else {
            CompanyProfile companyProfile = new CompanyProfile();
            companyProfile.setCompanyName("Fancy Testing Company");
            companyProfile.setDescription("We are a fancy Company!");
            companyProfile.setWebsite("www.a-fancy-company.com");
            companyProfile.setFaxNumber(1093049);
            companyProfile.setPhoneNumber(1093050);
            companyProfile.setEmail("recruitement@a-fancy-company.com");
            companyProfile.setAddress(this.createAddress());
            return companyProfile;
        }
    }
}
