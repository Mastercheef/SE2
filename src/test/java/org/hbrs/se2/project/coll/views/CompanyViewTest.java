package org.hbrs.se2.project.coll.views;

import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.Company;
import org.hbrs.se2.project.coll.repository.AddressRepository;
import org.hbrs.se2.project.coll.repository.CompanyRepository;
import org.hbrs.se2.project.coll.repository.ContactPersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



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


}
