package com.epam.tax.servlets.client;

import com.epam.tax.entities.Report;
import com.epam.tax.entities.Status;
import com.epam.tax.entities.User;
import com.epam.tax.services.impl.FileStorageServiceImpl;
import com.epam.tax.services.impl.ReportServiceImpl;
import com.epam.tax.xml.ReportXml;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet("/update-report")
@MultipartConfig
public class EditReportServlet extends HttpServlet {
    private final ReportServiceImpl reportService = new ReportServiceImpl();
    private final FileStorageServiceImpl fileStorageService = new FileStorageServiceImpl();
    private static final Logger log = LogManager.getLogger(EditReportServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long reportId = Long.parseLong(req.getParameter("update"));
        try {
            Report report = reportService.getById(reportId);
            req.getSession(false).setAttribute("report", report);
        } catch (SQLException e) {
            log.error(e.getMessage());
            resp.sendRedirect("WEB-INF/jsp/error.jsp");
        }
        req.getRequestDispatcher("WEB-INF/jsp/client/edit_report.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        Date date = Date.valueOf(req.getParameter("date"));


        User user = (User) req.getSession().getAttribute("user");

        String uniqueFolder = user.getLogin() + "/";
        Part filePart = req.getPart("file");
        String fileName = filePart.getSubmittedFileName();
        try {
            String targetPath = fileStorageService.getTargetPath();

            String path = targetPath + uniqueFolder;
            String pathXmlFile = path + fileName;

            fileStorageService.saveFile(path, fileName, req);

            ReportXml reportXml = reportService.readXml(pathXmlFile);
            log.info(fileName);
            log.info("file is uploaded");

            Report report = (Report) req.getSession(false).getAttribute("report");
            report.setType(type);
            report.setDateOfCreation(date);
            if (report.getStatus().equals(Status.NOT_CONFIRMED)) {
                report.setStatus(Status.SUBMITTED);
            }
            reportService.update(report, reportXml);
            resp.sendRedirect(req.getContextPath() + "/client/reports");
        } catch (SQLException | URISyntaxException e) {
            log.error(e.getMessage());
            resp.sendRedirect("WEB-INF/jsp/error.jsp");
        }
    }
}
