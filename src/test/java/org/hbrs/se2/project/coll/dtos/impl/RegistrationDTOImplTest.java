package org.hbrs.se2.project.coll.dtos.impl;

import org.hbrs.se2.project.coll.dtos.CompanyDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class RegistrationDTOImplTest {

    RegistrationDTOImpl registrationDTO = new RegistrationDTOImpl();

    @Mock
    private UserDTO userDTO;

    private String repeatedEmail = "email@hbrs";

    private String repeatedPassword = "password";
    @Mock
    private CompanyDTO companyDTO;

    @Test
    void setUserDTO() {
        assertNull(registrationDTO.getUserDTO());
        registrationDTO.setUserDTO(userDTO);
        assertNotNull(registrationDTO.getUserDTO());
    }

    @Test
    void setRepeatedEmail() {
        registrationDTO.setRepeatedEmail(repeatedEmail);
        assertEquals(repeatedEmail , registrationDTO.getRepeatedEmail());
    }

    @Test
    void setRepeatedPassword() {
        registrationDTO.setRepeatedPassword(repeatedPassword);
        assertEquals(repeatedPassword , registrationDTO.getRepeatedPassword());
    }

    @Test
    void setCompanyDTO() {
        registrationDTO.setCompanyDTO(companyDTO);
        assertNotNull(registrationDTO.getCompanyDTO());
    }

    @Test
    void getUserDTO() {
        registrationDTO.setUserDTO(userDTO);
        assertNotNull(registrationDTO.getUserDTO());
    }

    @Test
    void getRepeatedEmail() {
        registrationDTO.setRepeatedEmail(repeatedEmail);
        assertEquals(repeatedEmail , registrationDTO.getRepeatedEmail());
    }

    @Test
    void getRepeatedPassword() {
        registrationDTO.setRepeatedPassword(repeatedPassword);
        assertEquals(repeatedPassword , registrationDTO.getRepeatedPassword());
    }

    @Test
    void getCompanyDTO() {
        registrationDTO.setCompanyDTO(companyDTO);
        assertNotNull(registrationDTO.getCompanyDTO());
    }

    @Test
    void testConstructor() {
        RegistrationDTOImpl registrationDTO = new RegistrationDTOImpl(userDTO , repeatedEmail, repeatedPassword);
        assertNotNull(registrationDTO.getUserDTO());
        assertEquals(repeatedEmail , registrationDTO.getRepeatedEmail());
        assertEquals(repeatedPassword , registrationDTO.getRepeatedPassword());

    }
}