package org.hbrs.se2.project.coll.repository;

import org.hbrs.se2.project.coll.dtos.CompanyDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@AutoConfigureTestEntityManager
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CompanyRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    void findCompanyProfileById() {
        CompanyDTO dto = this.companyRepository.findCompanyProfileById(40000000);
        assertThat(dto.getCompanyName()).isEqualTo("Erfolgreiche Testfirma GmbH");

    }

    @Test
    void findCompanyProfileByCompanyName() {
        CompanyDTO dto = this.companyRepository.findCompanyProfileByCompanyName("Erfolgreiche Testfirma GmbH");
        assertEquals(40000000 , dto.getId());
    }
}