package com.epam.tax.servlets.program;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet("/download-template")
public class TemplateServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(TemplateServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String template = request.getParameter("template");

        String filePath = getServletContext().getInitParameter("uploadDirectory");
        String fileLocation = filePath + "/" + template;

        File file = new File(fileLocation);
        FileInputStream fis = new FileInputStream(file);
        ServletOutputStream sos = response.getOutputStream();

        response.setContentType("text/xml");
        response.setHeader("Content-Disposition", "attachment;filename=" + template);
        byte[] buffer = new byte[4096];

        while ((fis.read(buffer, 0, 4096)) != -1) {
            sos.write(buffer, 0, 4096);
        }
        fis.close();
    }
}
