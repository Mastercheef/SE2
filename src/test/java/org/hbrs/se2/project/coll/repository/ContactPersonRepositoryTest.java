package org.hbrs.se2.project.coll.repository;

import org.hbrs.se2.project.coll.entities.ContactPerson;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@AutoConfigureTestEntityManager
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ContactPersonRepositoryTest {


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ContactPersonRepository contactPersonRepository;

    private ContactPerson contactPerson;
    @Test
    void findContactPersonById() {
        contactPerson = contactPersonRepository.findContactPersonById(20000012);
        assertEquals("HR Department" , contactPerson.getRole());
    }

    @Test
    void findContactPersonByCompany_Id() {
        contactPerson = contactPersonRepository.findContactPersonByCompany_Id(40000003);
        assertEquals("CEO und Vorstand" , contactPerson.getRole());
    }
}