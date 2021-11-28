package org.hbrs.se2.project.coll.test;

import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.hbrs.se2.project.coll.repository.JobAdvertisementRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class JobAdvertisementRepositoryTest {

    @Autowired
    private JobAdvertisementRepository repository;

    @Test
    void findJobAdvertisementsByCompanyId() {
        List<JobAdvertisement> jobAdvertisement = repository.findJobAdvertisementsByCompanyId(40000000);
        assertNotNull(jobAdvertisement);
    }

}
