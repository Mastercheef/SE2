package org.hbrs.se2.project.coll.repository;

import org.hbrs.se2.project.coll.dtos.JobApplicationDTO;
import org.hbrs.se2.project.coll.entities.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface JobApplicationRepository extends JpaRepository<JobApplication, Integer> {

    JobApplicationDTO findJobApplicationById(int id);


}
