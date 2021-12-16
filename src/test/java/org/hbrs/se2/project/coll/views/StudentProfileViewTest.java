package org.hbrs.se2.project.coll.views;

import org.hbrs.se2.project.coll.dtos.StudentUserDTO;
import org.hbrs.se2.project.coll.repository.StudentUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class StudentProfileViewTest {
    @Autowired
    private StudentUserRepository studentUserRepository;

    @Test
    void getStudentUserById() {
        StudentUserDTO studentUser = studentUserRepository.findStudentUserById(20000000);

        assertNotNull(studentUser);
        assertEquals(studentUser.getFirstName(), "Frederick");
        assertEquals(studentUser.getLastName(), "Behringer");
        assertEquals(studentUser.getPhone(), "192345");
        assertEquals(studentUser.getEmail(), "email@hbrs.de");

    }
}
