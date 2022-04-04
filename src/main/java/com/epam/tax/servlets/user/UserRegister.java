package com.epam.tax.servlets.user;

import com.epam.tax.entities.Role;
import com.epam.tax.entities.User;
import com.epam.tax.services.impl.UserServiceImpl;
import com.epam.tax.servlets.util.PasswordHash;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class UserRegister extends HttpServlet {
    private final UserServiceImpl userService = new UserServiceImpl();
    private static final Logger log = LogManager.getLogger(UserRegister.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/jsp/client/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String loginURI = req.getContextPath() + "/login";

        String login = req.getParameter("login");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String password = req.getParameter("password");

        try {
            if (userService.checkValidUserInputRegister(name, surname, login, password)) {
                req.getSession().setAttribute("errMsg", "invalid input");
                resp.sendRedirect(req.getContextPath() + "/register");
            } else {
                User user = User.createUser(login, name, surname, PasswordHash.hash(password));
                user.setRole(Role.CLIENT);
                userService.insert(user);
                log.info("inserting user" + user);
                resp.sendRedirect(loginURI);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
