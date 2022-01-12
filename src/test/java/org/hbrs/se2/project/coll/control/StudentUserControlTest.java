package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.builder.UserDTOBuilder;
import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.control.factories.UserFactory;
import org.hbrs.se2.project.coll.dtos.StudentUserDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.StudentUser;
import org.hbrs.se2.project.coll.repository.StudentUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
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

    Address address = Mockito.mock(Address.class);
    Address addressReturn = Mockito.mock(Address.class);

    StudentUser studentUser = Mockito.mock(StudentUser.class);
    StudentUser studentUserReturn = Mockito.mock(StudentUser.class);

    StudentUserDTO studentUserDTO = Mockito.mock(StudentUserDTO.class);

    @Test
    void testHandleAddressExistance() {

        when(addressControl.checkAddressExistence(address)).thenReturn(addressReturn);
        assertEquals(addressReturn , studentUserControl.handleAddressExistance(address));
        assertTrue(studentUserControl.handleAddressExistance(address) instanceof  Address);
    }

    @Test
    void testCreateNewStudentUser() throws DatabaseUserException {
        UserDTO userBuild = UserDTOBuilder
                .please()
                .createDefaultTestUserWithFullData()
                .withType("st")
                .done();
        when(addressControl.checkAddressExistence(any())).thenReturn(addressReturn);
        when(studentUserRepository.save(any())).then(returnsFirstArg());
        StudentUser resultStudentUser = studentUserControl.createNewStudentUser(userBuild);
        assertEquals(userBuild.getFirstName(), resultStudentUser.getFirstName());
        assertEquals(userBuild.getLastName(), resultStudentUser.getLastName());
        assertEquals(addressReturn, resultStudentUser.getAddress());
        assertNotNull(resultStudentUser.getSettings());
    }

    @Test
    void testUpdateStudentUser() throws DatabaseUserException {
        when(studentUserReturn.getId()).thenReturn(1);
        when(studentUserReturn.getAddress()).thenReturn(addressReturn);

        when(studentUserDTO.getAddress()).thenReturn(address);
        when(addressControl.checkAddressExistence(address)).thenReturn(addressReturn);
        try (MockedStatic<UserFactory> mockFactory = Mockito.mockStatic(UserFactory.class)) {
            mockFactory.when(() -> UserFactory.createStudentUser(studentUserDTO)).thenReturn(studentUserReturn);
            when(studentUserRepository.save(any())).then(returnsFirstArg());

            StudentUser resultStudentuser = studentUserControl.updateStudentUser(studentUserDTO);
            assertEquals(studentUserReturn.getId(), resultStudentuser.getId());
            assertEquals(studentUserReturn.getAddress(), resultStudentuser.getAddress());
        }
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