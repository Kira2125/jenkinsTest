package com.example.demo.ConnectionDB;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionDB implements ConnectionInt {
    private final String jdbcURL = "jdbc:postgresql://192.168.0.109:5432/test";
    private final String jdbcUsername = "postgres";
    private final String jdbcPassword = "password";

    private BasicDataSource connectionPool;

    public Connection getConnection() {
        connectionPool = new BasicDataSource();
        connectionPool.setUsername(jdbcUsername);
        connectionPool.setPassword(jdbcPassword);
        connectionPool.setDriverClassName("org.postgresql.Driver");
        connectionPool.setUrl(jdbcURL);
        connectionPool.setInitialSize(5);
        connectionPool.setMaxTotal(10);
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }
}
