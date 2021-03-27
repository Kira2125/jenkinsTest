package com.example.demo.Commands;

import com.example.demo.dao.UserDAO;
import com.example.demo.model.User;

import java.sql.SQLException;

public class CommandUpdate implements CommandDB {
    private UserDAO userDAO;
    private boolean status;
    private User parameter;

    public CommandUpdate(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void execute() {
        try {
            status = userDAO.updateUser(parameter);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean isStatus() {
        return status;
    }

    public void setParameter(User parameter) {
        this.parameter = parameter;
    }
}
