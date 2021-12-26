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
        studentUser.setEmail("mustermann@gmx.de");
        studentUser.setType("st");
    }
        String graduation = "1.1.2000";
        String skills = "C++";
        String interests = "Bier";
        String website = "www.mustermann.de";
        String description = "Talentierter Programmierer";
        String subjectField = "Datenbank";

    @Test
    void testGetGraduation(){
        studentUser.setGraduation(graduation);
        assertEquals("1.1.2000", studentUser.getGraduation());
    }

    @Test
    void testGetSkills(){
        studentUser.setSkills(skills);
        assertEquals("C++", studentUser.getSkills());
    }

    @Test
    void testGetInterests(){
        studentUser.setInterests(interests);
        assertEquals("Bier", studentUser.getInterests());
    }

    @Test
    void testGetWebsite(){
        studentUser.setWebsite(website);
        assertEquals("www.mustermann.de", studentUser.getWebsite());
    }

    @Test
    void testGetDescription(){
        studentUser.setDescription(description);
        assertEquals("Talentierter Programmierer", studentUser.getDescription());
    }

    @Test
    void testGetSubjectField() {
        studentUser.setSubjectField(subjectField);
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
        studentUser.setGraduation(graduation);
        studentUser.setSkills(skills);
        studentUser.setInterests(interests);
        studentUser.setWebsite(website);
        studentUser.setDescription(description);
        studentUser.setSubjectField(subjectField);
        assertEquals("111, Herr, Dr., Max, Mustermann, 1999-01-01, 0123456789, Musterstrasse 1\n" +
                "12345 Musterstadt\n" +
                "Deutschland, mustermann@gmx.de, 1.1.2000, C++, Bier, www.mustermann.de, Talentierter Programmierer, Datenbank, st" , studentUser.toString());
    }
}