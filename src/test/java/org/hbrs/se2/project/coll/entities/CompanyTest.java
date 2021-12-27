package org.hbrs.se2.project.coll.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CompanyTest {

    private Company company = new Company();
    private Address address = new Address();
    private ContactPerson contactPerson = new ContactPerson();
    int id;
    String companyName;
    String phoneNumber;
    String faxNumber;
    String email;
    String website;
    String description;
    Set<ContactPerson> contactPersons = new HashSet<>();

    @BeforeEach
    void setup() {
        address.setId(1);
        address.setCountry("Germany");
        address.setStreet("Harald-Strasse");
        address.setPostalCode("68799");
        address.setCity("Hamburg");
        address.setHouseNumber("19");

        id = 10;
        companyName = "Amazon";
        phoneNumber = "1234";
        faxNumber = "12345";
        email = "amazon@t-online.de";
        website = "www.amazon.de";
        description = "reich";
        contactPerson.setId(10);
        contactPerson.setCompany(company);
        contactPerson.setRole("CEO");
        contactPersons.add(contactPerson);
    }

    @Test
    void testGetDescription(){
        company.setDescription(description);
        assertEquals("reich", company.getDescription());
    }

    @Test
    void testGetId(){
        company.setId(id);
        assertEquals(10, company.getId());
    }

    @Test
    void testGetCompanyName(){
        company.setCompanyName(companyName);
        assertEquals("Amazon", company.getCompanyName());
    }

    @Test
    void testGetFaxNumber(){
        company.setFaxNumber(faxNumber);
        assertEquals("12345", company.getFaxNumber());
    }

    @Test
    void testGetWebsite(){
        company.setWebsite(website);
        assertEquals("www.amazon.de", company.getWebsite());
    }

    @Test
    void testGetEmail(){
        company.setEmail(email);
        assertEquals("amazon@t-online.de", company.getEmail());
    }

    @Test
    void testGetPhoneNumber(){
        company.setPhoneNumber(phoneNumber);
        assertEquals("1234", company.getPhoneNumber());
    }

    @Test
    void testGetContactPersons(){
        company.setContactPersons(contactPersons);
        assertEquals(contactPersons, company.getContactPersons());
    }
}
