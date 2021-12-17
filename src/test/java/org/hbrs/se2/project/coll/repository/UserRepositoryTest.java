package org.hbrs.se2.project.coll.repository;

import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    private final int userId = 20000000;
    private UserDTO userDTO;

    @Test
    void findUserByEmail() {
        userDTO = userRepository.findUserByEmail("email@hbrs.de");
        assertEquals(userId, userDTO.getId());
    }

    @Test
    void findUserById() {
        userDTO = userRepository.findUserById(userId);
        assertEquals("email@hbrs.de" , userDTO.getEmail());
    }

    @Test
    void passwordIsSavedEncrypted() {
        Optional<User> wrapper = userRepository.findById(userId);
        assertTrue(wrapper.isPresent(), "The required Record is not in the Database!");
        User tmp = wrapper.get();
        tmp.setPassword("password");
        assertTrue(BCrypt.checkpw("password", tmp.getPassword()), "setPassword() does not encrypt!");
        assertTrue(BCrypt.checkpw("password", userRepository.findById(userId).get().getPassword()));
    }
}