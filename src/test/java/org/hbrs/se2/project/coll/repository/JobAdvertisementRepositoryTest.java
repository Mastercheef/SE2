package org.hbrs.se2.project.coll.repository;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureEmbeddedDatabase(provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY )
@Sql( {"/schema.sql" , "/data.sql"})
class JobAdvertisementRepositoryTest {

    @Test
    void findJobAdvertisementById() {
    }

    @Test
    void filterJobs() {
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