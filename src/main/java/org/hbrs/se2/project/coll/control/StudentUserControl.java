package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.control.factories.UserFactory;
import org.hbrs.se2.project.coll.dtos.StudentUserDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.Settings;
import org.hbrs.se2.project.coll.entities.StudentUser;
import org.hbrs.se2.project.coll.repository.StudentUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentUserControl {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentUserControl.class);

    @Autowired
    private StudentUserRepository studentUserRepository;
    @Autowired
    private AddressControl addressControl;

    public StudentUser createNewStudentUser(UserDTO userDTO) throws DatabaseUserException {
        Address address = handleAddressExistance(userDTO.getAddress());
        Settings settings = new Settings();
        settings.setNotificationIsEnabled(true);

        StudentUser newStudentUser = UserFactory.createStudentUserFromBasicUser(userDTO);
        newStudentUser.setAddress(address);
        newStudentUser.setSettings(settings);
        settings.setUser(newStudentUser);

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
                LOGGER.info("LOG : Updated StudentUser profile with ID : {}" , studentUser.getId());
            else
                LOGGER.info("LOG : Created new StudentUser: {}" , studentUser.getId());

            return savedStudentUser;
        } catch (Exception exception) {
            LOGGER.info("LOG : {}" , exception.toString());
            if (exception instanceof org.springframework.dao.DataAccessResourceFailureException) {
                throw new DatabaseUserException("Während der Verbindung zur Datenbank mit JPA ist ein Fehler aufgetreten.");
            } else {
                throw new DatabaseUserException("Es ist ein unerwarteter Fehler aufgetreten.");
            }
        }
    }
}
