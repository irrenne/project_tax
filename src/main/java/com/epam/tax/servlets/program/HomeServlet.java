package com.epam.tax.servlets.program;

import com.epam.tax.entities.Role;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(HomeServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("HomeServlet.doGet");
        Role role = (Role) req.getSession().getAttribute("role");
        if(role.equals(Role.CLIENT)) {
            log.info("user is client");
            req.getRequestDispatcher("WEB-INF/jsp/client/client.jsp").forward(req, resp);
        }else if(role.equals(Role.INSPECTOR)){
            log.info("user is inspector");
            req.getRequestDispatcher("WEB-INF/jsp/inspector/inspector.jsp").forward(req, resp);
        }
    }

}
