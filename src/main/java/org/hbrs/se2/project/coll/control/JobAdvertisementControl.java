package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.control.factories.JobFactory;
import org.hbrs.se2.project.coll.control.factories.MessageFactory;
import org.hbrs.se2.project.coll.dtos.JobAdvertisementDTO;
import org.hbrs.se2.project.coll.dtos.MessageDTO;
import org.hbrs.se2.project.coll.dtos.impl.JobAdvertisementDTOimpl;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.ContactPerson;
import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.hbrs.se2.project.coll.entities.Message;
import org.hbrs.se2.project.coll.repository.JobAdvertisementRepository;
import org.hbrs.se2.project.coll.util.Globals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class JobAdvertisementControl {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobAdvertisementControl.class);

    @Autowired
    JobAdvertisementRepository jobAdvertisementRepository;

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

    public JobAdvertisementDTO createJobDTO(JobAdvertisement jobAdvertisement) {
        return JobFactory.createJobDTO(jobAdvertisement);
    }
}

