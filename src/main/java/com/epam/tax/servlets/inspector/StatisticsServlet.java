package com.epam.tax.servlets.inspector;

import com.epam.tax.dao.impl.ReportDaoImpl;
import com.epam.tax.entities.Report;
import com.epam.tax.entities.User;
import com.epam.tax.services.impl.ReportServiceImpl;
import com.epam.tax.services.impl.UserServiceImpl;
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

@WebServlet("/inspector/statistics")
public class StatisticsServlet extends HttpServlet {
    private final UserServiceImpl userService = new UserServiceImpl();
    private final ReportServiceImpl reportService = new ReportServiceImpl();
    private static final Logger log = LogManager.getLogger(StatisticsServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getQueryString();
        if (query != null)
            query = query.replaceAll("&page=[0-9]", "");
        req.setAttribute("query", query);

        int page = 1;
        int recordsPerPage = 5;
        String lang = String.valueOf(req.getSession().getAttribute("lang"));
        Long langId = reportService.setLanguage(lang);
        Integer status = null;
        Long userId = null;
        String order = null;

        try {
            if (req.getParameter("page") != null)
                page = Integer.parseInt(req.getParameter("page"));
            if (req.getParameter("status") != null && !req.getParameter("status").isEmpty())
                status = Integer.parseInt(req.getParameter("status"));
            if (req.getParameter("order") != null && !req.getParameter("order").isEmpty())
                order = req.getParameter("order");
            if (req.getParameter("userId") != null && !req.getParameter("userId").isEmpty()) {
                userId = Long.parseLong(req.getParameter("userId"));
                User client = userService.getById(userId);
                req.setAttribute("client", client);
            }

            List<Report> list = reportService.findAllReportsPagination((page - 1) * recordsPerPage, recordsPerPage, langId, status, order, userId);

            int noOfRecords = ReportDaoImpl.getNoOfRecords();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

            List<User> users = userService.findAll();
            List<Report> allReports = reportService.findAll(langId);
            int submittedReports = reportService.submittedReports(allReports, userId);
            int confirmedReports = reportService.confirmedReports(allReports, userId);
            int deniedReports = reportService.deniedReports(allReports, userId);
            List<User> inspectors = reportService.findInspectors(allReports);


            log.info("got users" + users);
            log.info("got reports" + list);
            log.info("got inspectors" + inspectors);
            req.setAttribute("reports", list);
            req.setAttribute("noOfPages", noOfPages);
            req.setAttribute("currentPage", page);
            req.setAttribute("users", users);
            req.setAttribute("inspectors", inspectors);
            req.setAttribute("confirmed", confirmedReports);
            req.setAttribute("denied", deniedReports);
            req.setAttribute("submitted", submittedReports);
            log.info("success in setting attributes");
            req.getRequestDispatcher("/WEB-INF/jsp/inspector/statistic.jsp").forward(req, resp);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
