package org.hbrs.se2.project.coll.test.control.factories;

import org.hbrs.se2.project.coll.control.factories.UserFactory;
import org.hbrs.se2.project.coll.dtos.impl.StudentUserDTOImpl;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.StudentUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class UserFactoryTest {

    private UserFactory userFactory = null;

    @BeforeEach
    public void setUp() throws Exception {
        userFactory = new UserFactory();
    }

    @Test
    public void testCreateStudentUser() {
        Address address = new Address();
        address.setId(1);
        address.setCountry("Deutschland");
        address.setCity("Bonn");
        address.setPostalCode("12345");
        address.setStreet("Straße");
        address.setHouseNumber("5");

        StudentUserDTOImpl studentUserDTO = new StudentUserDTOImpl();
        studentUserDTO.setId(1);
        studentUserDTO.setSalutation("Herr");
        studentUserDTO.setTitle("Dr.");
        studentUserDTO.setFirstName("Hans");
        studentUserDTO.setLastName("Meier");
        studentUserDTO.setAddress(address);
        studentUserDTO.setDateOfBirth(LocalDate.of(2000, 1, 1));
        studentUserDTO.setPhone("123456789");
        studentUserDTO.setEmail("email@hbrs.de");
        studentUserDTO.setPassword("password");
        studentUserDTO.setSkills("Keine");
        studentUserDTO.setInterests("Interessen");
        studentUserDTO.setWebsite("Website");
        studentUserDTO.setGraduation("Abi");
        studentUserDTO.setDescription("Das bin ich");

        StudentUser studentUser = userFactory.createStudentUser(studentUserDTO);

        assertEquals(studentUserDTO.getId(), studentUser.getId());
        assertEquals(studentUserDTO.getSalutation(), studentUser.getSalutation());
        assertEquals(studentUserDTO.getTitle(), studentUser.getTitle());
        assertEquals(studentUserDTO.getFirstName(), studentUser.getFirstName());
        assertEquals(studentUserDTO.getLastName(), studentUser.getLastName());
        assertEquals(studentUserDTO.getAddress(), studentUser.getAddress());
        assertEquals(studentUserDTO.getDateOfBirth(), studentUser.getDateOfBirth());
        assertEquals(studentUserDTO.getPhone(), studentUser.getPhone());
        assertEquals(studentUserDTO.getEmail(), studentUser.getEmail());
        assertTrue(BCrypt.checkpw(studentUserDTO.getPassword(), studentUser.getPassword()));
        assertEquals(studentUserDTO.getSkills(), studentUser.getSkills());
        assertEquals(studentUserDTO.getInterests(), studentUser.getInterests());
        assertEquals(studentUserDTO.getWebsite(), studentUser.getWebsite());
        assertEquals(studentUserDTO.getGraduation(), studentUser.getGraduation());
        assertEquals(studentUserDTO.getDescription(), studentUser.getDescription());
    }
}
