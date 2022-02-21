package com.epam.tax.servlets.user;

import com.epam.tax.entities.Role;
import com.epam.tax.entities.User;
import com.epam.tax.services.impl.UserServiceImpl;
import com.epam.tax.servlets.util.PasswordHash;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

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
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/jsp/client/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String loginURI = req.getContextPath() + "/login";

        String login = req.getParameter("login");
//        byte[] bytes = login.getBytes(StandardCharsets.ISO_8859_1);
//        login = new String(bytes, StandardCharsets.UTF_8);
//        System.out.println(login);

        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String password = req.getParameter("password");

        User user = User.createUser(login, name, surname, PasswordHash.hash(password));
        System.out.println(surname + " " + name);
        System.out.println("CharacterEncoding = " + req.getCharacterEncoding());

//        request.setCharacterEncoding("UTF-8");
//        System.out.println("CharacterEncoding = " + request.getCharacterEncoding());

        try {
            if (userService.checkValidInput(login) &&
                    userService.checkValidInput(name) && userService.checkValidInput(surname)
                    && userService.checkValidInput(password)) {
                user.setRole(Role.CLIENT);
                userService.insert(user);
                log.info("inserting user" + user);
            } else {
                throw new Exception("invalid input");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.info(e.getMessage());
            resp.sendRedirect("WEB-INF/jsp/error.jsp");
        }
        resp.sendRedirect(loginURI);
    }

}
