package org.hbrs.se2.project.coll.repository;

import org.hbrs.se2.project.coll.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AddressRepository extends JpaRepository<Address, Integer> {

    List<Address> getAllByPostalCode(String postalCode);
    List<Address>   getByIdAfter(int id);
    Address         getById(int id);
    Address         getAddressByStreetAndHouseNumberAndPostalCodeAndCityAndCountry
            (String street, String houseNumber, String postalCode, String city, String country );
}
