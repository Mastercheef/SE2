package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.control.exceptions.DatabaseUserException;
import org.hbrs.se2.project.coll.control.factories.UserFactory;
import org.hbrs.se2.project.coll.dtos.ContactPersonDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.*;
import org.hbrs.se2.project.coll.repository.ContactPersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContactPersonControl {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactPersonControl.class);

    @Autowired
    private ContactPersonRepository contactPersonRepository;
    @Autowired
    private AddressControl addressControl;

    public ContactPerson createNewContactPerson(UserDTO userDTO, Company company) throws DatabaseUserException {
        Address address = handleAddressExistance(userDTO.getAddress());
        ContactPerson newContactPerson = UserFactory.createContactPersonFromBasicUser(userDTO);
        newContactPerson.setAddress(address);
        // In the registration process of a contact person, a new company must be created.
        newContactPerson.setCompany(company);
        newContactPerson.setRole("admin");

        Settings settings = new Settings();
        settings.setNotificationIsEnabled(true);
        newContactPerson.setSettings(settings);
        settings.setUser(newContactPerson);

        return saveContactPerson(newContactPerson);
    }

    public ContactPerson updateContactPerson(ContactPersonDTO contactPersonDTO) throws DatabaseUserException {
        Address address = handleAddressExistance(contactPersonDTO.getAddress());
        ContactPerson contactPerson = UserFactory.createContactPerson(contactPersonDTO);
        contactPerson.setAddress(address);

        return saveContactPerson(contactPerson);
    }

    public Address handleAddressExistance(Address address) {
        return addressControl.checkAddressExistence(address);
    }

    private ContactPerson saveContactPerson(ContactPerson contactPerson ) throws DatabaseUserException {
        try {
            // Abspeicherung der Entity in die DB
            ContactPerson savedContactPerson = this.contactPersonRepository.save( contactPerson );

            if (contactPerson.getId() > 0)
                LOGGER.info("LOG : Updated ContactPerson with ID : {}" , contactPerson.getId());
            else
                LOGGER.info("LOG : Created new ContactPerson: {}" , contactPerson.getId());

            return savedContactPerson;
        } catch (Exception exception) {
            LOGGER.info("LOG : {}" , exception.toString());
            if (exception instanceof org.springframework.dao.DataAccessResourceFailureException) {
                throw new DatabaseUserException("Während der Verbindung zur Datenbank mit JPA ist ein Fehler aufgetreten.");
            } else {
                throw new DatabaseUserException("Es ist ein unerwarteter Fehler aufgetreten.");
            }
        }
    }

    public boolean checkIfUserIsProfileOwner(UserDTO currentUser, int companyId) {
        ContactPerson contactPersonDTO = contactPersonRepository.findContactPersonByCompanyId(companyId);

        if(currentUser.getId() == contactPersonDTO.getId())
            return true;
        else return currentUser.getId() == companyId;
    }
}
