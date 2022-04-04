package com.epam.tax.servlets.client;

import com.epam.tax.entities.Report;
import com.epam.tax.entities.Status;
import com.epam.tax.entities.Type;
import com.epam.tax.entities.User;
import com.epam.tax.services.impl.FileStorageServiceImpl;
import com.epam.tax.services.impl.ReportServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
            if (!report.getStatus().equals(Status.NOT_CONFIRMED)) {
                resp.sendRedirect(req.getContextPath() + "/client/reports");
            } else {
                req.getSession(false).setAttribute("report", report);
                req.getSession(false).setAttribute("selectedType", Type.valueOf((report.getType()).toUpperCase()));
                req.getSession().setAttribute("types", new ArrayList<>(List.of(Type.values())));
                req.getRequestDispatcher("WEB-INF/jsp/client/edit_report.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        String dateInput = req.getParameter("date");
        User user = (User) req.getSession().getAttribute("user");

        Collection<Part> fileParts = req.getParts();
        String uniqueFolder = user.getLogin() + "/";
        String fileName = null;
        if (req.getPart("file") != null) {
            Part filePart = req.getPart("file");
            fileName = filePart.getSubmittedFileName();
        }
        try {
            Report report = (Report) req.getSession(false).getAttribute("report");
            String filePath = getServletContext().getInitParameter("uploadDirectory");
            String path = filePath + uniqueFolder;

            if (!reportService.checkValidInput(dateInput) || !reportService.checkValidInput(type) || !reportService.checkValidInput(fileName) || !reportService.checkValidType(type)) {
                log.info("check date " + dateInput);
                log.info("check type " + type);
                log.info("check file " + fileName);
                req.getSession().setAttribute("errMsg", "Please, fill in all data");
                resp.sendRedirect(req.getContextPath() + "/update-report?update=" + report.getId());
            } else {
                fileName = report.getFileName();
                fileStorageService.saveFile(path, fileName, fileParts);
                log.info(fileName);
                log.info("file is uploaded");

                Date date = Date.valueOf(dateInput);

                report.setType(type);
                report.setDateOfCreation(date);

                if (report.getStatus().equals(Status.NOT_CONFIRMED)) {
                    report.setStatus(Status.SUBMITTED);
                }
                reportService.update(report);
                resp.sendRedirect(req.getContextPath() + "/client/reports");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
