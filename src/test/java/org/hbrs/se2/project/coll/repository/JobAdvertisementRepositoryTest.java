package org.hbrs.se2.project.coll.repository;

import org.hbrs.se2.project.coll.entities.ContactPerson;
import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.hbrs.se2.project.coll.repository.JobAdvertisementRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class JobAdvertisementRepositoryTest {

    @Autowired
    private JobAdvertisementRepository repository;

    @Test
    void findJobAdvertisementsByCompanyId() {
        List<JobAdvertisement> jobAdvertisement = repository.findJobAdvertisementsByCompanyId(40000000);

        for(JobAdvertisement job : jobAdvertisement) {
            assertNotNull(job);
            System.out.println(job.getJobTitle());
        }
    }

    @Test
    void findJobAdvertisementById() {
        JobAdvertisement job = repository.findJobAdvertisementByJobDescription("Praktikum als Webdesigner.");
        short hours = 20;
        assertNotNull(job);
        assertEquals(job.getJobTitle(), "Praktikum im Bereich Webdesign");
        assertEquals(job.getTypeOfEmployment(), "Praktikum");
        assertEquals(job.getWorkingHours(), hours);
        assertEquals(job.getRequirements(), "Kenntnisse in HTML, JS, CSS");
    }

}
