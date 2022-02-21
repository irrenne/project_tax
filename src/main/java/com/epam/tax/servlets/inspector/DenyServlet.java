package com.epam.tax.servlets.inspector;

import com.epam.tax.entities.Report;
import com.epam.tax.entities.Status;
import com.epam.tax.entities.User;
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


@WebServlet("/inspector/deny_report")
public class DenyServlet extends HttpServlet {
    private final ReportServiceImpl reportService = new ReportServiceImpl();
    private final Logger log = LogManager.getLogger(DenyServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long reportId = Long.parseLong(request.getParameter("deny"));
        request.getSession(false).setAttribute("reportId", reportId);

        request.getRequestDispatcher("/WEB-INF/jsp/inspector/deny_report.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long reportId = (Long) request.getSession(false).getAttribute("reportId");
        User user = (User) request.getSession().getAttribute("user");
        String reason = request.getParameter("reason");

        try {
            Report report = reportService.getById(reportId);
            report.setComment(reason);
            report.setStatus(Status.NOT_CONFIRMED);
            report.setInspectorId(user.getId());
            if (!report.getStatus().equals(Status.CONFIRMED)) {
                reportService.updateInspector(report);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            response.sendRedirect("WEB-INF/jsp/error.jsp");
        }
        response.sendRedirect(request.getContextPath() + "/inspector/reports");
    }
}
