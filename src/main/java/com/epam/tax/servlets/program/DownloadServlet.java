package com.epam.tax.servlets.program;

import com.epam.tax.entities.Report;
import com.epam.tax.entities.User;
import com.epam.tax.services.impl.FileStorageServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
    private FileStorageServiceImpl fileStorageService = new FileStorageServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        Report report = (Report) request.getSession(false).getAttribute("report");

        String uniqueFolder = user.getLogin() + "/";

        try {
            String targetPath = fileStorageService.getTargetPath();
            System.out.println(request.getServletContext().getRealPath("/WEB-INF/resources/"));

            //String fileLocation = targetPath + uniqueFolder + report.getFileName();
            String fileLocation = "/Users/macbook/Desktop/temp/" + uniqueFolder + report.getFileName(); //change the path according to you

            File file = new File(fileLocation);
            FileInputStream fis = new FileInputStream(file);
            ServletOutputStream sos = response.getOutputStream();


            response.setContentType("text/xml");
            response.setHeader("Content-Disposition", "attachment;filename=" + report.getFileName());
            byte[] buffer = new byte[4096];

            while ((fis.read(buffer, 0, 4096)) != -1) {
                sos.write(buffer, 0, 4096);
            }
            fis.close();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
