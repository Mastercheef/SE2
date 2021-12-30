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
    void findJobAdvertisementById() {
        assertNotNull(        jobAdvertisementRepository.findJobAdvertisementById(30000000));
    }

    @Test
    void findJobAdvertisementByIde() {
        assertEquals(     10,   jobAdvertisementRepository.findJobAdvertisementById(30000000).getSalary());
    }

    @Test
    void filterJobs() {
        assertNotNull(        jobAdvertisementRepository.filterJobs("","","" ,true , LocalDate.of(2000,1,2) ,(short)40 ,2999));
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