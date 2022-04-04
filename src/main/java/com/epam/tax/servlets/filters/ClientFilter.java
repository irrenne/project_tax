package com.epam.tax.servlets.filters;

import com.epam.tax.entities.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/client/*")
public class ClientFilter implements Filter {
    private static final Logger log = LogManager.getLogger(ClientFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);
        String loginURI = request.getContextPath() + "/login";

        boolean loggedInClient = session.getAttribute("role").equals(Role.CLIENT);

        if (loggedInClient) {
            chain.doFilter(request, response);
        } else {
            log.info("user is not a client");
            response.sendRedirect(loginURI);
        }
    }
}
