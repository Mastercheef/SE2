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
class JobAdvertisementRepositoryTest {

    @Autowired
    JobAdvertisementRepository jobAdvertisementRepository;

    @Test
    void findJobAdvertisementByIdNotNull() {
        assertNotNull(        jobAdvertisementRepository.findJobAdvertisementById(30000000));
    }
    @Test
    void findJobAdvertisementByIdNull() {
        assertNull(        jobAdvertisementRepository.findJobAdvertisementById(30002220));
    }

    @Test
    void findJobAdvertisementByIde() {
        assertEquals("Persoenliche Assistentin",   jobAdvertisementRepository.findJobAdvertisementById(30000000).getJobTitle());
        assertEquals("Vollzeit",   jobAdvertisementRepository.findJobAdvertisementById(30000000).getTypeOfEmployment());
        assertEquals("Alles",   jobAdvertisementRepository.findJobAdvertisementById(30000000).getRequirements());
        assertEquals(false,   jobAdvertisementRepository.findJobAdvertisementById(30000000).getTemporaryEmployment());
        assertEquals(LocalDate.of(2021,12,1),   jobAdvertisementRepository.findJobAdvertisementById(30000000).getStartOfWork());
        assertEquals(LocalDate.of(2024,1,14),   jobAdvertisementRepository.findJobAdvertisementById(30000000).getEndOfWork());
        assertEquals((short)60,   jobAdvertisementRepository.findJobAdvertisementById(30000000).getWorkingHours());
        assertEquals(10,   jobAdvertisementRepository.findJobAdvertisementById(30000000).getSalary());

    }

    @Test
    void filterJobsemptyList() {
        assertTrue(
                jobAdvertisementRepository.filterJobs("","","" ,true , LocalDate.of(2000,1,2) ,(short)40 ,2999).size() == 0);
    }

    @Test
    void filterJobsNotemptyList() {
        assertTrue(jobAdvertisementRepository.filterJobs(
                "Persoenliche Assistentin","Vollzeit","Alles" ,false , LocalDate.of(2021,12,1) ,(short)60 ,10).size() == 1);
    }

    @Test
    void testFilterJobs() {
    }

    @Test
    void testFilterJobs1() {
    }

    @Test
    void testFilterJobs2() {
    }

    @Test
    void findJobAdvertisementsByCompanyId() {
    }
}