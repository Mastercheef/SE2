package org.hbrs.se2.project.coll.test;

import org.checkerframework.checker.units.qual.A;
import org.hbrs.se2.project.coll.dtos.StudentUserDTO;
import org.hbrs.se2.project.coll.entities.ContactPerson;
import org.hbrs.se2.project.coll.entities.StudentUser;
import org.hbrs.se2.project.coll.repository.ContactPersonRepository;
import org.hbrs.se2.project.coll.repository.StudentUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class StudentProfileViewTest {
    @Autowired
    private StudentUserRepository studentUserRepository;

    @Test
    void getStudentUserById() {
        StudentUserDTO studentUser = studentUserRepository.findStudentUserById(20000000);

        assertNotNull(studentUser);
        assertEquals(studentUser.getFirstName(), "Frederick");
        assertEquals(studentUser.getLastName(), "Behringer");
        assertEquals(studentUser.getPhone(), "192345");
        assertEquals(studentUser.getEmail(), "eeemail@hbrs.de");

    }
}
