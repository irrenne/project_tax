package com.epam.tax.servlets.inspector;

import com.epam.tax.entities.Report;
import com.epam.tax.entities.Status;
import com.epam.tax.entities.User;
import com.epam.tax.services.impl.ReportServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/inspector/reports.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long reportId = Long.parseLong(request.getParameter("confirm"));
        User user = (User) request.getSession().getAttribute("user");
        try {
            Report report = reportService.getById(reportId);
            report.setStatus(Status.CONFIRMED);
            report.setInspectorId(user.getId());
            if (!report.getStatus().equals(Status.NOT_CONFIRMED)) {
                reportService.updateInspector(report);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        response.sendRedirect(request.getContextPath() + "/inspector/reports");
    }
}
