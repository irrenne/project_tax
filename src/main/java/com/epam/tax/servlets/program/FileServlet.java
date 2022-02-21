package com.epam.tax.servlets.program;

import com.epam.tax.entities.Report;
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

@WebServlet("/file")
@MultipartConfig
public class FileServlet extends HttpServlet {
    private final ReportServiceImpl reportService = new ReportServiceImpl();
    private final FileStorageServiceImpl fileStorageService = new FileStorageServiceImpl();
    private static final Logger log = LogManager.getLogger(FileServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    //   String path = "/Users/macbook/Desktop/temp/" + uniqueFolder + "/";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        String uniqueFolder = user.getLogin() + "/";
        Part filePart = request.getPart("file");
        String fileName = filePart.getSubmittedFileName();
        try {
            String targetPath = fileStorageService.getTargetPath();

            String path = targetPath + uniqueFolder;
            String pathXmlFile = path + fileName;

            fileStorageService.saveFile(path, fileName, request);

            ReportXml reportXml = reportService.readXml(pathXmlFile);
            log.info(fileName);
            log.info("file is uploaded");

            String type = request.getParameter("type");
            Date date = Date.valueOf(request.getParameter("date"));

            String documentFileName = fileStorageService.uploadFile(pathXmlFile);

            Report report = Report.createReport(type, date, documentFileName);
//            Report report = Report.createReport(type, date, fileName);
            log.info("------------>created report " + report);
            report.setUserId(user.getId());

            reportService.insert(report, reportXml);
            log.info("inserted report");
            response.sendRedirect(request.getContextPath() + "/client/reports");
            //  response.sendRedirect(request.getContextPath() + "/create_report");
        } catch (SQLException | URISyntaxException e) {
            log.error(e.getMessage());
            response.sendRedirect("WEB-INF/jsp/error.jsp");
        }
    }
}
