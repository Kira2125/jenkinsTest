package com.example.demo.dao;

import com.example.demo.ConnectionDB.ConnectionDB;
import com.example.demo.ConnectionDB.ConnectionHSQLDB;
import com.example.demo.ConnectionDB.ConnectionInt;
import com.example.demo.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static final String INSERT_USERS_SQL = "INSERT INTO users" + "  (name, email, country) VALUES "
            + " (?, ?, ?);";

    private static final String SELECT_USER_BY_ID = "select id,name,email,country from users where id =?";
    private static final String SELECT_ALL_USERS = "select * from users";
    private static final String DELETE_USERS_SQL = "delete from users where id = ?;";
    private static final String UPDATE_USERS_SQL = "update users set name = ?,email= ?, country =? where id = ?;";
    private static final String SELECT_ALL_USER_ORDERED = "select * from users order by name";
    private final static  String createTableSQL = "DROP TABLE IF EXISTS users;\n" +
            "CREATE TABLE users\n" +
            "(\n" +
            "id int IDENTITY PRIMARY KEY,\n" +
            "name varchar(255),\n" +
            "email varchar(255),\n" +
            "country varchar(255),\n" +
            ");\n" +
            "INSERT INTO users (name, email, country) VALUES ('john', 'john@gmail.com', 'egypt');\n" +
            "INSERT INTO users (name, email, country) VALUES ('lary', 'lary@gmail.com', 'france');\n" +
            "INSERT INTO users (name, email, country) VALUES ('show', 'show@gmail.com', 'chine');\n" +
            "INSERT INTO users (name, email, country) VALUES ('neil', 'neil@gmail.com', 'usa');\n" +
            "INSERT INTO users (name, email, country) VALUES ('kravc', 'kravc@gmail.com', 'poland');\n";




    private ConnectionInt connectionDB;

    public UserDAO() {
        connectionDB = new ConnectionDB();
    }

    public UserDAO(String onlyForTesting) {
        connectionDB = new ConnectionHSQLDB();
        try (Connection connection = connectionDB.getConnection();
             Statement preparedStatement = connection.createStatement()) {
             preparedStatement.execute(createTableSQL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public boolean insertUser(User user) throws SQLException {
        boolean rowInserted = false;
        try (Connection connection = connectionDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getCountry());
            System.out.println(preparedStatement);
            rowInserted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowInserted;
    }

    public User selectUser(int id) {
        User user = null;


        try (Connection connection = connectionDB.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();


            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                user = new User(id, name, email, country);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
    }



    public List<User> selectAllUsers() {

        List<User> users = new ArrayList();

        try (Connection connection = connectionDB.getConnection();

             Statement statement = connection.createStatement()) {


            ResultSet rs = statement.executeQuery(SELECT_ALL_USERS);


            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                users.add(new User(id, name, email, country));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return users;
    }

    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = connectionDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;

        }
        return rowDeleted;
    }

    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = connectionDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getCountry());
            statement.setInt(4, user.getId());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public List<User> selectAllUsersOrderedByName() {
        List<User> users = new ArrayList();

        try (Connection connection = connectionDB.getConnection();
             Statement statement = connection.createStatement()) {


            ResultSet rs = statement.executeQuery(SELECT_ALL_USER_ORDERED);


            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                users.add(new User(id, name, email, country));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return users;
    }

    private static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

}