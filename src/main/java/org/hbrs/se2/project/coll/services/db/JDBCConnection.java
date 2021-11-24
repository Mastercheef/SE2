package org.hbrs.se2.project.coll.services.db;

import org.hbrs.se2.project.coll.services.db.exceptions.DatabaseLayerException;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Class to handle the Connection to the Database.
 * @author sascha, last edited fbehri2s
 */
public class JDBCConnection {

    private static JDBCConnection connection = null;
    private Connection conn;
    private String url = "jdbc:postgresql://dumbo.inf.h-brs.de/houde2s";
    private String login = "houde2s";
    private String password = "houde2s";

    /**
     * Opens a new Connection to the Database and returns an instant of this class.
     * @return JDBCConnection to Database
     * @throws DatabaseLayerException
     */
    public static JDBCConnection getInstance() throws DatabaseLayerException {
        if ( connection == null ) {
            connection = new JDBCConnection();
        }
        return connection;
    }

    private JDBCConnection() throws DatabaseLayerException {
        this.initConnection();
    }

    public void initConnection() throws DatabaseLayerException {
        try {
            DriverManager.registerDriver( new org.postgresql.Driver() );
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.openConnection();
    }

    public void openConnection() throws DatabaseLayerException {
        try {
            Properties props = new Properties();
            props.setProperty("user", login);
            props.setProperty("password", password);

            this.conn = DriverManager.getConnection(this.url, props);

        } catch (SQLException ex) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, ex);
            throw new DatabaseLayerException( "Fehler bei Zugriff auf die DB! Sichere Verbindung vorhanden!?" );
        }
    }

    public Statement getStatement() throws DatabaseLayerException {
        try {
            if ( this.conn.isClosed() ) {
                this.openConnection();
            }

            return this.conn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public PreparedStatement getPreparedStatement( String sql  ) throws DatabaseLayerException {
        try {
            if ( this.conn.isClosed() ) {
                this.openConnection();
            }

            return this.conn.prepareStatement(sql);
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void closeConnection(){
        try {
            this.conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}