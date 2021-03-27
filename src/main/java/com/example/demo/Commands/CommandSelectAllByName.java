package com.example.demo.Commands;

import com.example.demo.dao.UserDAO;
import com.example.demo.model.User;

import java.util.List;

public class CommandSelectAllByName implements CommandDB {
    private UserDAO userDAO;
    private List<User> users;

    public CommandSelectAllByName(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void execute() {
        users = userDAO.selectAllUsersOrderedByName();
    }

    public List<User> getUsers() {
        return users;
    }
}
