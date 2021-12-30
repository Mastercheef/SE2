package org.hbrs.se2.project.coll.repository;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureEmbeddedDatabase(provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY )
@Sql( {"/schema.sql" , "/address.sql"})
class AddressRepositoryTest {

    @Autowired
    AddressRepository addressRepository ;

    @Test
    @Sql( {"/schema.sql" , "/address.sql"})
    void getById() {
        assertNotNull(addressRepository.getById(10000000).toString());
        assertEquals("Thielenstrasse " , addressRepository.getById(10000000).getStreet());

    }
}
