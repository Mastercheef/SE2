package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.factories.CompanyProfileFactory;
import org.hbrs.se2.project.coll.control.factories.UserFactory;
import org.hbrs.se2.project.coll.dtos.CompanyProfileDTO;
import org.hbrs.se2.project.coll.dtos.StudentUserDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.CompanyProfile;
import org.hbrs.se2.project.coll.entities.StudentUser;
import org.hbrs.se2.project.coll.repository.AddressRepository;
import org.hbrs.se2.project.coll.repository.CompanyProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompanyProfileControl {

    @Autowired
    private CompanyProfileRepository repository;

    @Autowired
    private AddressRepository addressRepository;

    public CompanyProfileDTO loadCompanyProfileDataById(int id) {
        return repository.findCompanyProfileById(id);
    }

    //TODO: ResultDTO mit Rückmeldung für View bei Fehler
    public void saveCompanyProfile( CompanyProfileDTO userDTO ) {
        try {
            CompanyProfile companyProfile = CompanyProfileFactory.createCompanyProfile(userDTO);

            // Prüfen, ob eingetragene Adresse bereits als Datensatz vorhanden ist. Wenn ja, wird Datensatz der Adresse
            // aus DB geholt und der erzeugten Entity zugewiesen
            companyProfile.setAddress(this.checkAddressExistence(companyProfile.getAddress()));

            // Abspeicherung der Entity in die DB
            this.repository.save( companyProfile );

            if (companyProfile.getId() > 0)
                System.out.println("Updated Company profile: " + companyProfile.getId());
            else
                System.out.println("Created new CompanyProfile: " + companyProfile.getId());
        } catch (Error error) {
            // return resultdto mit Fehler
        }
    }

    public Address checkAddressExistence(Address address) {
        Address existingAddress = addressRepository.getAddressByStreetAndHouseNumberAndPostalCodeAndCityAndCountry(
                address.getStreet(),
                address.getHouseNumber(),
                address.getPostalCode(),
                address.getCity(),
                address.getCountry());

        if (existingAddress != null && existingAddress.getId() > 0) {
            System.out.println("Verwende existierende Adresse mit ID: " + existingAddress.getId());
            return existingAddress;
        } else {
            System.out.println("Speichere neue Adresse in DB...");
            Address newAddress = addressRepository.save(address);
            System.out.println("Neue Adresse angelegt mit ID: " + newAddress.getId());
            return newAddress;
        }
    }

    public CompanyProfileDTO findCompanyProfileByCompanyId(int id) {
        return repository.findCompanyProfileById(id);
    }

}

