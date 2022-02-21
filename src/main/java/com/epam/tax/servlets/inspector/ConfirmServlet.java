package com.epam.tax.servlets.inspector;

import com.epam.tax.entities.Report;
import com.epam.tax.entities.Status;
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

@WebServlet("/inspector/confirm_report")
public class ConfirmServlet extends HttpServlet {
    private final ReportServiceImpl reportService = new ReportServiceImpl();
    private static final Logger log = LogManager.getLogger(ConfirmServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/inspector/reports.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long reportId = Long.parseLong(request.getParameter("confirm"));
        try {
            Report report = reportService.getById(reportId);
            report.setStatus(Status.CONFIRMED);
            if (!report.getStatus().equals(Status.NOT_CONFIRMED)) {
                reportService.updateInspector(report);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            response.sendRedirect("WEB-INF/jsp/error.jsp");
        }
        response.sendRedirect(request.getContextPath() + "/inspector/reports");
    }
}
