package org.hbrs.se2.project.coll.control;

import org.hbrs.se2.project.coll.dtos.LoginResultDTO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.hbrs.se2.project.coll.dtos.impl.LoginResultDTOImpl;
import org.hbrs.se2.project.coll.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class LoginControl {

    @Autowired
    private UserRepository repository;

    private UserDTO userDTO = null;

    private LoginResultDTOImpl loginResult = new LoginResultDTOImpl();

    public LoginResultDTO authentificate(String email, String plainTextPassword ) {

        // User wird per SQL ausgelesen
        this.userDTO = this.getUser(email , plainTextPassword );

        if (this.userDTO == null) {
            loginResult.setResult(false);
            loginResult.setReason("Benutzername oder Passwort falsch");
        }
        return loginResult;
    }

    public UserDTO getCurrentUser(){
        return this.userDTO;
    }

    protected UserDTO getUser(String email , String plainTextPassword ) {
        UserDTO userTmp = null;
        try {
            userTmp = repository.findUserByEmail(email);
            if (BCrypt.checkpw(plainTextPassword, userTmp.getPassword())) {
                loginResult.setResult(true);
                loginResult.setReason("LogIn erfolgreich");
            } else {
                loginResult.setResult(false);
                loginResult.setReason("Das eingegebene Password ist falsch!");
            }
        } catch ( org.springframework.dao.DataAccessResourceFailureException e ) {
            loginResult.setResult(false);
            loginResult.setReason("Es ist ein Fehler während der Verbindung zur Datenbank aufgetreten: " + e);
        }
        return userTmp;
    }
}
