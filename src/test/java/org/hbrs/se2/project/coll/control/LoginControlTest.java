package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class LoginControlTest {

    @InjectMocks
    LoginControl loginControl;

    @Mock
    private UserRepository repository;

    @Mock
    UserDTO userDTO;

    @Test
    void getCurrentUser() {
        assertEquals(userDTO , loginControl.getCurrentUser() );
    }
}