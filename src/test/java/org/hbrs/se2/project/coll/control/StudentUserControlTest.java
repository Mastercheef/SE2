package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.repository.StudentUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class StudentUserControlTest {

    @InjectMocks
    StudentUserControl studentUserControl;

    @Mock
    private StudentUserRepository studentUserRepository;
    @Mock
    private AddressControl addressControl;

    @Mock
    Address address;
    @Mock
    Address addressReturn;


    @Test
    void handleAddressExistance() {

        when(addressControl.checkAddressExistence(address)).thenReturn(addressReturn);
        assertEquals(addressReturn , studentUserControl.handleAddressExistance(address));
        assertTrue(studentUserControl.handleAddressExistance(address) instanceof  Address);
    }
}