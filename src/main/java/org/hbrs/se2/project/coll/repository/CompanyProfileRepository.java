package org.hbrs.se2.project.coll.repository;

import org.hbrs.se2.project.coll.entities.CompanyProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface CompanyProfileRepository extends JpaRepository<CompanyProfile, Integer> {

/*
    @Query(" SELECT c.companyName, c.address, c.telephone, c.email, c.website, c.description, c.offers" +
            " FROM CompanyProfile c, User u" +
            " WHERE c.ID = u.id")




    @Query("  SELECT c.brand, c.model, c.price, u.firstName, u.lastName" +
            " FROM Car c, User u " +
            " WHERE  c.userid = u.id ")
    List<Object[]> findAllCarsAndTheirUsers();

    @Query("  SELECT c.brand, c.model, c.description, c.date, c.phone, c.price " +
            " FROM Car c ")
    List<CarDTO> findAllCarsWithMostImportantValues();

    List<CarDTO> findCarsByDateIsNotNull();*/
}



