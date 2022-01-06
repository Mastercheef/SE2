package org.hbrs.se2.project.coll.control;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.dtos.impl.LoginResultDTOImpl;
import org.hbrs.se2.project.coll.repository.UserRepository;
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

    @Mock
    UserDTO userDTO;

    @Mock
    UserDTO userTmp = null;
    @Mock
    private LoginResultDTOImpl loginResult = new LoginResultDTOImpl();

    String email = "email.gmx.de";
    String plaintext = "passwd";
    @Test
    void getCurrentUser() {
        assertEquals(userDTO , loginControl.getCurrentUser() );
    }

    @Test
    void authentificate() {
        Mockito.spy(loginControl);
        doReturn(userDTO).when(loginControl).getUser(email,plaintext);
        assertEquals(loginResult , loginControl.authentificate(email , plaintext));

        doReturn(null).when(loginControl).getUser(email,plaintext);
        assertFalse(loginControl.authentificate(email,plaintext).getResult());
    }

    @Test
    void getUser() {

      /*
        String plaintext = "passwd";
        when(repository.findUserByEmail(email)).thenReturn(userDTO);
        when(userDTO.getPassword()).thenReturn("passwd");
        doNothing().when(loginResult).setResult(true);
        doNothing().when(loginResult).setReason("LogIn erflogreich");
        assertEquals(userDTO , loginControl.getUser(email,plaintext));

       */

    }
}