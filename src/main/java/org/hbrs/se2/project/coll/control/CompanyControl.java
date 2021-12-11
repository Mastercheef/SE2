package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.factories.CompanyFactory;
import org.hbrs.se2.project.coll.dtos.CompanyDTO;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.Company;
import org.hbrs.se2.project.coll.repository.AddressRepository;
import org.hbrs.se2.project.coll.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompanyControl {

    @Autowired
    private CompanyRepository repository;
    @Autowired
    private AddressControl addressControl;

    public CompanyDTO loadCompanyProfileDataById(int id) {
        return repository.findCompanyProfileById(id);
    }

    //TODO: ResultDTO mit Rückmeldung für View bei Fehler
    public Company saveCompany(CompanyDTO companyDTO ) {
        try {
            Company company = CompanyFactory.createCompany(companyDTO);

            // Prüfen, ob eingetragene Adresse bereits als Datensatz vorhanden ist. Wenn ja, wird Datensatz der Adresse
            // aus DB geholt und der erzeugten Entity zugewiesen
            company.setAddress(addressControl.checkAddressExistence(company.getAddress()));

            // Abspeicherung der Entity in die DB
            Company savedCompany = this.repository.save( company );

            if (company.getId() > 0)
                System.out.println("Updated Company profile: " + company.getId());
            else
                System.out.println("Created new CompanyProfile: " + company.getId());

            return savedCompany;
        } catch (Error error) {
            // return resultdto mit Fehler
            return null;
        }
    }

    public CompanyDTO findCompanyProfileByCompanyId(int id) {
        return repository.findCompanyProfileById(id);
    }

}

