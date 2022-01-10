package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.builder.UserDTOBuilder;
import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.dtos.CompanyDTO;

import org.hbrs.se2.project.coll.dtos.RegistrationResultDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;

import org.hbrs.se2.project.coll.dtos.impl.RegistrationDTOImpl;
import org.hbrs.se2.project.coll.dtos.impl.UserDTOImpl;
import org.hbrs.se2.project.coll.repository.CompanyRepository;
import org.hbrs.se2.project.coll.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrationControlTest {


    @InjectMocks
    private RegistrationControl registrationControl = new RegistrationControl();

    @Mock
    UserRepository userRepository;
    @Mock
    CompanyControl companyControl;
    @Mock
    StudentUserControl studentUserControl;
    @Mock
    ContactPersonControl contactPersonControl;

    @Mock
    UserDTO userDTO = new UserDTOImpl();
    @Mock
    CompanyRepository companyRepository;
    String email = "test@mail.de";
    String companyName ="Mustermann GMBH";
    String website = "mustermann.de";
    @Mock
    CompanyDTO companyDTO;

    UserDTOImpl user;

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
        when(companyDTO.getCompanyName()).thenReturn(companyName);
        when(companyDTO.getEmail()).thenReturn(email);
        when(companyDTO.getWebsite()).thenReturn(website);

        when(companyRepository.findCompanyByCompanyNameAndEmailAndWebsite(
                companyDTO.getCompanyName(),
                companyDTO.getEmail(),
                companyDTO.getWebsite()
        )).thenReturn(companyDTO);

        when(companyDTO.getId()).thenReturn(100);
        assertTrue(registrationControl.checkIfCompanyAlreadyRegistered(companyDTO));
        when(companyDTO.getId()).thenReturn(0);
        assertFalse(registrationControl.checkIfCompanyAlreadyRegistered(companyDTO));

        when(companyRepository.findCompanyByCompanyNameAndEmailAndWebsite(
                companyDTO.getCompanyName(),
                companyDTO.getEmail(),
                companyDTO.getWebsite()
        )).thenReturn(null);
        assertFalse(registrationControl.checkIfCompanyAlreadyRegistered(companyDTO));

    }

    @Test
    void testRegisterUserPositiveStudent() throws DatabaseUserException {
        doReturn(null).when(userRepository).findUserByEmail(any());

        UserDTO userDTO = UserDTOBuilder
                .please()
                .createDefaultTestStudentUserWithFullData()
                .done();
        RegistrationDTOImpl registrationDTO = new RegistrationDTOImpl();
        registrationDTO.setUserDTO(userDTO);
        registrationDTO.setRepeatedEmail(userDTO.getEmail());
        registrationDTO.setRepeatedPassword(userDTO.getPassword());

        RegistrationResultDTO result = registrationControl.registerUser(registrationDTO);
        assertTrue(result.getResult());
        assertTrue(result.getReasons().contains(RegistrationResultDTO.ReasonType.SUCCESS));
    }

}