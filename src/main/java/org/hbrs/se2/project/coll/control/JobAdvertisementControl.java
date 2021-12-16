package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.factories.JobFactory;
import org.hbrs.se2.project.coll.dtos.RecruitmentAdvertisingDTO;
import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.hbrs.se2.project.coll.repository.JobAdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobAdvertisementControl {

    @Autowired
    JobAdvertisementRepository jobAdvertisementRepository;

    public void saveAdvertisement(RecruitmentAdvertisingDTO dto) {
        try {
            JobAdvertisement job = JobFactory.createJob(dto);
            this.jobAdvertisementRepository.save(job);
        } catch (Error error) {
            // TODO: Return resultDTO mit Fehler (return RegistrationResultDTO)
        }


    }

}

