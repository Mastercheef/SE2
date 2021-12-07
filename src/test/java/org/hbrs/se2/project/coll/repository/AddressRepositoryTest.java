package org.hbrs.se2.project.coll.repository;

import org.hbrs.se2.project.coll.entities.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@AutoConfigureTestEntityManager
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AddressRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AddressRepository addressRepository;

    private Address address;

    @BeforeEach
    void setup() {
        address = new Address();
        entityManager.flush();
        entityManager.clear();
        addressRepository.flush();

    }

    @Test
    void getByIdTestEntityManager() {

        Optional<Address> address2;
        address.setCountry("DE");
        address.setHouseNumber("2");
        address.setCity("WA");
        address.setStreet("Landgraben");
        address.setPostalCode("12345");

        entityManager.persist(address);
        address2 = addressRepository.findById(address.getId());

        assertEquals("Landgraben", address2.get().getStreet());
    }

    @Test
    void getById() {
        address = this.addressRepository.getById(10000001);
        assertThat(address.getStreet()).isEqualTo("Landgraben");


    }

    @Test
    void getAddressByStreetAndHouseNumberAndPostalCodeAndCityAndCountry() {
        address = this.addressRepository.getAddressByStreetAndHouseNumberAndPostalCodeAndCityAndCountry(
                "Landgraben",
                "12",
                "53773",
                "Hennef",
                "Deutschland"
        );

        assertThat(address.getId()).isEqualTo(10000001);
    }

    @Test
    void getAllByPostalCode() {
    }

    @Test
    void getByIdAfter() {
    }
}