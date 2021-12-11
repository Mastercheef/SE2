package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.factories.UserFactory;
import org.hbrs.se2.project.coll.dtos.StudentUserDTO;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.StudentUser;
import org.hbrs.se2.project.coll.repository.AddressRepository;
import org.hbrs.se2.project.coll.repository.StudentUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentProfileControl {

    @Autowired
    private StudentUserRepository repository;
    @Autowired
    private AddressControl addressControl;

    public StudentUserDTO loadProfileDataById(int id) {
        return repository.findStudentUserById(id);
    }

    //TODO: ResultDTO mit Rückmeldung für View bei Fehler
    public StudentUser saveStudentUser( StudentUserDTO userDTO ) {
        try {
            // Hier könnte man noch die Gültigkeit der Daten überprüfen
            // check( userDTO );

            //Erzeuge ein neues Car-Entity konsistent über eine Factory
            StudentUser studentUser = UserFactory.createStudentUser(userDTO);
            // Prüfen, ob eingetragene Adresse bereits als Datensatz vorhanden ist. Wenn ja, wird Datensatz der Adresse
            // aus DB geholt und der erzeugten Entity zugewiesen
            studentUser.setAddress(addressControl.checkAddressExistence(studentUser.getAddress()));

            // Abspeicherung des Entity in die DB
            StudentUser savedStudentUser = this.repository.save( studentUser );

            if (studentUser.getId() > 0)
                System.out.println("Updated StudentUser profile: " + studentUser.getId());
            else
                System.out.println("Created new StudentUser: " + studentUser.getId());

            return savedStudentUser;
        } catch (Error error) {
            // return resultdto mit Fehler
            return null;
        }
    }
}
