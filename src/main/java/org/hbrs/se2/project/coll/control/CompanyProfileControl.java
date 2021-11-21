package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.factories.CompanyProfileFactory;
import org.hbrs.se2.project.coll.dtos.CompanyProfileDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.CompanyProfile;
import org.hbrs.se2.project.coll.entities.User;
import org.hbrs.se2.project.coll.repository.CompanyProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompanyProfileControl {

    @Autowired
    private CompanyProfileRepository repository;

//    private CompanyProfileDTO companyProfileDTO = null;

    public void createCompanyProfile(CompanyProfileDTO companyProfileDTO, UserDTO userDTO) {

        // Erzeuge neue CompanyProfile-Entity über Factory
        CompanyProfile companyProfileEntity = CompanyProfileFactory.createCompanyProfile(companyProfileDTO, userDTO);

        // Abspeicherung der Entity in DB
        this.repository.save(companyProfileEntity);
    }
/*
    public CompanyProfileDTO findCompanyProfileByCompanyName() {
        return repository.findCompanyProfileByCompanyName()
    }*/

}

