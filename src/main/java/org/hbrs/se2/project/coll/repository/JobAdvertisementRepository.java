package org.hbrs.se2.project.coll.repository;

import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public interface JobAdvertisementRepository extends JpaRepository<JobAdvertisement, Integer> {

    JobAdvertisement findJobAdvertisementById(int id);

    // Filter functions ...
    // All filters
    @Query("select j from JobAdvertisement j where upper(j.jobTitle) like upper(concat('%', ?1, '%')) and " +
            "upper(j.typeOfEmployment) like upper(concat('%', ?2, '%')) and upper(j.requirements) like " +
            "upper(concat('%', ?3, '%')) and j.temporaryEmployment = ?4 and j.startOfWork >= ?5 and " +
            "j.workingHours <= ?6 and j.salary >= ?7 order by j.startOfWork DESC")
    List<JobAdvertisement> filterJobs(String title,String type, String requirements, boolean temporaryEmployment, LocalDate date, short hours, int salary);

    // Title, Type, Requirements only
    @Query("select j from JobAdvertisement j where upper(j.jobTitle) like upper(concat('%', ?1, '%')) and " +
            "upper(j.typeOfEmployment) like upper(concat('%', ?2, '%')) and upper(j.requirements) like " +
            "upper(concat('%', ?3, '%')) and j.startOfWork >= ?4 and j.workingHours <= ?5 and " +
            "j.salary >= ?6 order by j.startOfWork DESC")
    List<JobAdvertisement> filterJobs(String title, String type, String requirements, LocalDate date, short hours, int salary);

    // Title, Requirements, Temporary employment only
    @Query("select j from JobAdvertisement j where upper(j.jobTitle) like upper(concat('%', ?1, '%')) and " +
            "upper(j.requirements) like upper(concat('%', ?2, '%')) and j.temporaryEmployment = ?3 and " +
            "j.startOfWork >= ?4 and j.workingHours <= ?5 and j.salary >= ?6 order by j.startOfWork DESC")
    List<JobAdvertisement> filterJobs(String title, String requirements, boolean temporaryEmployment, LocalDate date, short hours, int salary);

    // Title, Requirements only
    @Query("select j from JobAdvertisement j where upper(j.jobTitle) like upper(concat('%', ?1, '%')) and " +
            "upper(j.requirements) like upper(concat('%', ?2, '%')) and j.startOfWork >= ?3 and " +
            "j.workingHours <= ?4 and j.salary >= ?5 order by j.startOfWork DESC")
    List<JobAdvertisement> filterJobs(String title, String requirements, LocalDate date, short hours, int salary);

    /* Finds all JobAdvertisements from a given CompanyId
    */
    @Query(
            value = "SELECT * FROM collhbrs.col_tab_job_advertisement a WHERE a.contact_person_id = " +
            "(SELECT MAX(user_id) FROM collhbrs.col_tab_contact_person WHERE company_id = :id)",
            nativeQuery = true
    )
    List<JobAdvertisement> findJobAdvertisementsByCompanyId(@Param("id") int id);

}
