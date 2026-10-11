package com.codegym.controller;

import com.codegym.dao.UserDAO;
import com.codegym.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "UserServlet", urlPatterns = "/users")
public class UserServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "";

        try {
            switch (action) {
                case "create" -> request.getRequestDispatcher("/user/create.jsp").forward(request, response);
                case "edit" -> showEditForm(request, response);
                case "delete" -> showDeleteForm(request, response);
                case "test-without-tran" -> testWithoutTran(request, response);
                default -> listUsers(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) action = "";

        try {
            switch (action) {
                case "create" -> insertUser(request, response);
                case "edit" -> updateUser(request, response);
                case "delete" -> deleteUser(request, response);
                default -> response.sendRedirect(request.getContextPath() + "/users");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<User> users = userDAO.selectAllUsers();
        request.setAttribute("listUser", users);
        request.getRequestDispatcher("/user/list.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("user", userDAO.selectUser(id));
        request.getRequestDispatcher("/user/edit.jsp").forward(request, response);
    }

    private void showDeleteForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("user", userDAO.selectUser(id));
        request.getRequestDispatcher("/user/delete.jsp").forward(request, response);
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        User user = new User(
                request.getParameter("name"),
                request.getParameter("email"),
                request.getParameter("country")
        );
        userDAO.insertUser(user);
        response.sendRedirect(request.getContextPath() + "/users");
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        User user = new User(
                Integer.parseInt(request.getParameter("id")),
                request.getParameter("name"),
                request.getParameter("email"),
                request.getParameter("country")
        );
        userDAO.updateUser(user);
        response.sendRedirect(request.getContextPath() + "/users");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        userDAO.deleteUser(Integer.parseInt(request.getParameter("id")));
        response.sendRedirect(request.getContextPath() + "/users");
    }

    private void testWithoutTran(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        userDAO.insertUpdateWithoutTransaction();
        System.out.println("Đã chạy xong hàm testWithoutTran. Hãy kiểm tra database!");

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<h2>Đã chạy test không sử dụng Transaction.</h2>");
        response.getWriter().println("<p>Hãy kiểm tra Console và chạy SELECT * FROM Employee; trong MySQL.</p>");
        response.getWriter().println("<a href='" + request.getContextPath() + "/users'>Quay lại danh sách</a>");
    }
}
