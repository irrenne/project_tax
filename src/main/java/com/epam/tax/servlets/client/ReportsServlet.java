package com.epam.tax.servlets.client;


import com.epam.tax.dao.impl.ReportDaoImpl;
import com.epam.tax.entities.Report;
import com.epam.tax.entities.User;
import com.epam.tax.services.impl.ReportServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        String query = req.getQueryString();
        if (query != null)
            query = query.replaceAll("&page=[0-9]", "");
        req.setAttribute("query", query);

        User user = (User) req.getSession().getAttribute("user");
        String lang = String.valueOf(req.getSession().getAttribute("lang"));
        Long langId = reportService.setLanguage(lang);
        Integer status = null;
        String order = null;
        int page = 1;
        int recordsPerPage = 5;

        if (req.getParameter("page") != null)
            page = Integer.parseInt(req.getParameter("page"));
        if (req.getParameter("status") != null && !req.getParameter("status").isEmpty())
            status = Integer.parseInt(req.getParameter("status"));
        if (req.getParameter("order") != null && !req.getParameter("order").isEmpty())
            order = req.getParameter("order");

        try {
            List<Report> list = reportService.findAllReportsOrderByTypeForUser((page - 1) * recordsPerPage, recordsPerPage, user, langId, status, order);
            int noOfRecords = ReportDaoImpl.getNoOfRecords();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            List<User> inspectors = reportService.findInspectors(list);

            req.setAttribute("reports", list);
            req.setAttribute("noOfPages", noOfPages);
            req.setAttribute("currentPage", page);
            req.setAttribute("inspectors", inspectors);
            log.info("reports for user " + user.getName());
            req.getRequestDispatcher("/WEB-INF/jsp/client/reports.jsp").forward(req, resp);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
