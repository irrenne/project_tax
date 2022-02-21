package com.epam.tax.servlets.client;


import com.epam.tax.dao.impl.ReportDao;
import com.epam.tax.entities.Report;
import com.epam.tax.entities.User;
import com.epam.tax.services.impl.ReportServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/client/reports")
public class ReportsServlet extends HttpServlet {
    private final ReportServiceImpl reportService = new ReportServiceImpl();
    private static final Logger log = LogManager.getLogger(ReportsServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ReportServlet.doGet");
        User user = (User) req.getSession().getAttribute("user");

        int page = 1;
        int recordsPerPage = 5;
        if (req.getParameter("page") != null)
            page = Integer.parseInt(req.getParameter("page"));
        try {
            List<Report> list = reportService.findAllReportsForUser((page - 1) * recordsPerPage, recordsPerPage, user);
            int noOfRecords = ReportDao.getNoOfRecords();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

            List<User> inspectors = reportService.findInspectors(list);

            req.setAttribute("reports", list);
            req.setAttribute("noOfPages", noOfPages);
            req.setAttribute("currentPage", page);
            req.setAttribute("inspectors", inspectors);
            log.info("reports for user " + user.getName());
            req.getRequestDispatcher("/WEB-INF/jsp/client/reports.jsp").forward(req, resp);
        } catch (SQLException e) {
            log.error(e.getMessage());
            resp.sendRedirect("WEB-INF/jsp/error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ReportsServlet.doPost");
    }
}
