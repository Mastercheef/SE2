package org.hbrs.se2.project.coll.repository;

import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class JobAdvertisementRepositoryTest {

    @Autowired
    private JobAdvertisementRepository repository;

    @Test
    void findJobAdvertisementsByCompanyId() {
        List<JobAdvertisement> jobAdvertisement = repository.findJobAdvertisementsByCompanyId(40000000);

        for(JobAdvertisement job : jobAdvertisement) {
            assertNotNull(job);
        }
    }

    @Test
    void findJobAdvertisementById() {
        JobAdvertisement job = repository.findJobAdvertisementByJobDescription("Gesucht wird ein Student zum Neuaufsetzen unserer Webseite. Sollte über die angegebenen Skills verfügen. Bachelor wie auch Master willkommen.");
        short hours = 16;
        assertNotNull(job);
        assertEquals("Web Engineer", job.getJobTitle());
        assertEquals("Praktikum", job.getTypeOfEmployment());
        assertEquals(hours, job.getWorkingHours());
        assertEquals("HTML, CSS, JavaScript, TypeScript", job.getRequirements());
    }

}
