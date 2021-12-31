package org.hbrs.se2.project.coll.control.factories;

import org.hbrs.se2.project.coll.dtos.ContactPersonDTO;
import org.hbrs.se2.project.coll.dtos.StudentUserDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    private static final String ERROR_MESSAGE = "class org.hbrs.se2.project.coll.control.factories.UserFactoryTest cannot access a member of class org.hbrs.se2.project.coll.control.factories.UserFactory with modifiers \"private\"";

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

    private static final String NAME = "Mustermann";
    private static final String EMAIL = "max@mustermann.de";
    private static final String PASSWORD = "password1234";
    private static final String STREET = "Mustermannstraße";
    private static final String CITY = "Mustermannstadt";
    private static final String PLZ = "12345";
    private static final String PHONE_NUMBER = "0123456789";
    @Test
    void createStudentUser() {
        StudentUser studentUser;

        when(studentDTO.getId()).thenReturn(100);
        when(studentDTO.getType()).thenReturn("st");
        when(studentDTO.getSalutation()).thenReturn("Herr");
        when(studentDTO.getTitle()).thenReturn(null);
        when(studentDTO.getFirstName()).thenReturn("Max");
        when(studentDTO.getLastName()).thenReturn(NAME);
        when(studentDTO.getDateOfBirth()).thenReturn(localDate);
        when(studentDTO.getAddress()).thenReturn(address);
        when(studentDTO.getAddress().getStreet()).thenReturn(STREET);
        when(studentDTO.getAddress().getCountry()).thenReturn("DE");
        when(studentDTO.getAddress().getHouseNumber()).thenReturn("2");
        when(studentDTO.getAddress().getCity()).thenReturn(CITY);
        when(studentDTO.getAddress().getPostalCode()).thenReturn(PLZ);
        when(studentDTO.getPhone()).thenReturn(PHONE_NUMBER);
        when(studentDTO.getEmail()).thenReturn(EMAIL);
        when(studentDTO.getPassword()).thenReturn(PASSWORD);
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
        assertEquals("Mustermann", studentUser.getLastName(), "Last Name is not set correctly,should be " + NAME);
        assertEquals(STREET, studentUser.getAddress().getStreet());
        assertEquals("DE", studentUser.getAddress().getCountry());
        assertEquals(Integer.valueOf(2), Integer.valueOf(studentUser.getAddress().getHouseNumber()));
        assertEquals(CITY, studentUser.getAddress().getCity());
        assertEquals(PLZ, studentUser.getAddress().getPostalCode());
        assertEquals(PHONE_NUMBER, studentUser.getPhone(), "Phone is not set correctly,should be 0123456789");
        assertEquals(EMAIL, studentUser.getEmail(), "Email is not set correctly,should be " + EMAIL);
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
        when(userDTO.getLastName()).thenReturn(NAME);
        when(userDTO.getEmail()).thenReturn(EMAIL);
        when(userDTO.getPassword()).thenReturn(PASSWORD);
        user = UserFactory.createUser(userDTO);
        assertEquals(100, user.getId(), "ID is not set correctly,should be 100");
        assertEquals("st", user.getType(), "Type is not set correctly,should be st");
        assertEquals("Herr", user.getSalutation(), "Salutation is not set correctly,should be Herr");
        assertNull(user.getTitle(), "Title is not set correctly,should be null");
        assertEquals("Max", user.getFirstName(), "First name is not set correctly,should be Max");
        assertEquals(NAME, user.getLastName(), "Last Name is not set correctly,should be " + NAME);
        assertEquals(EMAIL, user.getEmail(), "Email is not set correctly,should be " + EMAIL);
    }

    @Test
    void itShouldThrowIllegalAccessExceptionWhenInstancingJobFactory() throws NoSuchMethodException {
        Constructor<UserFactory> constructor = UserFactory.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        Throwable exceptionThatWasThrown = assertThrows(IllegalAccessException.class, constructor::newInstance);
        assertEquals(ERROR_MESSAGE, exceptionThatWasThrown.getMessage());
        constructor.setAccessible(true);
        assertThrows(ReflectiveOperationException.class,constructor::newInstance);
    }

    @Test
    void createStudentUserFromBasicUser() {

        StudentUser studentUser;

        mocking();

        studentUser = UserFactory.createStudentUserFromBasicUser(userDTO);

        assertEquals(100 , studentUser.getId());
        assertEquals("st" , studentUser.getType());
        assertEquals("Herr" , studentUser.getSalutation());
        assertEquals("Dr." , studentUser.getTitle());
        assertEquals("Max" , studentUser.getFirstName());
        assertEquals("Mustermann" , studentUser.getLastName());
        assertEquals(PHONE_NUMBER , studentUser.getPhone());
        assertEquals(LocalDate.of(2000, 1, 23), studentUser.getDateOfBirth());
        assertEquals(EMAIL,studentUser.getEmail());
    }

    private void mocking() {
        when(userDTO.getId()).thenReturn(100);
        when(userDTO.getType()).thenReturn("st");
        when(userDTO.getSalutation()).thenReturn("Herr");
        when(userDTO.getTitle()).thenReturn("Dr.");
        when(userDTO.getFirstName()).thenReturn("Max");
        when(userDTO.getLastName()).thenReturn(NAME);
        when(userDTO.getDateOfBirth()).thenReturn(localDate);
        when(userDTO.getAddress()).thenReturn(address);
        when(userDTO.getAddress().getStreet()).thenReturn(STREET);
        when(userDTO.getAddress().getCountry()).thenReturn("DE");
        when(userDTO.getAddress().getHouseNumber()).thenReturn("2");
        when(userDTO.getAddress().getCity()).thenReturn(CITY);
        when(userDTO.getAddress().getPostalCode()).thenReturn(PLZ);
        when(userDTO.getPhone()).thenReturn(PHONE_NUMBER);
        when(userDTO.getEmail()).thenReturn(EMAIL);
        when(userDTO.getPassword()).thenReturn(PASSWORD);
    }

    @Test
    void createContactPerson() {
        ContactPerson contactPerson ;
        when(contactPersonDTO.getId()).thenReturn(100);
        when(contactPersonDTO.getType()).thenReturn("st");
        when(contactPersonDTO.getSalutation()).thenReturn("Herr");
        when(contactPersonDTO.getTitle()).thenReturn("Prof.");
        when(contactPersonDTO.getFirstName()).thenReturn("Max");
        when(contactPersonDTO.getLastName()).thenReturn(NAME);
        when(contactPersonDTO.getDateOfBirth()).thenReturn(localDate);
        when(contactPersonDTO.getAddress()).thenReturn(address);
        when(contactPersonDTO.getAddress().getStreet()).thenReturn(STREET);
        when(contactPersonDTO.getAddress().getCountry()).thenReturn("DE");
        when(contactPersonDTO.getAddress().getHouseNumber()).thenReturn("2");
        when(contactPersonDTO.getAddress().getCity()).thenReturn(CITY);
        when(contactPersonDTO.getAddress().getPostalCode()).thenReturn(PLZ);
        when(contactPersonDTO.getPhone()).thenReturn(PHONE_NUMBER);
        when(contactPersonDTO.getEmail()).thenReturn(EMAIL);
        when(contactPersonDTO.getPassword()).thenReturn(PASSWORD);
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
        assertEquals(PHONE_NUMBER , contactPerson.getPhone());
        assertEquals(EMAIL, contactPerson.getEmail());
        assertNotNull(contactPerson.getCompany());
        assertEquals("Datenbank-Experte" , contactPerson.getRole());
    }

    @Test
    void createContactPersonFromBasicUser() {

        ContactPerson contactPerson;

        mocking();
        contactPerson = UserFactory.createContactPersonFromBasicUser(userDTO);
        assertSame(contactPerson.getClass(), ContactPerson.class, "Returned type is not a ContactPerson");

        assertEquals(100 , contactPerson.getId());
        assertEquals("st" , contactPerson.getType());
        assertEquals("Herr" , contactPerson.getSalutation());
        assertEquals("Dr." , contactPerson.getTitle());
        assertEquals("Max" , contactPerson.getFirstName());
        assertEquals("Mustermann" , contactPerson.getLastName());
        assertEquals(STREET, contactPerson.getAddress().getStreet());
        assertEquals("DE", contactPerson.getAddress().getCountry());
        assertEquals(Integer.valueOf(2), Integer.valueOf(contactPerson.getAddress().getHouseNumber()));
        assertEquals(CITY, contactPerson.getAddress().getCity());
        assertEquals(PLZ, contactPerson.getAddress().getPostalCode());
        assertEquals(PHONE_NUMBER , contactPerson.getPhone());
        assertEquals(LocalDate.of(2000, 1, 23), contactPerson.getDateOfBirth());
        assertEquals(EMAIL, contactPerson.getEmail());


    }
}