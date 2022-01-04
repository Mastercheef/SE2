package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AddressControlTest {

    @InjectMocks
    AddressControl addressControl = new AddressControl();

    @Mock
    AddressRepository addressRepository;

    @Mock
    Address address;

    @Mock
    Address newAddress;

    private String street = "Mustermannstraße";
    private String houseNumber = "23";
    private String postalCode = "12345";
    private String city = "Musterstadt";
    private String country = "Germany";
    @Test
    void checkAddressExistence() {
        mocking();
        when(address.getId()).thenReturn(100);

        assertEquals(address , addressControl.checkAddressExistence(address));
        assertNotEquals(newAddress , addressControl.checkAddressExistence(address));

        when(address.getId()).thenReturn(0);
        assertEquals(newAddress , addressControl.checkAddressExistence(address));
        assertNotEquals(address , addressControl.checkAddressExistence(address));
    }

    private void mocking() {
        when(address.getStreet()).thenReturn(street);
        when(address.getHouseNumber()).thenReturn(houseNumber);
        when(address.getPostalCode()).thenReturn(postalCode);
        when(address.getCity()).thenReturn(city);
        when(address.getCountry()).thenReturn(country);
        when(addressRepository.getAddressByStreetAndHouseNumberAndPostalCodeAndCityAndCountry(
                address.getStreet(),
                address.getHouseNumber(),
                address.getPostalCode(),
                address.getCity(),
                address.getCountry())).thenReturn(address);
        when(addressRepository.save(address)).thenReturn(newAddress);
    }
    @Test
    void getExistingAddressesIDBiggerZero() {

        MockitoAnnotations.openMocks(this);
        when(addressRepository.getByIdAfter(0)).thenReturn( new ArrayList<Address>());
        assertNotNull(addressControl.getExistingAddresses());
        assertEquals(new ArrayList<Address>() , addressControl.getExistingAddresses());

    }

}