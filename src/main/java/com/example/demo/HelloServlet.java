package com.example.demo;

import com.example.demo.Commands.*;
import com.example.demo.dao.UserDAO;
import com.example.demo.model.User;

import java.io.*;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(value = "/")
public class HelloServlet extends HttpServlet {
    private UserDAO userDAO;
    private CommandDB commandUpdate;
    private CommandDB commandSelect;
    private CommandDB commandDelete;
    private CommandDB commandInsert;
    private CommandDB commandSelectAll;
    private CommandDB commandSelectAllByName;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertUser(request, response);
                    break;
                case "/delete":
                    deleteUser(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateUser(request, response);
                    break;
                case "/order-name":
                    listUserByName(request, response);
                default:
                    listUser(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        commandSelectAll = new CommandSelectAll(userDAO);
        commandSelectAll.execute(request, response);

    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        commandSelect = new CommandSelect(userDAO);
        commandSelect.execute(request, response);
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {

        commandInsert = new CommandInsert(userDAO);
        commandInsert.execute(request, response);

    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {

        commandUpdate = new CommandUpdate(userDAO);
        commandUpdate.execute(request, response);

    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {

        commandDelete = new CommandDelete(userDAO);
        commandDelete.execute(request, response);

    }

    private void listUserByName(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        commandSelectAllByName = new CommandSelectAllByName(userDAO);
        commandSelectAllByName.execute(request, response);

    }
}