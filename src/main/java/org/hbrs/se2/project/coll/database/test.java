package org.hbrs.se2.project.coll.database;

import java.sql.*;
import java.util.Properties;

public class test {

    public static void main(String[] args) throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
        String url = "jdbc:postgresql://dumbo.inf.h-brs.de/houde2s";
        Properties props = new Properties();
        props.setProperty("user", "houde2s");
        props.setProperty("password", "houde2s");

        Connection connection = DriverManager.getConnection(url, props);
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM collhbrs.col_tab_student");

        while (result.next()) {
            System.out.println("Kneipe: " + result.getString("biersorte"));
        }
    }
}
