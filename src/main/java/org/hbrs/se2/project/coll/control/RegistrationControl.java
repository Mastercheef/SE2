package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.dtos.RegistrationResultDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;

public class RegistrationControl {
    private UserDTO user;

    public void RegistrationControl(){
    }

    public boolean validateInput(UserDTO user_){
        return false;
    }

    public RegistrationResultDTO registerUser(UserDTO user_){
        return null;
    }

    public boolean checkForExistence(UserDTO user_){
        return false;
    }
}
