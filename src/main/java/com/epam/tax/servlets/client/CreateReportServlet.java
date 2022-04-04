package com.epam.tax.servlets.client;

import com.epam.tax.entities.Type;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/create_report")
public class CreateReportServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(CreateReportServlet.class);
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("create report page");
        req.getSession().setAttribute("types", new ArrayList<>(List.of(Type.values())));
        req.getRequestDispatcher("WEB-INF/jsp/client/create_report.jsp").forward(req, resp);
    }
}