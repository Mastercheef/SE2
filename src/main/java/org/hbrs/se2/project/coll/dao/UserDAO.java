package org.hbrs.se2.project.coll.dao;

import org.hbrs.se2.project.coll.dtos.UserDTO;

import org.hbrs.se2.project.coll.dtos.impl.UserDTOImpl;
import org.hbrs.se2.project.coll.services.db.JDBCConnection;
import org.hbrs.se2.project.coll.services.db.exceptions.DatabaseLayerException;
import org.hbrs.se2.project.coll.util.Globals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {

    public UserDTO findUserByUseridAndPassword(String username, String password) {
        ResultSet resultSet;
        try {
            Statement statement = null;
            try {
                // Connect to Database and create a statement
                statement = JDBCConnection.getInstance().getStatement();
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }
            resultSet = statement.executeQuery(
                    "SELECT * "
                            + "FROM collhbrs.col_view_student_profile p"
                            + "WHERE p.user_id = \'" + username + "\'"
                            + " AND p.password = \'" + password + "\'");

        } catch (SQLException ex) {
            DatabaseLayerException e = new DatabaseLayerException("Fehler in einem SQL-Befehl!" +
                    "Bitte wenden Sie sich an einen Administrator.");
            e.setReason(Globals.Errors.SQLERROR);
            //throw e;
            return null;
        }
        catch (NullPointerException ex) {
            DatabaseLayerException e = new DatabaseLayerException("Fehler bei der Verbindung mit der Datenbank!");
            e.setReason(Globals.Errors.DATABASE);
            //throw e;
            return null;
        }

        try {
            if (resultSet.next()) {
                // Object-Relational-Mapping (ORM)
                UserDTOImpl user = new UserDTOImpl();;
                user.setId( resultSet.getInt(1));
                user.setFirstname( resultSet.getString(4));
                user.setLastname(resultSet.getString(5));

                return user;

            } else {
                // Error: No user could be found
                DatabaseLayerException e = new DatabaseLayerException("Es konnte kein Nutzer gefunden werden.");
                e.setReason(Globals.Errors.NOUSERFOUND);
                //throw e;
                return null;
            }
        } catch (SQLException ex) {
            // Error: Something else went wrong
            DatabaseLayerException e = new DatabaseLayerException("Probleme mit der Datenbank...");
            e.setReason(Globals.Errors.DATABASE);
            //throw e;
            return null;

        } finally {
            //JDBCConnection.getInstance().closeConnection();
        }
    }
}
