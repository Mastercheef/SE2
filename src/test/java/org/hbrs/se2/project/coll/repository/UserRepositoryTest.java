package org.hbrs.se2.project.coll.repository;

import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    private UserDTO userDTO;

    @Test
    void findUserByIdAndPassword() {
        userDTO = userRepository.findUserByIdAndPassword(20000012 , "abc123");
        assertEquals("judithmeier@company.com" , userDTO.getEmail());
    }

    @Test
    void findUserByEmailAndPassword() {
        userDTO = userRepository.findUserByEmailAndPassword("judithmeier@company.com" , "abc123");
        assertEquals(20000012 , userDTO.getId());
    }

    @Test
    void findUserByEmail() {
        userDTO = userRepository.findUserByEmail("knutmut@gmail.com");
        assertEquals(20000001 , userDTO.getId());
    }

    @Test
    void findUserById() {
        userDTO = userRepository.findUserById(20000016);
        assertEquals("konich@gmail.com" , userDTO.getEmail());
    }
}