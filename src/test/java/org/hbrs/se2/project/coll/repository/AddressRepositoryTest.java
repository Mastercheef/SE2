package org.hbrs.se2.project.coll.repository;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureEmbeddedDatabase(provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY )
class AddressRepositoryTest {

    @Autowired
    AddressRepository addressRepository ;

    @Test
    @Sql( {"/schema.sql" , "/address.sql"})
    void getById() {
        System.out.println(addressRepository.getById(10000000));
    }
}
