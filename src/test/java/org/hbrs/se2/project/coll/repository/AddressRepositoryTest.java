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
class AddressRepositoryTest {

    @Autowired
    AddressRepository addressRepository ;

    @Test
    void getByIdNotNull() {
        assertNotNull(addressRepository.getById(10000000).toString());
    }

    @Test
    void getById() {
        assertEquals("Thielenstrasse " , addressRepository.getById(10000000).getStreet());
    }

    @Test
    void getByIdNull() {
        assertNull(addressRepository.getById(19999999));
    }

    @Test
    void getAddressByStreetAndHouseNumberAndPostalCodeAndCityAndCountry() {

        assertNotNull(addressRepository.getAddressByStreetAndHouseNumberAndPostalCodeAndCityAndCountry(
                "Finkenherd" , "4", "56075" , "Koblenz" , "Deutschland" ));
    }

    @Test
    void getAddressByStreetAndHouseNumberAndPostalCodeAndCityAndCountryID() {
        assertEquals(10000001 , addressRepository.getAddressByStreetAndHouseNumberAndPostalCodeAndCityAndCountry(
                "Finkenherd" , "4", "56075" , "Koblenz" , "Deutschland" ).getId());
    }

    @Test
    void getAddressByStreetAndHouseNumberAndPostalCodeAndCityAndCountryIDNull() {
        assertNull(addressRepository.getAddressByStreetAndHouseNumberAndPostalCodeAndCityAndCountry(
                "Müll" , "Falsch", "Text" , "wda" , "Narnia" ));
    }

    @Test
    void getByIdAfterNotNull() {
        assertNotNull(addressRepository.getByIdAfter(10000002));
    }

    @Test
    void getByIdAfter() {
        assertEquals(1 ,addressRepository.getByIdAfter(10000001).size());
    }

    @Test
    void getByIdAfterNull() {
        assertNotNull(addressRepository.getByIdAfter(19999999));
    }
}
