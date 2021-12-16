package org.hbrs.se2.project.coll.control.factories;

import org.hbrs.se2.project.coll.dtos.ContactPersonDTO;
import org.hbrs.se2.project.coll.dtos.StudentUserDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.ContactPerson;
import org.hbrs.se2.project.coll.entities.StudentUser;
import org.hbrs.se2.project.coll.entities.User;

public class UserFactory {

    private UserFactory() {
        throw new IllegalStateException("Factory Class");
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

    public static StudentUser createStudentUserFromBasicUser(UserDTO userDTO) {
        StudentUser studentUser = new StudentUser();
        studentUser.setId(userDTO.getId());
        studentUser.setType(userDTO.getType());
        studentUser.setSalutation(userDTO.getSalutation());
        studentUser.setTitle(userDTO.getTitle());
        studentUser.setFirstName(userDTO.getFirstName());
        studentUser.setLastName(userDTO.getLastName());
        studentUser.setAddress(userDTO.getAddress());
        studentUser.setPhone(userDTO.getPhone());
        studentUser.setDateOfBirth(userDTO.getDateOfBirth());
        studentUser.setEmail(userDTO.getEmail());
        studentUser.setPassword(userDTO.getPassword());

        return studentUser;
    }

    public static ContactPerson createContactPerson(ContactPersonDTO contactPersonDTO) {
        ContactPerson contactPerson = new ContactPerson();
        contactPerson.setId(contactPersonDTO.getId());
        contactPerson.setType(contactPersonDTO.getType());
        contactPerson.setSalutation(contactPersonDTO.getSalutation());
        contactPerson.setTitle(contactPersonDTO.getTitle());
        contactPerson.setFirstName(contactPersonDTO.getFirstName());
        contactPerson.setLastName(contactPersonDTO.getLastName());
        contactPerson.setAddress(contactPersonDTO.getAddress());
        contactPerson.setPhone(contactPersonDTO.getPhone());
        contactPerson.setDateOfBirth(contactPersonDTO.getDateOfBirth());
        contactPerson.setEmail(contactPersonDTO.getEmail());
        contactPerson.setPassword(contactPersonDTO.getPassword());
        contactPerson.setCompany(contactPersonDTO.getCompany());
        contactPerson.setRole(contactPersonDTO.getRole());

        return contactPerson;
    }

    public static ContactPerson createContactPersonFromBasicUser(UserDTO userDTO) {
        ContactPerson contactPerson = new ContactPerson();
        contactPerson.setId(userDTO.getId());
        contactPerson.setType(userDTO.getType());
        contactPerson.setSalutation(userDTO.getSalutation());
        contactPerson.setTitle(userDTO.getTitle());
        contactPerson.setFirstName(userDTO.getFirstName());
        contactPerson.setLastName(userDTO.getLastName());
        contactPerson.setAddress(userDTO.getAddress());
        contactPerson.setPhone(userDTO.getPhone());
        contactPerson.setDateOfBirth(userDTO.getDateOfBirth());
        contactPerson.setEmail(userDTO.getEmail());
        contactPerson.setPassword(userDTO.getPassword());

        return contactPerson;
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
        user.setAddress(userDTO.getAddress());

        return user;
    }

}
