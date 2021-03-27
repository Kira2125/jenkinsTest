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
    private CommandUpdate commandUpdate;
    private CommandSelect commandSelect;
    private CommandDelete commandDelete;
    private CommandInsert commandInsert;
    private CommandSelectAll commandSelectAll;
    private CommandSelectAllByName commandSelectAllByName;

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
        commandSelectAll.execute();
        List<User> listUser = commandSelectAll.getUsers();
        request.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        commandSelect = new CommandSelect(userDAO);
        commandSelect.setParameter(id);
        commandSelect.execute();
        User existingUser = commandSelect.getUser();
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
        request.setAttribute("user", existingUser);
        dispatcher.forward(request, response);

    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");
        User newUser = new User(name, email, country);
        commandInsert = new CommandInsert(userDAO);
        commandInsert.setParameter(newUser);
        commandInsert.execute();
        response.sendRedirect("list");
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");

        User user = new User(id, name, email, country);
        commandUpdate = new CommandUpdate(userDAO);
        commandUpdate.setParameter(user);
        commandUpdate.execute();
        response.sendRedirect("list");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        commandDelete = new CommandDelete(userDAO);
        commandDelete.setParameter(id);
        commandDelete.execute();
        response.sendRedirect("list");

    }

    private void listUserByName(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        commandSelectAllByName = new CommandSelectAllByName(userDAO);
        commandSelectAllByName.execute();
        List<User> listUser = commandSelectAllByName.getUsers();
        request.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }
}