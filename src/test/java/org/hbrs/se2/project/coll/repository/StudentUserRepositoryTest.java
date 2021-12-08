package org.hbrs.se2.project.coll.repository;

import org.hbrs.se2.project.coll.dtos.StudentUserDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@AutoConfigureTestEntityManager
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudentUserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StudentUserRepository studentUserRepository;


    @Test
    void findStudentUserById() {
        StudentUserDTO studentUserDTO;
        studentUserDTO = studentUserRepository.findStudentUserById(20000017);
        assertEquals("muster@muster.de" ,studentUserDTO.getEmail());
    }
}