package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AddressControlTest {

    @InjectMocks
    AddressControl addressControl = new AddressControl();

    @Mock
    AddressRepository addressRepository;

    @Test
    void checkAddressExistence() {
    }

    @Test
    void getExistingAddresses() {

        MockitoAnnotations.openMocks(this);
        when(addressRepository.getByIdAfter(0)).thenReturn( new ArrayList<Address>());
        assertNotNull(addressControl.getExistingAddresses());
        assertEquals(new ArrayList<Address>() , addressControl.getExistingAddresses());

    }
}