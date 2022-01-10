package org.hbrs.se2.project.coll.control;
import org.hbrs.se2.project.coll.dtos.LoginResultDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.dtos.impl.LoginResultDTOImpl;
import org.hbrs.se2.project.coll.dtos.impl.UserDTOImpl;
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

    /*@Mock
    UserDTO userDTO;

    @Mock
    private LoginResultDTOImpl loginResult = new LoginResultDTOImpl();*/

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
        when(tmpUserDTO.getPassword()).thenReturn(hashedPW);
        when(repository.findUserByEmail(email)).thenReturn(tmpUserDTO);
    }

    @Test
    void testAuthenticatePositive() {
        LoginResultDTO result = loginControl.authentificate(email,plaintext);
        assertTrue(result.getResult());
        assertEquals(loginSucces, result.getReason());
    }

    @Test
    void testAuthenticateNegative() {
        LoginResultDTO result = loginControl.authentificate(email,"anders");
        assertFalse(result.getResult());
        assertEquals(loginWrongPassword, result.getReason());
    }

    @Test
    void testAuthenticateNegativeException() {
        when(tmpUserDTO.getPassword()).thenThrow(org.springframework.dao.DataAccessResourceFailureException.class);
        LoginResultDTO result = loginControl.authentificate(email,"anders");
        assertFalse(result.getResult());
        assertTrue(result.getReason().contains("Es ist ein Fehler während der Verbindung zur Datenbank aufgetreten"));
    }
}