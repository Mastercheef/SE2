package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.factories.JobFactory;
import org.hbrs.se2.project.coll.dtos.JobApplicationDTO;
import org.hbrs.se2.project.coll.dtos.JobApplicationResultDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.dtos.impl.JobApplicationResultDTOImpl;
import org.hbrs.se2.project.coll.entities.ContactPerson;
import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.hbrs.se2.project.coll.entities.JobApplication;
import org.hbrs.se2.project.coll.repository.JobApplicationRepository;
import org.hbrs.se2.project.coll.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JobApplicationControl {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobApplicationControl.class);

    @Autowired
    JobApplicationRepository jobApplicationRepository;
    @Autowired
    private ContactPersonControl contactPersonControl;
    @Autowired
    private JobAdvertisementControl jobAdvertisementControl;

    private JobApplicationResultDTOImpl applicationResultDTO;
    private JobApplicationDTO jobApplicationDTO;

    public JobApplicationResultDTO createJobApplication(JobApplicationDTO jobApplicationDTO) {
        try {
            this.applicationResultDTO = new JobApplicationResultDTOImpl();
            this.jobApplicationDTO = jobApplicationDTO;

            checkForRequiredApplicationInput();

            if (applicationResultDTO.getReasons().isEmpty()) {

                JobApplication savedApplication = jobApplicationRepository.save(JobFactory.createJobApplication(jobApplicationDTO));

                applicationResultDTO.setApplicationID(savedApplication.getId());
                applicationResultDTO.setResult(true);
                applicationResultDTO.addReason(JobApplicationResultDTO.ReasonType.SUCCESS);
            } else {
                applicationResultDTO.setResult(false);
            }

        } catch (Exception exception) {
            LOGGER.info("LOG : {}" , exception.getMessage());
            applicationResultDTO.setResult(false);
            applicationResultDTO.addReason(JobApplicationResultDTO.ReasonType.UNEXPECTED_ERROR);
            throw exception;
        }
        return applicationResultDTO;
    }

    protected void checkForRequiredApplicationInput() {
        checkValueAndSetResponse(jobApplicationDTO.getHeadline(), JobApplicationResultDTO.ReasonType.HEADLINE_MISSING);
        checkValueAndSetResponse(jobApplicationDTO.getText(), JobApplicationResultDTO.ReasonType.TEXT_MISSING);
    }

    protected void checkValueAndSetResponse(String value, JobApplicationResultDTO.ReasonType reason){
        if(Utils.stringIsEmptyOrNull(value)) {
            applicationResultDTO.addReason(reason);
        }
    }

    public JobApplicationDTO loadJobApplication(int applicationID) {
        this.jobApplicationDTO = jobApplicationRepository.findJobApplicationById(applicationID);
        return this.jobApplicationDTO;
    }

    public List<JobApplicationDTO> loadJobApplicationsFromCompany(UserDTO user) {
        ContactPerson contactPerson = contactPersonControl.findContactPersonById(user.getId());
        List<JobAdvertisement> jobAdds = jobAdvertisementControl.getJobsByCompanyId(contactPerson.getCompany().getId());
        List<JobApplicationDTO> jobApplications = new ArrayList<>();
        for(JobAdvertisement jobAdd : jobAdds) {
            jobApplications.addAll(jobApplicationRepository.findJobApplicationsByJobAdvertisement(jobAdd));
        }
        return jobApplications;
    }

    public boolean isUserAllowedToAccessJobApplications(UserDTO user) {

        if (user.getType().equals("st")) {
            return this.jobApplicationDTO.getStudentUser().getId() == user.getId();
        } else {
            return this.jobApplicationDTO.getJobAdvertisement().getContactPerson().getCompany().getId() ==
                    getContactPersonFromSessionUser(user).getCompany().getId();
        }

    }

    public ContactPerson getContactPersonFromSessionUser(UserDTO user) {
        return contactPersonControl.findContactPersonById(user.getId());
    }
}
