package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.ContactPerson;
import org.hbrs.se2.project.coll.repository.ContactPersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ContactPersonControlTest {

    @InjectMocks
    private ContactPersonControl contactPersonControl;

    @Mock
    private ContactPersonRepository contactPersonRepository;

    @Mock
    private AddressControl addressControl;

    @Mock
    Address address;
    @Mock
    Address addressReturn;

    @Mock
    ContactPerson contactPerson;
    @Mock
    ContactPerson contactPersonDTO;

    @Mock
    UserDTO currentUser;
    @Test
    void findContactPersonById() {
        when(contactPersonRepository.findContactPersonById(100)).thenReturn(contactPerson);
        assertNotNull(contactPersonControl.findContactPersonById(100));
        assertTrue(contactPersonControl.findContactPersonById(100) instanceof  ContactPerson);
    }

    @Test
    void handleAddressExistance() {
        when(addressControl.checkAddressExistence(address)).thenReturn(addressReturn);
        assertNotNull(contactPersonControl.handleAddressExistance(address));
        assertEquals(addressReturn , contactPersonControl.handleAddressExistance(address));
    }

    @Test
    void checkIfUserIsProfileOwner() {
        when(contactPersonRepository.findContactPersonByCompanyId(200)).thenReturn(contactPersonDTO);
        when(currentUser.getId()).thenReturn(100);
        when(contactPersonDTO.getId()).thenReturn(200);
        assertFalse(contactPersonControl.checkIfUserIsProfileOwner(currentUser, 200));
        when(currentUser.getId()).thenReturn(200);
        assertTrue(contactPersonControl.checkIfUserIsProfileOwner(currentUser, 200));
    }
}