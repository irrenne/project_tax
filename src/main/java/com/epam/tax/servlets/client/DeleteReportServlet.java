package com.epam.tax.servlets.client;

import com.epam.tax.services.impl.ReportServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/delete-report")
public class DeleteReportServlet extends HttpServlet {
    private final ReportServiceImpl reportService = new ReportServiceImpl();
    private static final Logger log = LogManager.getLogger(DeleteReportServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/jsp/client/reports.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("delete") != null) {
            try {
                reportService.deleteById(Long.parseLong(req.getParameter("delete")));
            } catch (SQLException e) {
                log.error(e.getMessage());
                resp.sendRedirect("WEB-INF/jsp/error.jsp");
            }
        }resp.sendRedirect(req.getContextPath() +"/client/reports");
    }
}
