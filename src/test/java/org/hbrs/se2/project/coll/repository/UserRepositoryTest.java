package org.hbrs.se2.project.coll.repository;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
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
        assertEquals(20000000,userRepository.findUserByEmail("hans@hbrs.de").getId());
    }

    @Test
    void findUserByIdNotNull() {
        assertNotNull(userRepository.findUserById(20000000));
    }
    @Test
    void findUserByIdNull() {
        assertNull(userRepository.findUserById(204445000));

    }
    @Test
    void findUserById() {
        assertEquals("Hans",userRepository.findUserById(20000000).getFirstName());
        assertEquals("Meier",userRepository.findUserById(20000000).getLastName());
        assertEquals("st",userRepository.findUserById(20000000).getType());
        assertEquals("hans@hbrs.de",userRepository.findUserById(20000000).getEmail());

    }
}