package org.hbrs.se2.project.coll.repository;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureEmbeddedDatabase(provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY )
@Sql( {"/schema.sql" , "/data.sql"})
class CompanyRepositoryTest {

    @Autowired
    CompanyRepository companyRepository;

    @Test
    void findCompanyProfileByIDNotNull() {
        assertNotNull(companyRepository.findCompanyProfileById(40000000));
    }


    @Test
    void findCompanyProfileById() {
        assertEquals("Dicker Bubatz" , companyRepository.findCompanyProfileById(40000000).getCompanyName());
        assertEquals("www.bubatz.de" , companyRepository.findCompanyProfileById(40000000).getWebsite());
        assertEquals("12345" , companyRepository.findCompanyProfileById(40000000).getPhoneNumber());
        assertEquals("firma@bubatz.de" , companyRepository.findCompanyProfileById(40000000).getEmail());
        assertEquals("Dicker Bubatz" , companyRepository.findCompanyProfileById(40000000).getDescription());
        assertEquals("12345" , companyRepository.findCompanyProfileById(40000000).getFaxNumber());
        assertEquals(40000000, companyRepository.findCompanyProfileById(40000000).getId());
    }
    @Test
    void findCompanyProfileByIDNull() {
        assertNull(companyRepository.findCompanyProfileById(40999990));
    }



    @Test
    void findCompanyByCompanyNameAndEmailAndWebsiteNotNull() {
        assertNotNull(companyRepository.findCompanyByCompanyNameAndEmailAndWebsite(
                "Dicker Bubatz" , "firma@bubatz.de" , "www.bubatz.de" ));
    }
    @Test
    void findCompanyByCompanyNameAndEmailAndWebsiteID() {
        assertEquals(40000000 , companyRepository.findCompanyByCompanyNameAndEmailAndWebsite(
                "Dicker Bubatz" , "firma@bubatz.de" , "www.bubatz.de" ).getId());
    }

    @Test
    void findCompanyByCompanyNameAndEmailAndWebsiteNull() {
        assertNull(companyRepository.findCompanyByCompanyNameAndEmailAndWebsite(
                "Bubatz" , "firma@bubatz.de" , "www.bubatz.de" ));
        assertNull(companyRepository.findCompanyByCompanyNameAndEmailAndWebsite(
                "Dicker Bubatz" , "firma@bubatz.gmx" , "www.bubatz.de" ));
        assertNull(companyRepository.findCompanyByCompanyNameAndEmailAndWebsite(
                "Dicker Bubatz" , "firma@bubatz.de" , "www.bubatz.com" ));
        assertNull(companyRepository.findCompanyByCompanyNameAndEmailAndWebsite(
                "" , "firma@" , "" ));

    }
}