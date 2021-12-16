package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressControl {

    @Autowired
    private AddressRepository addressRepository;

    public AddressControl() {}

    public Address checkAddressExistence(Address address) {
        Address existingAddress = addressRepository.getAddressByStreetAndHouseNumberAndPostalCodeAndCityAndCountry(
                address.getStreet(),
                address.getHouseNumber(),
                address.getPostalCode(),
                address.getCity(),
                address.getCountry());


        if (existingAddress != null && existingAddress.getId() > 0) {
            System.out.println("Using existing Address with ID: " + existingAddress.getId());
            return existingAddress;
        } else {
            System.out.println("Saving new Address in DB...");
            Address newAddress = addressRepository.save(address);
            System.out.println("Created new Address with ID: " + newAddress.getId());
            return newAddress;
        }
    }
}
