package org.hbrs.se2.project.coll.test;

import org.checkerframework.checker.units.qual.A;
import org.hbrs.se2.project.coll.dtos.CompanyProfileDTO;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.CompanyProfile;
import org.hbrs.se2.project.coll.repository.AddressRepository;
import org.hbrs.se2.project.coll.repository.CompanyProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CompanyProfileEditViewTest {

    @Autowired
    private CompanyProfileRepository companyProfileRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    void createAndCheckExistingAddress() {
        Address address = new Address();
        address.setStreet("Landgraben");
        address.setHouseNumber("12");
        address.setPostalCode("53773");
        address.setCity("Hennef");
        address.setCountry("Deutschland");

        List<Address> existingAddresses = addressRepository.getByIdAfter(0);
        int ID = checkAddressExistence(address, existingAddresses);

        assertEquals(ID, 10000001);
    }

    @Test
    void useExistingAddressForCompany() {
        CompanyProfile  profile = new CompanyProfile();
        Address         address = addressRepository.getById(10000000);

        profile.setAddress(address);
        assertEquals(profile.getAddress().getId(), address.getId());
    }

    @Test
    void editCompanyProfileParameters()
    {
        CompanyProfile      editedProfile   = new CompanyProfile();

        String  newDescription = "Editierte Firma";
        String  newCompanyName = "TEST GmbH und Co. KG";
        String  newWebsite     = "testfirma.de";
        String  newEmail       = "testfirma@test.com";
        int     newPhoneNumber = 123456789;
        int     newFaxNumber   = 987654321;
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
        int ID = -1;

        for(Address b : addresses) {
            if(Objects.equals(a.getStreet(), b.getStreet()) &&
                    Objects.equals(a.getHouseNumber(), b.getHouseNumber()) &&
                    Objects.equals(a.getPostalCode(), b.getPostalCode()) &&
                    Objects.equals(a.getCity(), b.getCity()) &&
                    Objects.equals(a.getCountry(), b.getCountry())) {
                ID = b.getId();
                break;
            }
        }
        return ID;
    }
}
