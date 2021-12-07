package org.hbrs.se2.project.coll.repository;

import org.hbrs.se2.project.coll.dtos.CompanyProfileDTO;
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
class CompanyProfileRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CompanyProfileRepository companyProfileRepository;

    @Test
    void findCompanyProfileById() {
        CompanyProfileDTO dto = this.companyProfileRepository.findCompanyProfileById(40000003);
        assertThat(dto.getCompanyName()).isEqualTo("Schuh und Mann GbR");

    }

    @Test
    void findCompanyProfileByCompanyName() {
        CompanyProfileDTO dto = this.companyProfileRepository.findCompanyProfileByCompanyName("Schuh und Mann GbR");
        assertEquals(40000003 , dto.getId());
    }
}