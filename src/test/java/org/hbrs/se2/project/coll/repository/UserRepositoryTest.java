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
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    private static final String EMAIL = "hans@hbrs.de";

    @Test
    void findUserByEmailNotNull() {
        assertNotNull(userRepository.findUserByEmail(EMAIL));
    }
    @Test
    void findUserByEmailNull() {
        assertNull(userRepository.findUserByEmail("Hans@hans.de"));
    }
    @Test
    void findUserByEmail() {
        assertEquals(20000000,userRepository.findUserByEmail(EMAIL).getId());
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
        assertEquals(EMAIL,userRepository.findUserById(20000000).getEmail());

    }
}