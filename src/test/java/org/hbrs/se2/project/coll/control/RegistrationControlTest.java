package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.dtos.RegistrationDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.dtos.impl.RegistrationResultDTOImpl;
import org.hbrs.se2.project.coll.dtos.impl.UserDTOImpl;
import org.hbrs.se2.project.coll.repository.CompanyRepository;
import org.hbrs.se2.project.coll.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class RegistrationControlTest {


    @InjectMocks
    private RegistrationControl registrationControl = new RegistrationControl();

    @Mock
    UserRepository userRepository;
    @Mock
    UserDTO userDTO = new UserDTOImpl();

    String email = "test@mail.de";

    @Test
    void checkIfEmailAlreadyInUse() {

        doReturn(userDTO).when(userRepository).findUserByEmail(email);
        when(userDTO.getId()).thenReturn(100);
        assertTrue(registrationControl.checkIfEmailAlreadyInUse(email));

    }

    @Test
    void checkIfEmailAlreadyInUseNoID() {
        doReturn(userDTO).when(userRepository).findUserByEmail(email);
        when(userDTO.getId()).thenReturn(0);
        assertFalse(registrationControl.checkIfEmailAlreadyInUse(email));
    }

    @Test
    void checkIfEmailAlreadyNull() {
        doReturn(null).when(userRepository).findUserByEmail(email);
        assertFalse(registrationControl.checkIfEmailAlreadyInUse(email));

    }
    @Test
    void checkIfCompanyAlreadyRegistered() {
    }

    @Test
    void checkRepeatedEmail() {
    }

    @Test
    void checkRepeatedPassword() {
    }
}