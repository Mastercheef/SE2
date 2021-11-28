package org.hbrs.se2.project.coll.test;

import org.hbrs.se2.project.coll.entities.ContactPerson;
import org.hbrs.se2.project.coll.repository.ContactPersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CompanyProfileViewTest {
    @Autowired
    private ContactPersonRepository contactPersonRepository;

    @Test
    void getContactPersonByUserId() {
        ContactPerson contactPerson;
        contactPerson = contactPersonRepository.findContactPersonById(20000012);

        assertEquals(contactPerson.getFirstName(), "Judith");
        assertEquals(contactPerson.getLastName(), "Meier");
        assertEquals(contactPerson.getPhone(), "1122334455");
        assertEquals(contactPerson.getEmail(), "judithmeier@company.com");
    }

    @Test
    void getContactPersonByCompanyId() {
        ContactPerson contactPerson;
        contactPerson = contactPersonRepository.findContactPersonByCompany_Id(40000000);

        assertEquals(contactPerson.getFirstName(), "Judith");
        assertEquals(contactPerson.getLastName(), "Meier");
        assertEquals(contactPerson.getPhone(), "1122334455");
        assertEquals(contactPerson.getEmail(), "judithmeier@company.com");
    }
}
