package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.dtos.LoginResultDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.repository.CompanyProfileRepository;
import org.hbrs.se2.project.coll.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class LoginControl {

    @Autowired
    private UserRepository repository;

    private UserDTO userDTO = null;

    public LoginResultDTO authentificate(String username, String password ) {
        LoginResultDTO authentificationResult = new LoginResultDTO();

        // User wird per SQL ausgelesen
        this.userDTO = this.getUser( authentificationResult, username , password );

        return authentificationResult;
    }

    public UserDTO getCurrentUser(){
        return this.userDTO;

    }

    private UserDTO getUser(LoginResultDTO result, String username , String password ) {
        UserDTO userTmp;
        try {
            userTmp = repository.findUserByUserIdAndPassword(username, password);
            result.setResult(true);
            result.setReason("LogIn erfolgreich");
        } catch ( org.springframework.dao.DataAccessResourceFailureException e ) {
            userTmp = null;
            result.setResult(false);
            result.setReason("Es ist ein Fehler während der Verbindung zur Datenbank aufgetreten: " + e);
        }
        return userTmp;
    }

}
