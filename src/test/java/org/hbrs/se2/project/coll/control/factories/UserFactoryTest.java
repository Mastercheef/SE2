package org.hbrs.se2.project.coll.control.factories;

import org.hbrs.se2.project.coll.dtos.StudentUserDTO;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.StudentUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserFactoryTest {

    @Mock
    StudentUserDTO studentDTO;

    StudentUser studentUser;

    @Mock
    Address address;

    @Test
    void createStudentUser() {
        when(studentDTO.getId()).thenReturn(100);
        when(studentDTO.getType()).thenReturn("st");
        when(studentDTO.getSalutation()).thenReturn("Herr");
        when(studentDTO.getTitle()).thenReturn(null);
        when(studentDTO.getFirstName()).thenReturn("Max");
        when(studentDTO.getLastName()).thenReturn("Mustermann");
        when(studentDTO.getAddress()).thenReturn(address);
        when(studentDTO.getAddress().getCity()).thenReturn("Mustermannstraße");
        when(studentDTO.getAddress().getCountry()).thenReturn("DE");
        when(studentDTO.getAddress().getHouseNumber()).thenReturn("2");
        when(studentDTO.getAddress().getCity()).thenReturn("Mustermannstadt");
        when(studentDTO.getAddress().getPostalCode()).thenReturn("12345");
        when(studentDTO.getPhone()).thenReturn("0123456789");
        //TODO Mocking Student Birth LocalDAte Object
        when(studentDTO.getEmail()).thenReturn("max@mustermann.de");
        when(studentDTO.getPassword()).thenReturn("password1234");
        when(studentDTO.getGraduation()).thenReturn("1.1.2000");
        when(studentDTO.getSkills()).thenReturn("Java");
        when(studentDTO.getInterests()).thenReturn("Viele");
        when(studentDTO.getWebsite()).thenReturn("www.mustermann.de");
        when(studentDTO.getDescription()).thenReturn("Mustermann Description");

        studentUser = UserFactory.createStudentUser(studentDTO);
        assertTrue(studentUser instanceof StudentUser ,"Returned type is not a studentUser");

        assertEquals(100 , studentUser.getId(),"ID is not set correctly,should be 100");
        assertEquals("st",studentUser.getType(),"Type is not set correctly,should be st");
        assertEquals("Herr",studentUser.getSalutation(),"Salutation is not set correctly,should be Herr");
        assertNull(studentUser.getTitle(), "Title is not set correctly,should be null");
        assertEquals("Max",studentUser.getFirstName(),"First name is not set correctly,should be Max");
        assertEquals("Mustermann",studentUser.getLastName(),"Last Name is not set correctly,should be Mustermann");
        assertEquals("Mustermannstadt",studentUser.getAddress().getCity());
        assertEquals("DE",studentUser.getAddress().getCountry());
        assertEquals( Integer.valueOf(2),Integer.valueOf(studentUser.getAddress().getHouseNumber()));
        assertEquals("Mustermannstadt",studentUser.getAddress().getCity());
        assertEquals("12345",studentUser.getAddress().getPostalCode());
        assertEquals("0123456789" , studentUser.getPhone(),"Phone is not set correctly,should be 0123456789");
        //TODO Learn how to mock LocalDate
        assertEquals("max@mustermann.de", studentUser.getEmail(),"Email is not set correctly,should be max@mustermann.de");
        assertEquals("password1234",studentUser.getPassword() ,"Password is not set correctly,should be password1234");
        assertEquals("1.1.2000",studentUser.getGraduation(),"Graduation is not set correctly,should be: 1.1.2000");
        assertEquals("Java",studentUser.getSkills(),"Skills is not set correctly,should be:Java");
        assertEquals("Viele",studentUser.getInterests(),"Interests is not set correctly,should be Viele");
        assertEquals("www.mustermann.de",studentUser.getWebsite(),"Website is not set correctly,should be www.mustermann.de");
        assertEquals("Mustermann Description",studentUser.getDescription(),"Description is not set correctly,should be Mustermann Description");



    }

    @Test
    void testAddress() {
        Address address2;
    }

    @Test
    //TODO Create test
    void createUser() {
    }
}