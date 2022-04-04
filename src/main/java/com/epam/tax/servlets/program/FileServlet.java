package com.epam.tax.servlets.program;

import com.epam.tax.entities.Report;
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
import java.util.Collection;

@WebServlet("/client/file")
@MultipartConfig
public class FileServlet extends HttpServlet {
    private final ReportServiceImpl reportService = new ReportServiceImpl();
    private final FileStorageServiceImpl fileStorageService = new FileStorageServiceImpl();
    private static final Logger log = LogManager.getLogger(FileServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(req.getContextPath() + "/create_report").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        String uniqueFolder = user.getLogin() + "/";
        String fileName = null;
        if (request.getPart("file") != null) {
            Part filePart = request.getPart("file");
            fileName = filePart.getSubmittedFileName();
        }
        Collection<Part> fileParts = request.getParts();

        String type = request.getParameter("type");
        String dateInput = request.getParameter("date");
        try {
            if (!reportService.checkValidInput(dateInput) || !reportService.checkValidInput(type) || !reportService.checkValidInput(fileName) || !reportService.checkValidType(type)) {
                log.info("check date " + dateInput);
                log.info("check type " + type);
                log.info("check file " + fileName);
                request.getSession().setAttribute("errMsg", "Please, fill in all data");
                response.sendRedirect(request.getContextPath() + "/create_report");
            } else {
                Date date = Date.valueOf(dateInput);
                String filePath = String.valueOf(getServletContext().getAttribute("uploadDirectory"));
                String path = filePath + uniqueFolder;
                fileName = fileStorageService.getGeneratedFileName("xml");

                fileStorageService.saveFile(path, fileName, fileParts);
                log.info(fileName);
                log.info("file is uploaded");

                Report report = Report.createReport(type, date, fileName);
                report.setUserId(user.getId());
                log.info("------------>created report " + report);

                reportService.insert(report);
                log.info("inserted report");

                response.sendRedirect(request.getContextPath() + "/client/reports");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
