package com.epam.tax.servlets.filters;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = {"/login", "/home", "/client/*", "/inspector/*"} )
public class LoginFilter implements Filter {
    private static final Logger log = LogManager.getLogger(LoginFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        String loginURI = request.getContextPath() + "/login";
        BasicConfigurator.configure();


        boolean loggedIn = session != null && session.getAttribute("user") != null;
        boolean loginRequest = request.getRequestURI().equals(loginURI);

        if (loggedIn || loginRequest) {
            log.info("user can move forward");
            chain.doFilter(request, response);
        } else {
            log.info("oops, log in again");
            response.sendRedirect(loginURI);
        }
    }
}