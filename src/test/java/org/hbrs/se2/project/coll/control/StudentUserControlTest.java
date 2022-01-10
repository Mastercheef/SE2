package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.StudentUser;
import org.hbrs.se2.project.coll.repository.StudentUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
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

    @Mock
    StudentUser studentUser;

    @Mock
    StudentUser studentUserReturn;


    @Test
    void handleAddressExistance() {

        when(addressControl.checkAddressExistence(address)).thenReturn(addressReturn);
        assertEquals(addressReturn , studentUserControl.handleAddressExistance(address));
        assertTrue(studentUserControl.handleAddressExistance(address) instanceof  Address);
    }

    @Test
    void saveStudentUser() throws DatabaseUserException {
        doReturn(studentUserReturn).when(studentUserRepository).save(studentUser);
        doReturn(100).when(studentUser).getId();

        assertEquals(studentUserReturn , studentUserControl.saveStudentUser(studentUser));

        doReturn(0).when(studentUser).getId();
        assertEquals(studentUserReturn , studentUserControl.saveStudentUser(studentUser));
    }

    @Test
    void saveUserSettingsDataAccessResourceFailureException(){
        when(studentUserRepository.save(studentUser)).thenThrow(DataAccessResourceFailureException.class);
        try {
            assertThrows(DataAccessResourceFailureException.class , (Executable) studentUserControl.saveStudentUser(studentUser));
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }
    }

    @Test
    void saveUserSettingsOtherError() {
        when(studentUserRepository.save(studentUser)).thenThrow(DataIntegrityViolationException.class);
        try {
            assertThrows(DataIntegrityViolationException.class , (Executable) studentUserControl.saveStudentUser(studentUser));
        } catch (DatabaseUserException e) {
            e.printStackTrace();
        }
    }
}