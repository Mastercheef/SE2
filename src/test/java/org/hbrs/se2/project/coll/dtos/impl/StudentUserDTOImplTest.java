package org.hbrs.se2.project.coll.dtos.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentUserDTOImplTest {

    StudentUserDTOImpl studentUserDTO = new StudentUserDTOImpl();
    private String graduation = "1.1.2077";
    private String skills = "Diagramme!";
    private String interests = "Viele!";
    private String website = "max.de";
    private String description ="Lorem ipsum";

    @Test
    void getGraduation() {
        studentUserDTO.setGraduation(graduation);
        assertEquals(graduation , studentUserDTO.getGraduation());
    }

    @Test
    void getSkills() {
        studentUserDTO.setSkills(skills);
        assertEquals(skills , studentUserDTO.getSkills());
    }

    @Test
    void getInterests() {
        studentUserDTO.setInterests(interests);
        assertEquals(interests , studentUserDTO.getInterests());
    }

    @Test
    void getWebsite() {
        studentUserDTO.setWebsite(website);
        assertEquals(website , studentUserDTO.getWebsite());
    }

    @Test
    void getDescription() {
        studentUserDTO.setDescription(description);
        assertEquals(description , studentUserDTO.getDescription());
    }



}