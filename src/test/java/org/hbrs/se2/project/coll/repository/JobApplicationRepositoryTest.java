package org.hbrs.se2.project.coll.repository;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureEmbeddedDatabase(provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY )
@Sql( {"/schema.sql" , "/data.sql"})
class JobApplicationRepositoryTest {

    @Autowired
    JobApplicationRepository jobApplicationRepository;

    @Test
    void jobApplicationRepositoryNotNull() {
        assertNotNull(jobApplicationRepository.findJobApplicationById(60000001));
    }
    @Test
    void jobApplicationRepositoryNull() {
        assertNull(jobApplicationRepository.findJobApplicationById(60007891));
    }
    @Test
    void findJobApplicationByIdTest() {
        assertEquals("Bewerbung auf Keller Mitarbeiter",jobApplicationRepository.findJobApplicationById(60000001).getHeadline());
        assertEquals("Bin stabil",jobApplicationRepository.findJobApplicationById(60000001).getText());
        assertEquals(LocalDate.of(2021,12,29),jobApplicationRepository.findJobApplicationById(60000001).getDate());
    }
}