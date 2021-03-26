package com.example.demo.ConnectionDB;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHSQLDB implements ConnectionInt {
    private final static String jdbcURL = "jdbc:hsqldb:hsql://192.168.0.105/Test";
    private final static String jdbcUsername = "SA";
    private final static String jdbcPassword = "";

    private Connection connection;

    public Connection getConnection() {

        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
