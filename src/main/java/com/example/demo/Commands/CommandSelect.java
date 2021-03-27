package com.example.demo.Commands;

import com.example.demo.dao.UserDAO;
import com.example.demo.model.User;

public class CommandSelect implements CommandDB {
    private UserDAO userDAO;
    private User user;
    private int parameter;

    public CommandSelect(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void execute() {
       user = userDAO.selectUser(parameter);
    }

    public User getUser() {
        return user;
    }

    public void setParameter(int parameter) {
        this.parameter = parameter;
    }
}
