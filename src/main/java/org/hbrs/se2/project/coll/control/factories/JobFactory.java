package org.hbrs.se2.project.coll.control.factories;

import org.hbrs.se2.project.coll.dtos.RecruitmentAdvertisingDTO;
import org.hbrs.se2.project.coll.entities.JobAdvertisement;

public class JobFactory {

    private JobFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static JobAdvertisement createJob(RecruitmentAdvertisingDTO dto) {
        JobAdvertisement jobAdvertisement = new JobAdvertisement();
        jobAdvertisement.setId(dto.getId());
        jobAdvertisement.setTemporaryEmployment(dto.getTemporaryEmployment());
        jobAdvertisement.setTypeOfEmployment(dto.getTypeOfEmployment());
        jobAdvertisement.setWorkingHours(dto.getWorkingHours());
        jobAdvertisement.setRequirements(dto.getRequirements());
        jobAdvertisement.setWorkingLocation(dto.getAddress());
        jobAdvertisement.setStartOfWork(dto.getStartOfWork());
        jobAdvertisement.setEndOfWork(dto.getEndOfWork());
        jobAdvertisement.setContactPerson(dto.getContactPerson());
        jobAdvertisement.setJobDescription(dto.getJobDescription());
        jobAdvertisement.setJobTitle(dto.getJobTitle());

        return jobAdvertisement;
    }
}
