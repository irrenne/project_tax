package com.epam.tax.servlets.user;

import com.epam.tax.entities.User;
import com.epam.tax.services.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserServiceImpl userService = new UserServiceImpl();
    private static final Logger log = LogManager.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("LoginServlet.doGet");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("LoginServlet.doPost");

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        try {
            User user = userService.getByLogin(login);
            log.info("get user" + user);

            if (userService.isValid(login, password, user)) {
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("role", user.getRole());
                response.sendRedirect(request.getContextPath() + "/home");
                log.info("%---------------- SUCCESS --------------------%, user is logged in");
                return;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            response.sendRedirect("WEB-INF/jsp/error.jsp");
        }
        response.sendRedirect(request.getContextPath() + "/login");
    }
}