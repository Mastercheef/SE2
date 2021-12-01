package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.factories.UserFactory;
import org.hbrs.se2.project.coll.dtos.StudentUserDTO;
import org.hbrs.se2.project.coll.entities.Address;
import org.hbrs.se2.project.coll.entities.StudentUser;
import org.hbrs.se2.project.coll.repository.AddressRepository;
import org.hbrs.se2.project.coll.repository.StudentUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentProfileControl {

    @Autowired
    private StudentUserRepository repository;

    @Autowired
    private AddressRepository addressRepository;

    public StudentUserDTO loadProfileDataById(int id) {
        return repository.findStudentUserById(id);
    }

    //TODO: ResultDTO mit Rückmeldung für View bei Fehler
    public void saveStudentUser( StudentUserDTO userDTO ) {
        try {
            // Hier könnte man noch die Gültigkeit der Daten überprüfen
            // check( userDTO );

            //Erzeuge ein neues Car-Entity konsistent über eine Factory
            StudentUser studentUser = UserFactory.createStudentUser(userDTO);
            // Prüfen, ob eingetragene Adresse bereits als Datensatz vorhanden ist. Wenn ja, wird Datensatz der Adresse
            // aus DB geholt und der erzeugtzen Entity zugewiesen
            studentUser.setAddress(this.checkAddressExistence(studentUser.getAddress()));

            // Abspeicherung des Entity in die DB
            this.repository.save( studentUser );

            if (studentUser.getId() > 0)
                System.out.println("Updated StudentUser profile: " + studentUser.getId());
            else
                System.out.println("Created new StudentUser: " + studentUser.getId());
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
}
