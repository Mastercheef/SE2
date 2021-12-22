package org.hbrs.se2.project.coll.repository;

import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface JobAdvertisementRepository extends JpaRepository<JobAdvertisement, Integer> {

    JobAdvertisement findJobAdvertisementByJobDescription(String string);
    JobAdvertisement findJobAdvertisementById(int id);

    // Filter function ...
    List<JobAdvertisement> findJobAdvertisementsByJobTitleContainsIgnoreCaseAndTypeOfEmploymentContainsIgnoreCaseAndRequirementsContainsIgnoreCase(String title,
                                                                                                                                                     String type,
                                                                                                                                                     String requirements);

    /* Finds all JobAdvertisements from a given CompanyId
    */
    @Query(
            value = "SELECT * FROM collhbrs.col_tab_job_advertisement a WHERE a.contact_person_id = " +
            "(SELECT MAX(user_id) FROM collhbrs.col_tab_contact_person WHERE company_id = :id)",
            nativeQuery = true
    )
    List<JobAdvertisement> findJobAdvertisementsByCompanyId(@Param("id") int id);

}
