package org.hbrs.se2.project.coll.repository;

import org.hbrs.se2.project.coll.dtos.CompanyProfileDTO;
import org.hbrs.se2.project.coll.entities.CompanyProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface CompanyProfileRepository extends JpaRepository<CompanyProfile, Integer> {

    // TODO: Fehler beseitigen
    /* Ja */
    //List<CompanyProfileDTO> getUserByOccupation(String occupation );
    //List<CompanyProfileDTO>
   // CompanyProfileDTO findCompanyProfileByCompanyName(String name);


   // CompanyProfileDTO findUserByUserID(String ID);

}



