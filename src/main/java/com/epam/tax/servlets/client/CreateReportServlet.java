package com.epam.tax.servlets.client;

import com.epam.tax.services.impl.ReportServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/create_report")
public class CreateReportServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(CreateReportServlet.class);
    private final ReportServiceImpl reportService = new ReportServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/jsp/client/create_report.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        User user = (User) req.getSession().getAttribute("user");
//        log.info("user in session " + user);
//        System.out.println(req.getSession().getAttribute("hi"));
//
//        String type = req.getParameter("type");
//        Date date = Date.valueOf(req.getParameter("date"));
////        ReportXml reportXml = (ReportXml) req.getSession(false).getAttribute("reportXml");
//
////        System.out.println("Serv fileeeeee" + getServletContext().getInitParameter("upload.location"));
//
//        Report report = Report.createReport(type, date);
//        log.info("------------>created report " + report);
//        report.setUserId(user.getId());
//        try {
//            reportService.insert(report, reportXml);
//            log.info("inserted report");
//            resp.sendRedirect(req.getContextPath() + "/client/reports");
//        } catch (SQLException e) {
//            log.error(e.getMessage());
//            resp.sendRedirect("WEB-INF/jsp/error.jsp");
//        }
    }
}
