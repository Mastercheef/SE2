package org.hbrs.se2.project.coll.control.factories;

import org.hbrs.se2.project.coll.dtos.StudentUserDTO;
import org.hbrs.se2.project.coll.entities.StudentUser;

public class UserFactory {

    public static StudentUser createNewStudentUser(StudentUserDTO studentDTO) {
        StudentUser newStudentUser = new StudentUser();
        newStudentUser.setFirstName(studentDTO.getFirstName());
        newStudentUser.setLastName(studentDTO.getLastName());
        newStudentUser.setAddress(studentDTO.getAddress());
        newStudentUser.setPhone(studentDTO.getPhone());
        //newStudentUser.setDateOfBirth(studentDTO.getDateOfBirth());
        newStudentUser.setEmail(studentDTO.getEmail());
        newStudentUser.setPassword(studentDTO.getPassword());
        newStudentUser.setGraduation(studentDTO.getGraduation());
        newStudentUser.setSkills(studentDTO.getSkills());
        newStudentUser.setInterests(studentDTO.getInterests());
        newStudentUser.setWebsite(studentDTO.getWebsite());
        newStudentUser.setDescription(studentDTO.getDescription());

        return newStudentUser;
    }

}
