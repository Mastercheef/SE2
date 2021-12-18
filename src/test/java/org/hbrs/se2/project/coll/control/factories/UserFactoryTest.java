package org.hbrs.se2.project.coll.control.factories;

import org.hbrs.se2.project.coll.dtos.ContactPersonDTO;
import org.hbrs.se2.project.coll.dtos.StudentUserDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserFactoryTest {

    private static final String errorMessage = "class org.hbrs.se2.project.coll.control.factories.UserFactoryTest cannot access a member of class org.hbrs.se2.project.coll.control.factories.UserFactory with modifiers \"private\"";

    @Mock
    private StudentUserDTO studentDTO;

    @Mock
    private Address address;

    @Mock
    private UserDTO userDTO;

    @Mock
    private Company company;

    @Mock
    ContactPersonDTO contactPersonDTO;

    private final LocalDate localDate = LocalDate.of(2000, 1, 23);

    private static final String name = "Mustermann";
    private static final String email = "max@mustermann.de";
    private static final String password = "password1234";
    @Test
    void createStudentUser() {
        StudentUser studentUser;

        when(studentDTO.getId()).thenReturn(100);
        when(studentDTO.getType()).thenReturn("st");
        when(studentDTO.getSalutation()).thenReturn("Herr");
        when(studentDTO.getTitle()).thenReturn(null);
        when(studentDTO.getFirstName()).thenReturn("Max");
        when(studentDTO.getLastName()).thenReturn(name);
        when(studentDTO.getDateOfBirth()).thenReturn(localDate);
        when(studentDTO.getAddress()).thenReturn(address);
        when(studentDTO.getAddress().getStreet()).thenReturn("Mustermannstraße");
        when(studentDTO.getAddress().getCountry()).thenReturn("DE");
        when(studentDTO.getAddress().getHouseNumber()).thenReturn("2");
        when(studentDTO.getAddress().getCity()).thenReturn("Mustermannstadt");
        when(studentDTO.getAddress().getPostalCode()).thenReturn("12345");
        when(studentDTO.getPhone()).thenReturn("0123456789");
        when(studentDTO.getEmail()).thenReturn(email);
        when(studentDTO.getPassword()).thenReturn(password);
        when(studentDTO.getGraduation()).thenReturn("1.1.2000");
        when(studentDTO.getSkills()).thenReturn("Java");
        when(studentDTO.getInterests()).thenReturn("Viele");
        when(studentDTO.getWebsite()).thenReturn("www.mustermann.de");
        when(studentDTO.getDescription()).thenReturn("Mustermann Description");

        studentUser = UserFactory.createStudentUser(studentDTO);
        assertTrue(studentUser instanceof StudentUser, "Returned type is not a studentUser");

        assertEquals(100, studentUser.getId(), "ID is not set correctly,should be 100");
        assertEquals("st", studentUser.getType(), "Type is not set correctly,should be st");
        assertEquals("Herr", studentUser.getSalutation(), "Salutation is not set correctly,should be Herr");
        assertNull(studentUser.getTitle(), "Title is not set correctly,should be null");
        assertEquals("Max", studentUser.getFirstName(), "First name is not set correctly,should be Max");
        assertEquals("Mustermann", studentUser.getLastName(), "Last Name is not set correctly,should be " + name);
        assertEquals("Mustermannstraße", studentUser.getAddress().getStreet());
        assertEquals("DE", studentUser.getAddress().getCountry());
        assertEquals(Integer.valueOf(2), Integer.valueOf(studentUser.getAddress().getHouseNumber()));
        assertEquals("Mustermannstadt", studentUser.getAddress().getCity());
        assertEquals("12345", studentUser.getAddress().getPostalCode());
        assertEquals("0123456789", studentUser.getPhone(), "Phone is not set correctly,should be 0123456789");
        assertEquals(email, studentUser.getEmail(), "Email is not set correctly,should be " + email);        assertTrue(BCrypt.checkpw("password1234", studentUser.getPassword()), "Password is not set correctly,should be password1234");
        assertTrue(BCrypt.checkpw(password, studentUser.getPassword()), "Password is not set correctly,should be " + password);
        assertEquals("1.1.2000", studentUser.getGraduation(), "Graduation is not set correctly,should be: 1.1.2000");
        assertEquals("Java", studentUser.getSkills(), "Skills is not set correctly,should be:Java");
        assertEquals("Viele", studentUser.getInterests(), "Interests is not set correctly,should be Viele");
        assertEquals("www.mustermann.de", studentUser.getWebsite(), "Website is not set correctly,should be www.mustermann.de");
        assertEquals("Mustermann Description", studentUser.getDescription(), "Description is not set correctly,should be Mustermann Description");
        assertEquals("2000-01-23", studentUser.getDateOfBirth().toString());


    }

    @Test
    void createUser() {
        User user;

        when(userDTO.getId()).thenReturn(100);
        when(userDTO.getType()).thenReturn("st");
        when(userDTO.getSalutation()).thenReturn("Herr");
        when(userDTO.getTitle()).thenReturn(null);
        when(userDTO.getFirstName()).thenReturn("Max");
        when(userDTO.getLastName()).thenReturn(name);
        when(userDTO.getEmail()).thenReturn(email);
        when(userDTO.getPassword()).thenReturn(password);
        user = UserFactory.createUser(userDTO);

        //TODO Address
        assertEquals(100, user.getId(), "ID is not set correctly,should be 100");
        assertEquals("st", user.getType(), "Type is not set correctly,should be st");
        assertEquals("Herr", user.getSalutation(), "Salutation is not set correctly,should be Herr");
        assertNull(user.getTitle(), "Title is not set correctly,should be null");
        assertEquals("Max", user.getFirstName(), "First name is not set correctly,should be Max");
        assertEquals(name, user.getLastName(), "Last Name is not set correctly,should be " + name);
        assertEquals(email, user.getEmail(), "Email is not set correctly,should be " + email);
        assertTrue(BCrypt.checkpw(password, user.getPassword()), "Password is not set correctly,should be " + password);

    }

    @Test
    void itShouldThrowIllegalAccessExceptionWhenInstancingJobFactory() throws NoSuchMethodException {
        Constructor<UserFactory> constructor = UserFactory.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        Throwable exceptionThatWasThrown = assertThrows(IllegalAccessException.class, constructor::newInstance);
        assertEquals(errorMessage, exceptionThatWasThrown.getMessage());
        constructor.setAccessible(true);
        assertThrows(ReflectiveOperationException.class,constructor::newInstance);
    }

    @Test
    void createStudentUserFromBasicUser() {

        StudentUser studentUser;

        when(userDTO.getId()).thenReturn(100);
        when(userDTO.getType()).thenReturn("st");
        when(userDTO.getSalutation()).thenReturn("Herr");
        when(userDTO.getTitle()).thenReturn("Dr.");
        when(userDTO.getFirstName()).thenReturn("Max");
        when(userDTO.getLastName()).thenReturn(name);
        when(userDTO.getDateOfBirth()).thenReturn(localDate);
        when(userDTO.getAddress()).thenReturn(address);
        when(userDTO.getAddress().getStreet()).thenReturn("Mustermannstraße");
        when(userDTO.getAddress().getCountry()).thenReturn("DE");
        when(userDTO.getAddress().getHouseNumber()).thenReturn("2");
        when(userDTO.getAddress().getCity()).thenReturn("Mustermannstadt");
        when(userDTO.getAddress().getPostalCode()).thenReturn("12345");
        when(userDTO.getPhone()).thenReturn("0123456789");
        when(userDTO.getEmail()).thenReturn(email);
        when(userDTO.getPassword()).thenReturn(password);

        studentUser = UserFactory.createStudentUserFromBasicUser(userDTO);

        assertEquals(100 , studentUser.getId());
        assertEquals("st" , studentUser.getType());
        assertEquals("Herr" , studentUser.getSalutation());
        assertEquals("Dr." , studentUser.getTitle());
        assertEquals("Max" , studentUser.getFirstName());
        assertEquals("Mustermann" , studentUser.getLastName());
        assertEquals("0123456789" , studentUser.getPhone());
        assertEquals(LocalDate.of(2000, 1, 23), studentUser.getDateOfBirth());
        assertEquals(email ,studentUser.getEmail());
        assertTrue(BCrypt.checkpw(password, studentUser.getPassword()));
    }

    @Test
    void createContactPerson() {
        ContactPerson contactPerson ;
        when(contactPersonDTO.getId()).thenReturn(100);
        when(contactPersonDTO.getType()).thenReturn("st");
        when(contactPersonDTO.getSalutation()).thenReturn("Herr");
        when(contactPersonDTO.getTitle()).thenReturn("Prof.");
        when(contactPersonDTO.getFirstName()).thenReturn("Max");
        when(contactPersonDTO.getLastName()).thenReturn(name);
        when(contactPersonDTO.getDateOfBirth()).thenReturn(localDate);
        when(contactPersonDTO.getAddress()).thenReturn(address);
        when(contactPersonDTO.getAddress().getStreet()).thenReturn("Mustermannstraße");
        when(contactPersonDTO.getAddress().getCountry()).thenReturn("DE");
        when(contactPersonDTO.getAddress().getHouseNumber()).thenReturn("2");
        when(contactPersonDTO.getAddress().getCity()).thenReturn("Mustermannstadt");
        when(contactPersonDTO.getAddress().getPostalCode()).thenReturn("12345");
        when(contactPersonDTO.getPhone()).thenReturn("0123456789");
        when(contactPersonDTO.getEmail()).thenReturn(email);
        when(contactPersonDTO.getPassword()).thenReturn(password);
        when(contactPersonDTO.getCompany()).thenReturn(company);
        when(contactPersonDTO.getRole()).thenReturn("Datenbank-Experte");

        contactPerson = UserFactory.createContactPerson(contactPersonDTO);
        assertEquals(100 , contactPerson.getId());
        assertEquals("st" , contactPerson.getType());
        assertEquals("Herr" , contactPerson.getSalutation());
        assertEquals("Prof." , contactPerson.getTitle());
        assertEquals("Max" , contactPerson.getFirstName());
        assertEquals("Mustermann" , contactPerson.getLastName());
        assertEquals(localDate , contactPerson.getDateOfBirth());
        assertNotNull(contactPerson.getAddress());
        assertEquals("0123456789" , contactPerson.getPhone());
        assertEquals(email , contactPerson.getEmail());
        assertTrue(BCrypt.checkpw(password, contactPerson.getPassword()));
        assertNotNull(contactPerson.getCompany());
        assertEquals("Datenbank-Experte" , contactPerson.getRole());
    }

    @Test
    void createContactPersonFromBasicUser() {

        ContactPerson contactPerson;

        when(userDTO.getId()).thenReturn(100);
        when(userDTO.getType()).thenReturn("st");
        when(userDTO.getSalutation()).thenReturn("Herr");
        when(userDTO.getTitle()).thenReturn("Dr.");
        when(userDTO.getFirstName()).thenReturn("Max");
        when(userDTO.getLastName()).thenReturn(name);
        when(userDTO.getDateOfBirth()).thenReturn(localDate);
        when(userDTO.getAddress()).thenReturn(address);
        when(userDTO.getAddress().getStreet()).thenReturn("Mustermannstraße");
        when(userDTO.getAddress().getCountry()).thenReturn("DE");
        when(userDTO.getAddress().getHouseNumber()).thenReturn("2");
        when(userDTO.getAddress().getCity()).thenReturn("Mustermannstadt");
        when(userDTO.getAddress().getPostalCode()).thenReturn("12345");
        when(userDTO.getPhone()).thenReturn("0123456789");
        when(userDTO.getEmail()).thenReturn(email);
        when(userDTO.getPassword()).thenReturn(password);

        contactPerson = UserFactory.createContactPersonFromBasicUser(userDTO);
        assertSame(contactPerson.getClass(), ContactPerson.class, "Returned type is not a ContactPerson");

        assertEquals(100 , contactPerson.getId());
        assertEquals("st" , contactPerson.getType());
        assertEquals("Herr" , contactPerson.getSalutation());
        assertEquals("Dr." , contactPerson.getTitle());
        assertEquals("Max" , contactPerson.getFirstName());
        assertEquals("Mustermann" , contactPerson.getLastName());
        assertEquals("Mustermannstraße", contactPerson.getAddress().getStreet());
        assertEquals("DE", contactPerson.getAddress().getCountry());
        assertEquals(Integer.valueOf(2), Integer.valueOf(contactPerson.getAddress().getHouseNumber()));
        assertEquals("Mustermannstadt", contactPerson.getAddress().getCity());
        assertEquals("12345", contactPerson.getAddress().getPostalCode());
        assertEquals("0123456789" , contactPerson.getPhone());
        assertEquals(LocalDate.of(2000, 1, 23), contactPerson.getDateOfBirth());
        assertEquals(email , contactPerson.getEmail());
        assertTrue(BCrypt.checkpw(password, contactPerson.getPassword()));

    }
}