package com.example.demo.Commands;

import com.example.demo.dao.UserDAO;
import com.example.demo.model.User;

import java.util.List;

public class CommandSelectAll implements CommandDB{
    private UserDAO userDAO;
    private List<User> users;

    public CommandSelectAll(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void execute() {
        users = userDAO.selectAllUsers();
    }

    public List<User> getUsers() {
        return users;
    }
}
