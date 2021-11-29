package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.dtos.LoginResultDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class LoginControl {

    @Autowired
    private UserRepository repository;

    private UserDTO userDTO = null;

    private LoginResultDTO loginResult = new LoginResultDTO();

    public LoginResultDTO authentificate(String email, String password ) {


        // User wird per SQL ausgelesen
        this.userDTO = this.getUser(email , password );

        if (this.userDTO == null) {
            loginResult.setResult(false);
            loginResult.setReason("Benutzername oder Passwort falsch");
        }

        return loginResult;
    }

    public UserDTO getCurrentUser(){
        return this.userDTO;

    }

    private UserDTO getUser(String email , String password ) {
        UserDTO userTmp = null;
        try {
            userTmp = repository.findUserByEmailAndPassword(email, password);
            loginResult.setResult(true);
            loginResult.setReason("LogIn erfolgreich");
        } catch ( org.springframework.dao.DataAccessResourceFailureException e ) {
            loginResult.setResult(false);
            loginResult.setReason("Es ist ein Fehler während der Verbindung zur Datenbank aufgetreten: " + e);
        }
        return userTmp;
    }

}
