package org.hbrs.se2.project.coll.repository;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@DataJpaTest

@AutoConfigureEmbeddedDatabase(provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY )
@Sql( {"/schema.sql" , "/data.sql"})
class ContactPersonRepositoryTest {

    @Autowired
    ContactPersonRepository contactPersonRepository;

    @Test
    void findContactPersonByCompanyIdNotNull() {
            assertNotNull(        contactPersonRepository.findContactPersonByCompanyId(40000000));
    }

    @Test
    void findContactPersonByIdNotNull() {
        assertNotNull(contactPersonRepository.findContactPersonById(19999978));
    }
    @Test
    void findContactPersonByCompanyIdNull() {
        assertNull(        contactPersonRepository.findContactPersonByCompanyId(40560000));
    }
    @Test
    void findContactPersonByIdNull() {
        assertNull(        contactPersonRepository.findContactPersonById(19993453));
    }

    @Test
    void findContactPersonByCompanyId() {
        assertEquals(     "admin" ,    contactPersonRepository.findContactPersonByCompanyId(40000000).getRole());
    }

    @Test
    void findContactPersonById() {
        assertEquals("admin",contactPersonRepository.findContactPersonById(19999978).getRole());
    }
}