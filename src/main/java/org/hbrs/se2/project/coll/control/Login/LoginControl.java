package org.hbrs.se2.project.coll.control.Login;

import org.hbrs.se2.project.coll.dao.UserDAO;
import org.hbrs.se2.project.coll.dtos.UserDTO;
import org.springframework.stereotype.Component;


@Component
public class LoginControl {

    private UserDTO userDTO = null;

    public LoginResult authentificate(String username, String password ) {
        LoginResult authentificationResult = new LoginResult();

        // User wird per SQL ausgelesen
        this.userDTO = this.getUser( authentificationResult, username , password );

        return authentificationResult;
    }

    public UserDTO getCurrentUser(){
        return this.userDTO;

    }

    private UserDTO getUser( LoginResult result, String username , String password ) {
        UserDTO userTmp;
        UserDAO userDAO = new UserDAO();
        try {
            userTmp = userDAO.findUserByUseridAndPassword(username, password);
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
