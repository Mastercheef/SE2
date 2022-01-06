package org.hbrs.se2.project.coll.control.factories;

import org.hbrs.se2.project.coll.dtos.JobAdvertisementDTO;
import org.hbrs.se2.project.coll.dtos.JobApplicationDTO;
import org.hbrs.se2.project.coll.dtos.impl.JobAdvertisementDTOimpl;
import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.hbrs.se2.project.coll.entities.JobApplication;

public class JobFactory {

    private JobFactory() {
        throw new IllegalStateException("Factory Class");
    }

    public static JobAdvertisement createJob(JobAdvertisementDTO dto) {
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
        jobAdvertisement.setSalary(dto.getSalary());

        return jobAdvertisement;
    }

    public static JobAdvertisementDTO createJobDTO(JobAdvertisement jobAdvertisement) {
        JobAdvertisementDTOimpl jobAdvertisementDTOimpl = new JobAdvertisementDTOimpl();
        jobAdvertisementDTOimpl.setId(jobAdvertisement.getId());
        jobAdvertisementDTOimpl.setTemporaryEmployment(jobAdvertisement.getTemporaryEmployment());
        jobAdvertisementDTOimpl.setTypeOfEmployment(jobAdvertisement.getTypeOfEmployment());
        jobAdvertisementDTOimpl.setWorkingHours(jobAdvertisement.getWorkingHours());
        jobAdvertisementDTOimpl.setRequirements(jobAdvertisement.getRequirements());
        jobAdvertisementDTOimpl.setAddress(jobAdvertisement.getWorkingLocation());
        jobAdvertisementDTOimpl.setStartOfWork(jobAdvertisement.getStartOfWork());
        jobAdvertisementDTOimpl.setEndOfWork(jobAdvertisement.getEndOfWork());
        jobAdvertisementDTOimpl.setContactPerson(jobAdvertisement.getContactPerson());
        jobAdvertisementDTOimpl.setJobDescription(jobAdvertisement.getJobDescription());
        jobAdvertisementDTOimpl.setJobTitle(jobAdvertisement.getJobTitle());
        jobAdvertisementDTOimpl.setSalary(jobAdvertisement.getSalary());
        return jobAdvertisementDTOimpl;
    }

    public static JobApplication createJobApplication(JobApplicationDTO dto) {
        JobApplication newJobApplication = new JobApplication();
        newJobApplication.setStudentUser(dto.getStudentUser());
        newJobApplication.setJobAdvertisement(dto.getJobAdvertisement());
        newJobApplication.setHeadline(dto.getHeadline());
        newJobApplication.setText(dto.getText());
        newJobApplication.setDate(dto.getDate());

        return newJobApplication;
    }
}
