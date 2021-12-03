package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.factories.UserFactory;
import org.hbrs.se2.project.coll.dtos.StudentUserDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.dtos.impl.StudentUserDTOImpl;
import org.hbrs.se2.project.coll.entities.StudentUser;
import org.hbrs.se2.project.coll.entities.User;
import org.hbrs.se2.project.coll.repository.StudentUserRepository;
import org.hbrs.se2.project.coll.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegistrationControl {

    @Autowired
    UserRepository userRepository;

    StudentProfileControl control;


    public void registerUser(UserDTO userDTO) {
        try {
            User newUser = UserFactory.createUser(userDTO);
            this.userRepository.save(newUser);
/*
            if(userDTO.getType() == "st") {
                StudentUserDTOImpl studentProfile = new StudentUserDTOImpl();
                studentProfile.setId(userDTO.getId());
                studentProfile.setAddress(userDTO.getAddress());
                studentProfile.setDateOfBirth(userDTO.getDateOfBirth());
                control.saveStudentUser(studentProfile);
            }*/

        } catch (Error error) {
            // TODO: Return resultDTO mit Fehler (return RegistrationResultDTO)
        }
    }

    public boolean checkUserExistence(UserDTO user) {
        UserDTO existingUser = userRepository.findUserByEmail(user.getEmail());

        if(existingUser != null && existingUser.getId() > 0)
            return true;
        else
            return false;
    }
}
