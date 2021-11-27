package org.hbrs.se2.project.coll.control.factories;

import org.hbrs.se2.project.coll.dtos.StudentUserDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.StudentUser;
import org.hbrs.se2.project.coll.entities.User;

public class UserFactory {

    public static StudentUser createStudentUser(StudentUserDTO studentDTO) {
        StudentUser studentUser = new StudentUser();
        studentUser.setUserId(studentDTO.getUserId());
        studentUser.setFirstName(studentDTO.getFirstName());
        studentUser.setLastName(studentDTO.getLastName());
        studentUser.setAddress(studentDTO.getAddress());
        studentUser.setPhone(studentDTO.getPhone());
        studentUser.setDateOfBirth(studentDTO.getDateOfBirth());
        studentUser.setEmail(studentDTO.getEmail());
        studentUser.setPassword(studentDTO.getPassword());
        studentUser.setGraduation(studentDTO.getGraduation());
        studentUser.setSkills(studentDTO.getSkills());
        studentUser.setInterests(studentDTO.getInterests());
        studentUser.setWebsite(studentDTO.getWebsite());
        studentUser.setDescription(studentDTO.getDescription());

        return studentUser;
    }

}
