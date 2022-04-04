package com.epam.tax.servlets.program;

import com.epam.tax.entities.Report;
import com.epam.tax.services.impl.ReportServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/pdf")
public class PDFServlet extends HttpServlet {
    private final ReportServiceImpl reportService = new ReportServiceImpl();
    private static final Logger log = LogManager.getLogger(PDFServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long reportId = Long.parseLong(req.getParameter("view"));
        String filePath = getServletContext().getInitParameter("uploadDirectory");
        try {
            Report report = reportService.getById(reportId);
            resp.setContentType("application/pdf");
            reportService.generatePDF(report, resp.getOutputStream(), filePath);
        } catch (Exception e) {
            log.error(e);
        }
    }
}
