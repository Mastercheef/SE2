package org.hbrs.se2.project.coll.repository;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureEmbeddedDatabase(provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY )
@Sql( {"/schema.sql" , "/data.sql"})
class StudentUserRepositoryTest {

    @Autowired
    StudentUserRepository studentUserRepository;
    @Test
    void findStudentUserByIdNotNull() {
        assertNotNull(studentUserRepository.findStudentUserById(20000000));
    }

    @Test
    void findStudentUserByIDNull() {
        assertNull(studentUserRepository.findStudentUserById(20000123));
    }

    @Test
    void findStudentUserBy() {
        assertEquals("student_description" , studentUserRepository.findStudentUserById(20000000).getDescription());
        assertEquals("2000-1-2" , studentUserRepository.findStudentUserById(20000000).getGraduation());
        assertEquals("Viele Interessen" , studentUserRepository.findStudentUserById(20000000).getInterests());
        assertEquals("Ganz viele Skillz" , studentUserRepository.findStudentUserById(20000000).getSkills());
        assertEquals("website.de" , studentUserRepository.findStudentUserById(20000000).getWebsite());

    }
}