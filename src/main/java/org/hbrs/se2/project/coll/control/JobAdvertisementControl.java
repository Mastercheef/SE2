package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.control.factories.JobFactory;
import org.hbrs.se2.project.coll.dtos.JobAdvertisementDTO;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.hbrs.se2.project.coll.repository.CompanyRepository;
import org.hbrs.se2.project.coll.repository.ContactPersonRepository;
import org.hbrs.se2.project.coll.repository.JobAdvertisementRepository;
import org.hbrs.se2.project.coll.util.Globals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class JobAdvertisementControl {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobAdvertisementControl.class);

    @Autowired
    JobAdvertisementRepository jobAdvertisementRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ContactPersonRepository contactPersonRepository;

    public void saveAdvertisement(JobAdvertisementDTO dto) throws DatabaseUserException {
        try {
            JobAdvertisement job = JobFactory.createJob(dto);
            this.jobAdvertisementRepository.save(job);
        } catch (Exception exception) {
            LOGGER.info(Globals.LogMessage.LOG, exception.toString());
            if (exception instanceof org.springframework.dao.DataAccessResourceFailureException) {
                throw new DatabaseUserException(Globals.LogMessage.CONNECTED);
            } else {
                throw new DatabaseUserException(Globals.LogMessage.ERROR);
            }
        }
    }

    // Löschen eines Stellenangebots aus der Datenbank
    public void deleteAdvertisement(JobAdvertisement jobAdvertisement) throws DatabaseUserException {
        try {
            this.jobAdvertisementRepository.delete(jobAdvertisement);
        } catch (Exception exception) {
            LOGGER.info(Globals.LogMessage.LOG, exception.toString());
            if (exception instanceof org.springframework.dao.DataAccessResourceFailureException) {
                throw new DatabaseUserException(Globals.LogMessage.CONNECTED);
            } else {
                throw new DatabaseUserException(Globals.LogMessage.ERROR);
            }
        }
    }

    public JobAdvertisement getJob(int jobId) {
        return jobAdvertisementRepository.findJobAdvertisementById(jobId);
    }

    public JobAdvertisementDTO createJobDTO(JobAdvertisement jobAdvertisement) {
        return JobFactory.createJobDTO(jobAdvertisement);
    }

    public String getCompanyName(JobAdvertisement jobAdvertisement) {
        int companyId = getCompanyId(jobAdvertisement);
        return companyRepository.findCompanyProfileById(companyId).getCompanyName();
    }

    public Address getCompanyAddress(JobAdvertisement jobAdvertisement) {
        int companyId = getCompanyId(jobAdvertisement);
        return companyRepository.findCompanyProfileById(companyId).getAddress();
    }

    public String getCompanyPhoneNumber(JobAdvertisement jobAdvertisement) {
        int companyId = getCompanyId(jobAdvertisement);
        return companyRepository.findCompanyProfileById(companyId).getPhoneNumber();
    }

    public String getContactPersonName(JobAdvertisement jobAdvertisement) {
        int companyId = getCompanyId(jobAdvertisement);
        return (contactPersonRepository.findContactPersonByCompanyId(companyId).getFirstName() + " " +
                contactPersonRepository.findContactPersonByCompanyId(companyId).getLastName());
    }

    public String getContactPersonEmail(JobAdvertisement jobAdvertisement) {
        int companyId = getCompanyId(jobAdvertisement);
        return contactPersonRepository.findContactPersonByCompanyId(companyId).getEmail();
    }

    public int getCompanyId(JobAdvertisement jobAdvertisement) {
        return contactPersonRepository.findContactPersonById(jobAdvertisement.getContactPerson().
                getId()).getCompany().getId();
    }

    // Used to find all available Jobs and filter them by entry date
    public List<JobAdvertisement> getAllJobs() {
        return jobAdvertisementRepository.findAll(Sort.by(Sort.Direction.DESC, "startOfWork"));

    }

    /* We need multiple filter functions because of Vaadin Combo Boxes. */

    // All filters set
    public List<JobAdvertisement> filterJobs(String title, String type, String requirements,
                                             boolean temporaryEmployment, LocalDate date) {
        return jobAdvertisementRepository.findJobAdvertisementsByJobTitleContainsIgnoreCaseAndTypeOfEmploymentContainsIgnoreCaseAndRequirementsContainsIgnoreCaseAndTemporaryEmploymentAndStartOfWorkIsGreaterThanEqualOrderByStartOfWorkDesc(title, type, requirements, temporaryEmployment, date);
    }

    // Only "JobType" filter set
    public List<JobAdvertisement> filterJobs(String title, String type, String requirements, LocalDate date) {
        return jobAdvertisementRepository.findJobAdvertisementsByJobTitleContainsIgnoreCaseAndTypeOfEmploymentContainsIgnoreCaseAndRequirementsContainsIgnoreCaseAndStartOfWorkIsGreaterThanEqualOrderByStartOfWorkDesc(title, type, requirements, date);
    }

    // Only "Temporary Employment" filter set
    public List<JobAdvertisement> filterJobs(String title, String requirements, boolean temporaryEmployment, LocalDate date) {
        return jobAdvertisementRepository.findJobAdvertisementsByJobTitleContainsIgnoreCaseAndRequirementsContainsIgnoreCaseAndTemporaryEmploymentAndStartOfWorkIsGreaterThanEqualOrderByStartOfWorkDesc(title, requirements, temporaryEmployment, date);
    }

    // "Job Type" and "Temporary Employment" filters not set
    public List<JobAdvertisement> filterJobs(String title, String requirements, LocalDate date) {
        return jobAdvertisementRepository.findJobAdvertisementsByJobTitleContainsIgnoreCaseAndRequirementsContainsIgnoreCaseAndStartOfWorkIsGreaterThanEqualOrderByStartOfWorkDesc(title, requirements, date);
    }

}

