package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.dtos.impl.StudentUserDTOImpl;
import org.hbrs.se2.project.coll.repository.StudentUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentProfileControlTest {

    @InjectMocks
    private StudentProfileControl studentProfileControl = new StudentProfileControl();

    @Mock
    private StudentUserRepository studentUserRepository;

    @Mock
    StudentUserControl studentUserControl;


    StudentUserDTOImpl studentUserDTO;
    StudentUserDTOImpl studentUserDTOUpdated;

    @BeforeEach
    void setup() {
        studentUserDTO = new StudentUserDTOImpl();
        studentUserDTO.setId(100);
    }

    @Test
    void loadProfileDataById() {
        MockitoAnnotations.openMocks(this);
        doReturn(studentUserDTO).when(studentUserRepository).findStudentUserById(100);
        assertNotNull(studentProfileControl.loadProfileDataById(100));
        assertEquals(studentUserDTO , studentProfileControl.loadProfileDataById(100));


    }

    @Test
    void updateStudentProfile() throws DatabaseUserException {
        MockitoAnnotations.openMocks(this);
        doReturn(studentUserDTOUpdated).when(studentUserControl).updateStudentUser(studentUserDTO);
        assertEquals(studentUserDTOUpdated , studentProfileControl.updateStudentProfile(studentUserDTO));
    }
}