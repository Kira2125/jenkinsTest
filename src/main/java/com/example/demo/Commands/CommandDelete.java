package com.example.demo.Commands;

import com.example.demo.dao.UserDAO;

import java.sql.SQLException;

public class CommandDelete implements CommandDB {
    private UserDAO userDAO;
    boolean status;
    private int parameter;

    public CommandDelete(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void execute() {
        try {
            status = userDAO.deleteUser(parameter);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean isStatus() {
        return status;
    }

    public void setParameter(int parameter) {
        this.parameter = parameter;
    }
}
