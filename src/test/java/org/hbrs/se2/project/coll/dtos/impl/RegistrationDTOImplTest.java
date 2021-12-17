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

    private String repeatedEmail;

    private String repeatedPassword;
    @Mock
    private CompanyDTO companyDTO;

    @Test
    void setUserDTO() {
        registrationDTO.setUserDTO(userDTO);
    }

    @Test
    void setRepeatedEmail() {
    }

    @Test
    void setRepeatedPassword() {
    }

    @Test
    void setCompanyDTO() {
    }

    @Test
    void getUserDTO() {
    }

    @Test
    void getRepeatedEmail() {
    }

    @Test
    void getRepeatedPassword() {
    }

    @Test
    void getCompanyDTO() {
    }
}