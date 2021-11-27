package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.factories.UserFactory;
import org.hbrs.se2.project.coll.dtos.StudentUserDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.StudentUser;
import org.hbrs.se2.project.coll.repository.StudentUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentProfileControl {

    @Autowired
    private StudentUserRepository repository;

    public StudentUserDTO loadProfileDataById(int id) {
        return repository.findStudentUserById(id);
    }

    public void saveStudentUser( StudentUserDTO userDTO ) {
        // Hier könnte man noch die Gültigkeit der Daten überprüfen
        // check( userDTO );

        //Erzeuge ein neues Car-Entity konsistent über eine Factory
        StudentUser studentUser = UserFactory.createNewStudentUser(userDTO);
        System.out.println("Created StudentUser Entity: " + studentUser.getId());

        // Abspeicherung des Entity in die DB
        this.repository.save( studentUser );

    }
}
