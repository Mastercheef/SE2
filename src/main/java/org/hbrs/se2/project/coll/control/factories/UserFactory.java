package org.hbrs.se2.project.coll.control.factories;

import org.hbrs.se2.project.coll.dtos.StudentUserDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.StudentUser;
import org.hbrs.se2.project.coll.entities.User;

import java.time.LocalDate;

public class UserFactory {

    private UserFactory() {

    }

    public static StudentUser createStudentUser(StudentUserDTO studentDTO) {
        StudentUser studentUser = new StudentUser();
        studentUser.setId(studentDTO.getId());
        studentUser.setType(studentDTO.getType());
        studentUser.setSalutation(studentDTO.getSalutation());
        studentUser.setTitle(studentDTO.getTitle());
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

    public static User createUser(UserDTO userDTO) {
        User user = new User();

        user.setId(userDTO.getId());
        user.setSalutation(userDTO.getSalutation());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setTitle(userDTO.getTitle());
        user.setType(userDTO.getType());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());

        // Standard Values for Registration Purposes
        Address address = new Address();
        address.setId(10000008);    // Musteradresse
        user.setAddress(address);
        user.setPhone("00000000");
        user.setDateOfBirth(LocalDate.of(1900,1,1));
        return user;
    }

}
