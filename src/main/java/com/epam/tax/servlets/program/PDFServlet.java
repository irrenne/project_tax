package com.epam.tax.servlets.program;

import com.epam.tax.entities.Report;
import com.epam.tax.services.impl.ReportServiceImpl;
import com.itextpdf.text.DocumentException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/pdf")
public class PDFServlet extends HttpServlet {
    private final ReportServiceImpl reportService = new ReportServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long reportId = Long.parseLong(req.getParameter("view"));
        Report report = null;
        try {
            report = reportService.getById(reportId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            writeToResponse(resp, report);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void writeToResponse(HttpServletResponse response, Report report) throws IOException, DocumentException, SQLException {
        response.setContentType("application/pdf");
        byte[] fileContent = report.getDocument();
        ServletOutputStream out = response.getOutputStream();
        out.write(fileContent);
        out.flush();
    }
}
