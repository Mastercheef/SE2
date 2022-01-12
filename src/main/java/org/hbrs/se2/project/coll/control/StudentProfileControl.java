package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.dtos.StudentUserDTO;
import org.hbrs.se2.project.coll.entities.StudentUser;
import org.hbrs.se2.project.coll.repository.StudentUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentProfileControl {

    @Autowired
    private StudentUserRepository studentUserRepository;
    @Autowired
    private StudentUserControl studentUserControl;

    public StudentUserDTO loadProfileDataById(int id) {
        return studentUserRepository.findStudentUserById(id);
    }

    public StudentUser updateStudentProfile(StudentUserDTO studentUserDTO ) throws DatabaseUserException {
        // StudentUser and StudentProfile are using the same database table / datasets
        return studentUserControl.updateStudentUser(studentUserDTO);
    }
}
