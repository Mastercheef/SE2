package org.hbrs.se2.project.coll.views;

import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.Company;
import org.hbrs.se2.project.coll.entities.ContactPerson;
import org.hbrs.se2.project.coll.repository.AddressRepository;
import org.hbrs.se2.project.coll.repository.CompanyRepository;
import org.hbrs.se2.project.coll.repository.ContactPersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CompanyViewTest {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ContactPersonRepository contactPersonRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @Test
    void getContactPersonByUserId() {
        ContactPerson contactPerson;
        contactPerson = contactPersonRepository.findContactPersonById(20000012);

        assertEquals(contactPerson.getFirstName(), "Judith");
        assertEquals(contactPerson.getLastName(), "Meier");
        assertEquals(contactPerson.getPhone(), "1122334455");
        assertEquals(contactPerson.getEmail(), "judithmeier@company.com");
    }

    @Test
    void getContactPersonByCompanyId() {
        ContactPerson contactPerson;
        contactPerson = contactPersonRepository.findContactPersonByCompanyId(40000000);

        assertEquals(contactPerson.getFirstName(), "Judith");
        assertEquals(contactPerson.getLastName(), "Meier");
        assertEquals(contactPerson.getPhone(), "1122334455");
        assertEquals(contactPerson.getEmail(), "judithmeier@company.com");
    }

    @Test
    void createReadAndDeleteACompanyProfile(){

        Address address = new Address();
        address.setId(10000000);

        Company companyProfile = new Company();
        companyProfile.setCompanyName("Fancy Testing Company");
        companyProfile.setDescription("We are a fancy Company!");
        companyProfile.setWebsite("www.a-fancy-company.com");
        companyProfile.setFaxNumber("1093049");
        companyProfile.setAddress(address);
        companyRepository.save(companyProfile);

        int tmpId = companyProfile.getId();

        Optional<Company> wrapper = companyRepository.findById(tmpId);
        assertTrue(wrapper.isPresent());
        Company companyProfileAfterCreate = wrapper.get();
        assertEquals(companyProfile.getCompanyName(), companyProfileAfterCreate.getCompanyName());
        assertEquals(companyProfile.getEmail(), companyProfileAfterCreate.getEmail());
        assertEquals(companyProfile.getDescription(), companyProfileAfterCreate.getDescription());

        assertNotSame(companyProfile, companyProfileAfterCreate);

        companyRepository.deleteById(tmpId);

        wrapper = companyRepository.findById(tmpId);
        assertFalse(wrapper.isPresent());
    }


    @Test
    void createAndCheckExistingAddress() {
        Address address = new Address();
        address.setStreet("Landgraben");
        address.setHouseNumber("12");
        address.setPostalCode("53773");
        address.setCity("Hennef");
        address.setCountry("Deutschland");

        List<Address> existingAddresses = addressRepository.getByIdAfter(0);
        int id = checkAddressExistence(address, existingAddresses);

        assertEquals(id, 10000001);
    }

    @Test
    void useExistingAddressForCompany() {
        Company profile = new Company();
        Address         address = addressRepository.getById(10000000);

        profile.setAddress(address);
        assertEquals(profile.getAddress().getId(), address.getId());
    }

    @Test
    void editCompanyProfileParameters()
    {
        Company editedProfile   = new Company();

        String  newDescription = "Editierte Firma";
        String  newCompanyName = "TEST GmbH und Co. KG";
        String  newWebsite     = "testfirma.de";
        String  newEmail       = "testfirma@test.com";
        String  newPhoneNumber = "123456789";
        String  newFaxNumber   = "987654321";
        int     newId          = 123;
        Address newAddress     = new Address();
        newAddress.setId(10000002);

        editedProfile.setDescription(newDescription);
        editedProfile.setCompanyName(newCompanyName);
        editedProfile.setWebsite(newWebsite);
        editedProfile.setEmail(newEmail);
        editedProfile.setPhoneNumber(newPhoneNumber);
        editedProfile.setFaxNumber(newFaxNumber);
        editedProfile.setId(newId);
        editedProfile.setAddress(newAddress);

        assertEquals(editedProfile.getDescription(), newDescription);
        assertEquals(editedProfile.getCompanyName(), newCompanyName);
        assertEquals(editedProfile.getWebsite(), newWebsite);
        assertEquals(editedProfile.getEmail(), newEmail);
        assertEquals(editedProfile.getPhoneNumber(), newPhoneNumber);
        assertEquals(editedProfile.getFaxNumber(), newFaxNumber);
        assertEquals(editedProfile.getId(), newId);
        assertEquals(editedProfile.getAddress(), newAddress);
    }

    public int checkAddressExistence(Address a, List<Address> addresses) {
        int id = -1;

        for(Address b : addresses) {
            if(Objects.equals(a.getStreet(), b.getStreet()) &&
                    Objects.equals(a.getHouseNumber(), b.getHouseNumber()) &&
                    Objects.equals(a.getPostalCode(), b.getPostalCode()) &&
                    Objects.equals(a.getCity(), b.getCity()) &&
                    Objects.equals(a.getCountry(), b.getCountry())) {
                id = b.getId();
                break;
            }
        }
        return id;
    }
}
