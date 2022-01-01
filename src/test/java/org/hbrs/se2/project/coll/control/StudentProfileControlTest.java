package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.dtos.StudentUserDTO;
import org.hbrs.se2.project.coll.entities.StudentUser;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class StudentProfileControlTest {

    @Mock
    private StudentProfileControl studentProfileControl;

    @Mock
    StudentUserDTO studentUserDTO;

    @Test
    void loadProfileDataById() {
        when(studentProfileControl.loadProfileDataById(100)).thenReturn(studentUserDTO);
        assertNotNull(studentProfileControl.loadProfileDataById(100));
    }

    @Test
    void updateStudentProfile() throws DatabaseUserException {

        when(studentUserDTO.getId()).thenReturn(100);
        assertEquals(100,studentUserDTO.getId());

        when(studentProfileControl.updateStudentProfile(studentUserDTO)).thenReturn(new StudentUser());
        assertEquals(new StudentUser(), studentProfileControl.updateStudentProfile(studentUserDTO));


    }
}