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


@WebServlet("/inspector/deny_report")
public class DenyServlet extends HttpServlet {
    private final ReportServiceImpl reportService = new ReportServiceImpl();
    private final Logger log = LogManager.getLogger(DenyServlet.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        response.sendRedirect(request.getContextPath() + "/inspector/reports");
    }
}
