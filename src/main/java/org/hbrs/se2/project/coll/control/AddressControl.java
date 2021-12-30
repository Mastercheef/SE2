package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.repository.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddressControl {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressControl.class);

    @Autowired
    private AddressRepository addressRepository;

    public AddressControl() {
        //Required for Vaadin
    }

    public Address checkAddressExistence(Address address) {
        Address existingAddress = addressRepository.getAddressByStreetAndHouseNumberAndPostalCodeAndCityAndCountry(
                address.getStreet(),
                address.getHouseNumber(),
                address.getPostalCode(),
                address.getCity(),
                address.getCountry());


        if (existingAddress != null && existingAddress.getId() > 0) {
            LOGGER.info("Using existing Address with ID: {}" , existingAddress.getId());
            return existingAddress;
        } else {
            LOGGER.info("Saving new Address in DB...");
            Address newAddress = addressRepository.save(address);
            LOGGER.info("Created new Address with ID: {}" , newAddress.getId());
            return newAddress;
        }
    }
    public List<Address> getExistingAddresses() {
        return addressRepository.getByIdAfter(0);
    }
}
