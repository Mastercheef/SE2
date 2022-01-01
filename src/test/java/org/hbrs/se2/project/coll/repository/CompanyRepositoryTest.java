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


    private static final String COMPANY_NAME = "Dicker Bubatz";
    private static final String WEBSITE = "www.bubatz.de";
    private static final String PHONENUMBER = "12345";
    private static final String E_MAIL = "firma@bubatz.de";
    private static final String FAX_NUMBER = "12345";
    private static final int ID = 40000000;

    @Test
    void findCompanyProfileByIDNotNull() {
        assertNotNull(companyRepository.findCompanyProfileById(40000000));
    }


    @Test
    void findCompanyProfileById() {
        assertEquals(COMPANY_NAME , companyRepository.findCompanyProfileById(40000000).getCompanyName());
        assertEquals(WEBSITE , companyRepository.findCompanyProfileById(40000000).getWebsite());
        assertEquals(PHONENUMBER , companyRepository.findCompanyProfileById(40000000).getPhoneNumber());
        assertEquals(E_MAIL , companyRepository.findCompanyProfileById(40000000).getEmail());
        assertEquals(COMPANY_NAME , companyRepository.findCompanyProfileById(40000000).getDescription());
        assertEquals(FAX_NUMBER , companyRepository.findCompanyProfileById(40000000).getFaxNumber());
        assertEquals(ID, companyRepository.findCompanyProfileById(40000000).getId());
    }
    @Test
    void findCompanyProfileByIDNull() {
        assertNull(companyRepository.findCompanyProfileById(40999990));
    }



    @Test
    void findCompanyByCompanyNameAndEmailAndWebsiteNotNull() {
        assertNotNull(companyRepository.findCompanyByCompanyNameAndEmailAndWebsite(
                COMPANY_NAME , E_MAIL , WEBSITE ));
    }
    @Test
    void findCompanyByCompanyNameAndEmailAndWebsiteID() {
        assertEquals(ID , companyRepository.findCompanyByCompanyNameAndEmailAndWebsite(
                COMPANY_NAME , E_MAIL , WEBSITE ).getId());
    }

    @Test
    void findCompanyByCompanyNameAndEmailAndWebsiteNull() {
        assertNull(companyRepository.findCompanyByCompanyNameAndEmailAndWebsite(
                "Bubatz" , E_MAIL , WEBSITE ));
        assertNull(companyRepository.findCompanyByCompanyNameAndEmailAndWebsite(
                COMPANY_NAME , "firma@bubatz.gmx" , WEBSITE ));
        assertNull(companyRepository.findCompanyByCompanyNameAndEmailAndWebsite(
                COMPANY_NAME , E_MAIL , "www.bubatz.com" ));
        assertNull(companyRepository.findCompanyByCompanyNameAndEmailAndWebsite(
                "" , "firma@" , "" ));

    }
}