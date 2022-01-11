package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.dtos.LoginResultDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.repository.UserRepository;
import org.hbrs.se2.project.coll.util.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginControlTest {

    @InjectMocks
    @Spy
    LoginControl loginControl;

    @Mock
    private UserRepository repository;

    String email = "email.gmx.de";
    String plaintext = "passwd";
    String loginWrongUserAndPass = "Benutzername oder Passwort falsch";
    String loginWrongPassword = "Das eingegebene Password ist falsch!";
    String loginSucces = "LogIn erfolgreich";
    UserDTO tmpUserDTO;
    String hashedPW = Utils.hashPassword(plaintext);

    @BeforeEach
    void setUp() {
        tmpUserDTO = Mockito.mock(UserDTO.class);
        when(repository.findUserByEmail(email)).thenReturn(tmpUserDTO);
    }

    @Test
    void getCurrentUser() {
        when(tmpUserDTO.getPassword()).thenReturn(hashedPW);
        loginControl.authentificate(email,plaintext);
        assertEquals(tmpUserDTO, loginControl.getCurrentUser());
    }


    @Test
    void testAuthenticatePositive() {
        when(tmpUserDTO.getPassword()).thenReturn(hashedPW);
        LoginResultDTO result = loginControl.authentificate(email,plaintext);
        assertTrue(result.getResult());
        assertEquals(loginSucces, result.getReason());
    }

    @Test
    void testAuthenticateNegative() {
        when(tmpUserDTO.getPassword()).thenReturn(hashedPW);
        LoginResultDTO result = loginControl.authentificate(email,"anders");
        assertFalse(result.getResult());
        assertEquals(loginWrongPassword, result.getReason());
    }

    @Test
    void testAuthenticateNegativeNull() {
        when(repository.findUserByEmail(email)).thenReturn(null);
        LoginResultDTO result = loginControl.authentificate(email,"anders");
        assertFalse(result.getResult());
        assertEquals(loginWrongUserAndPass, result.getReason());
    }

    @Test
    void testAuthenticateNegativeException() {
        when(tmpUserDTO.getPassword()).thenThrow(org.springframework.dao.DataAccessResourceFailureException.class);
        LoginResultDTO result = loginControl.authentificate(email,"anders");
        assertFalse(result.getResult());
        assertTrue(result.getReason().contains("Es ist ein Fehler während der Verbindung zur Datenbank aufgetreten"));
    }
}