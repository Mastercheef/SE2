package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.entities.ContactPerson;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthorizationControl {

    @Autowired
    private ContactPersonControl contactPersonControl;

    public AuthorizationControl() {
        // May be of use in the future
    }

    public boolean isUserAllowedToAccessCompanyApplications(UserDTO user, int companyId) {
        ContactPerson contactPerson = contactPersonControl.findContactPersonById(user.getId());

        if (contactPerson == null)
            return false;
        else {
            return contactPerson.getCompany().getId() == companyId;
        }
    }

}
