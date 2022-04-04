package com.epam.tax.servlets.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter(urlPatterns = {"/home", "/client/*", "/inspector/*", "/pdf", "/download-template", "/download", "/create_report", "/update-report"})
public class LogoutFilter implements Filter {
    private static final Logger log = LogManager.getLogger(LogoutFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        String loginURI = request.getContextPath() + "/login";


        boolean loggedIn = session != null && session.getAttribute("user") != null;
        boolean loginRequest = request.getRequestURI().equals(loginURI);

        if (loggedIn && !loginRequest) {
            log.info("user can move forward");
            chain.doFilter(request, response);
        } else {
            log.info("oops, log in again");
            response.sendRedirect(loginURI);
        }
    }

}