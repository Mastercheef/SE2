package org.hbrs.se2.project.coll.database;

import java.sql.*;
import java.util.Properties;

public class test {

    public static void main(String[] args) throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
        String url = "jdbc:postgresql://dumbo.inf.h-brs.de/demouser";
        Properties props = new Properties();
        props.setProperty("user", "demouser");
        props.setProperty("password", "demouser");

        Connection connection = DriverManager.getConnection(url, props);
        Statement statement = connection.createStatement();
        ResultSet reuslt = statement.executeQuery("SELECT * FROM bierschema.angebot");

        while (reuslt.next()) {
            System.out.println("Kneipe: " + reuslt.getString("biersorte"));
        }
    }
}
