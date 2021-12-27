package org.hbrs.se2.project.coll.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ContactPersonTest {
    private ContactPerson contactPerson = new ContactPerson();
    int id;
    String role;
    Company company = new Company();
    JobAdvertisement advertisement = new JobAdvertisement();
    Set<JobAdvertisement> advertisements = new HashSet<>();

    @BeforeEach
    void setup(){
        id = 1;
        role = "CEO";
        company.setId(5);

        advertisement.setContactPerson(contactPerson);
        advertisement.setId(10);
        advertisements.add(advertisement);
    }

    @Test
    void testGetRole(){
        contactPerson.setRole(role);
        assertEquals("CEO", contactPerson.getRole());
    }

    @Test
    void testGetCompany(){
        contactPerson.setCompany(company);
        assertEquals(company, contactPerson.getCompany());
    }

    @Test
    void testGetAdvertisements(){
        contactPerson.setAdvertisements(advertisements);
        assertEquals(advertisements, contactPerson.getAdvertisements());
        assertEquals(advertisement, contactPerson.getAdvertisements().toArray()[0]);
    }
}
