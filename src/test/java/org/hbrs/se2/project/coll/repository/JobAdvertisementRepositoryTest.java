package org.hbrs.se2.project.coll.repository;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureEmbeddedDatabase(provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY )
@Sql( {"/schema.sql" , "/data.sql"})
class JobAdvertisementRepositoryTest {

    private static final String JOB_TITLE = "Persoenliche Assistentin";
    private static final String TYPE_OF_EMPLOYMENT = "Vollzeit";
    private static final String REQUIREMENTS = "Alles";
    private static final LocalDate START_OF_WORK = LocalDate.of(2021,12,1);
    private static final LocalDate END_OF_WORK = LocalDate.of(2024,1,14);
    private static final short WORKING_HOURS = 60;
    private static final int SALARY = 10;

    @Autowired
    JobAdvertisementRepository jobAdvertisementRepository;

    @Test
    void findJobAdvertisementByIdNotNull() {
        assertNotNull(        jobAdvertisementRepository.findJobAdvertisementById(30000000));
    }
    @Test
    void findJobAdvertisementByIdNull() {
        assertNull(        jobAdvertisementRepository.findJobAdvertisementById(30002220));
    }

    @Test
    void findJobAdvertisementByIde() {
        assertEquals(JOB_TITLE,   jobAdvertisementRepository.findJobAdvertisementById(30000000).getJobTitle());
        assertEquals(TYPE_OF_EMPLOYMENT,   jobAdvertisementRepository.findJobAdvertisementById(30000000).getTypeOfEmployment());
        assertEquals(REQUIREMENTS,   jobAdvertisementRepository.findJobAdvertisementById(30000000).getRequirements());
        assertEquals(false,   jobAdvertisementRepository.findJobAdvertisementById(30000000).getTemporaryEmployment());
        assertEquals(START_OF_WORK,   jobAdvertisementRepository.findJobAdvertisementById(30000000).getStartOfWork());
        assertEquals(END_OF_WORK,   jobAdvertisementRepository.findJobAdvertisementById(30000000).getEndOfWork());
        assertEquals(WORKING_HOURS,   jobAdvertisementRepository.findJobAdvertisementById(30000000).getWorkingHours());
        assertEquals(SALARY,   jobAdvertisementRepository.findJobAdvertisementById(30000000).getSalary());

    }

    @Test
    void filterJobsemptyList() {
        assertTrue(
                jobAdvertisementRepository.filterJobs("","","" ,true , LocalDate.of(2000,1,2) ,(short)40 ,2999).size() == 0);
    }

    @Test
    void filterJobsNotemptyList() {
        assertTrue(jobAdvertisementRepository.filterJobs(
                JOB_TITLE,TYPE_OF_EMPLOYMENT,REQUIREMENTS ,false , START_OF_WORK ,WORKING_HOURS ,SALARY).size() == 1);
    }


    @Test
    void testFilterJobsListNotEmpty() {
        assertFalse(jobAdvertisementRepository.filterJobs(JOB_TITLE , TYPE_OF_EMPLOYMENT , REQUIREMENTS ,  START_OF_WORK , WORKING_HOURS , SALARY).isEmpty());
    }

    @Test
    void testFilterJobsEmpty() {
        assertTrue(jobAdvertisementRepository.filterJobs("Koch" , TYPE_OF_EMPLOYMENT , REQUIREMENTS ,  START_OF_WORK , WORKING_HOURS , SALARY).isEmpty());
    }

    @Test
    void testFilterJobs1NotEmpty() {
        assertFalse(jobAdvertisementRepository.filterJobs(
                JOB_TITLE , REQUIREMENTS , false ,  START_OF_WORK ,  WORKING_HOURS , SALARY).isEmpty());
    }

    @Test
    void testFilterJobs1Empty() {
        assertTrue(jobAdvertisementRepository.filterJobs(
                "Koch" , REQUIREMENTS , false ,  START_OF_WORK ,  WORKING_HOURS , SALARY).isEmpty());
    }



    @Test
    void findJobAdvertisementsByCompanyIdNotEmpty() {
        assertFalse(jobAdvertisementRepository.findJobAdvertisementsByCompanyId(40000000).isEmpty());
    }

    @Test
    void findJobAdvertisementsByCompanyIdEmpty() {
        assertTrue(jobAdvertisementRepository.findJobAdvertisementsByCompanyId(40000011).isEmpty());
    }
}