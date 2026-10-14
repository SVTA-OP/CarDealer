package com.example.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * HELPER CLASS: creates a connection to MySQL.
 * Every servlet that needs the database calls DBConnection.getConnection().
 *
 * !!! CHANGE USER and PASSWORD to match YOUR MySQL installation !!!
 */
public class DBConnection {

    // jdbc:mysql://<host>:<port>/<database name>
    private static final String URL =
            "jdbc:mysql://localhost:3306/servletdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "root";   // <-- change me

    public static Connection getConnection() throws SQLException {
        try {
            // Load the MySQL driver class from the mysql-connector-j jar.
            // (Needed inside Tomcat so the driver is found for sure.)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // This happens if the MySQL jar is missing from WEB-INF/lib
            throw new SQLException("MySQL driver jar not found in WEB-INF/lib", e);
        }
        // Open and return the connection
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
