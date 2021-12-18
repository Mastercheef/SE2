package org.hbrs.se2.project.coll.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.*;

class StudentUserTest {

    private StudentUser studentUser;


    private Address address = new Address();

    @BeforeEach
    void setUp() {

        studentUser = new StudentUser();
        address.setPostalCode("12345");
        address.setStreet("Musterstrasse");
        address.setCity("Musterstadt");
        address.setCountry("Deutschland");
        address.setHouseNumber("1");

        studentUser.setId(111);
        studentUser.setSalutation("Herr");
        studentUser.setTitle("Dr.");
        studentUser.setFirstName("Max");
        studentUser.setLastName("Mustermann");
        studentUser.setDateOfBirth(LocalDate.of(1999,1,1));
        studentUser.setPhone("0123456789");
        studentUser.setAddress(address);
        studentUser.setGraduation("1.1.2000");
        studentUser.setSkills("C++");
        studentUser.setInterests("Bier");
        studentUser.setWebsite("www.mustermann.de");
        studentUser.setDescription("Talentierter Programmierer");
        studentUser.setSubjectField("Datenbank");
        studentUser.setEmail("mustermann@gmx.de");
        studentUser.setType("st");



    }
    @Test
    void getSubjectField() {
        assertEquals("Datenbank" , studentUser.getSubjectField());
    }



    @Test
    //TODO Implement
    void getApplications() {
        JobAdvertisement jobAdvertisementOne = new JobAdvertisement();
        JobAdvertisement jobAdvertisementTwo = new JobAdvertisement();
        Set<JobAdvertisement> setJob = new HashSet<JobAdvertisement>();
        setJob.add(jobAdvertisementOne);
        setJob.add(jobAdvertisementTwo);
        studentUser.setApplications(setJob);

        assertEquals(2 , studentUser.getApplications().size());
    }




    @Test
    void testToString() {
        assertEquals("111, Herr, Dr., Max, Mustermann, 1999-01-01, 0123456789, Musterstrasse 1\n" +
                "12345 Musterstadt\n" +
                "Deutschland, mustermann@gmx.de, 1.1.2000, C++, Bier, www.mustermann.de, Talentierter Programmierer, Datenbank, st" , studentUser.toString());
    }
}