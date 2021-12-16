package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.control.factories.UserFactory;
import org.hbrs.se2.project.coll.dtos.StudentUserDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.StudentUser;
import org.hbrs.se2.project.coll.repository.StudentUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentUserControl {

    @Autowired
    private StudentUserRepository studentUserRepository;
    @Autowired
    private AddressControl addressControl;

    public StudentUser createNewStudentUser(UserDTO userDTO) throws DatabaseUserException {
        Address address = handleAddressExistance(userDTO.getAddress());
        StudentUser newStudentUser = UserFactory.createStudentUserFromBasicUser(userDTO);
        newStudentUser.setAddress(address);

        return saveStudentUser(newStudentUser);
    }

    public StudentUser updateStudentUser(StudentUserDTO studentUserDTO) throws DatabaseUserException {
        Address address = handleAddressExistance(studentUserDTO.getAddress());
        StudentUser studentUser = UserFactory.createStudentUser(studentUserDTO);
        studentUser.setAddress(address);

        return saveStudentUser(studentUser);
    }

    public Address handleAddressExistance(Address address) {
        return addressControl.checkAddressExistence(address);
    }

    private StudentUser saveStudentUser(StudentUser studentUser ) throws DatabaseUserException {
        try {
            // Abspeicherung der Entity in die DB
            StudentUser savedStudentUser = this.studentUserRepository.save( studentUser );

            if (studentUser.getId() > 0)
                System.out.println("LOG : Updated StudentUser profile with ID : " + studentUser.getId());
            else
                System.out.println("LOG : Created new StudentUser: " + studentUser.getId());

            return savedStudentUser;
        } catch (Exception exception) {
            System.out.println("LOG : " + exception);
            if (exception instanceof org.springframework.dao.DataAccessResourceFailureException) {
                throw new DatabaseUserException("Während der Verbindung zur Datenbank mit JPA ist ein Fehler aufgetreten.");
            } else {
                throw new DatabaseUserException("Es ist ein unerwarteter Fehler aufgetreten.");
            }
        }
    }
}
