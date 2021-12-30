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
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void findUserByEmailNotNull() {
        assertNotNull(userRepository.findUserByEmail("hans@hbrs.de"));
    }
    @Test
    void findUserByEmailNull() {
        assertNull(userRepository.findUserByEmail("hans@hbrs.com"));
    }
    @Test
    void findUserByEmail() {
        assertEquals(19999952,userRepository.findUserByEmail("hans@hbrs.de").getId());
    }

    @Test
    void findUserByIdNotNull() {
        assertNotNull(userRepository.findUserById(19999952));
    }
    @Test
    void findUserByIdNull() {
        assertNull(userRepository.findUserById(19939952));

    }
    @Test
    void findUserById() {
        assertEquals("Hans",userRepository.findUserById(19999952).getFirstName());
        assertEquals("Meier",userRepository.findUserById(19999952).getLastName());
        assertEquals("st",userRepository.findUserById(19999952).getType());
        assertEquals("hans@hbrs.de",userRepository.findUserById(19999952).getEmail());

    }
}