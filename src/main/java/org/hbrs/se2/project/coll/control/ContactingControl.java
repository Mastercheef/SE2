package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.dtos.RecruitmentAdvertisingDTO;
import org.hbrs.se2.project.coll.entities.JobAdvertisement;
import org.hbrs.se2.project.coll.repository.CompanyRepository;
import org.hbrs.se2.project.coll.repository.JobAdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContactingControl {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private JobAdvertisementRepository jobAdvertisementRepository;

    public String getJobTitle(int id) {
        return jobAdvertisementRepository.findJobAdvertisementById(id).getJobTitle();
    }

    public String getCompanyName(int id) {
        return companyRepository.findCompanyProfileById(id).getCompanyName();
    }
}
