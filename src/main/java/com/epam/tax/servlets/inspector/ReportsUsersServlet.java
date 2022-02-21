package com.epam.tax.servlets.inspector;

import com.epam.tax.dao.impl.ReportDao;
import com.epam.tax.entities.Report;
import com.epam.tax.entities.User;
import com.epam.tax.services.impl.ReportServiceImpl;
import com.epam.tax.services.impl.UserServiceImpl;
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

@WebServlet("/inspector/reports")
public class ReportsUsersServlet extends HttpServlet {
    private final UserServiceImpl userService = new UserServiceImpl();
    private final ReportServiceImpl reportService = new ReportServiceImpl();
    private static final Logger log = LogManager.getLogger(ReportsUsersServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ReportsUsersServlet.doGet");
        int page = 1;
        int recordsPerPage = 5;
        if (req.getParameter("page") != null)
            page = Integer.parseInt(req.getParameter("page"));

        try {
            List<Report> list = reportService.findAllReportsPagination((page - 1) * recordsPerPage, recordsPerPage);

            int noOfRecords = ReportDao.getNoOfRecords();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

            List<User> users = userService.findAll();

            log.info("got users" + users);
            log.info("got reports" + list);
            req.setAttribute("reports", list);
            req.setAttribute("noOfPages", noOfPages);
            req.setAttribute("currentPage", page);
            req.setAttribute("users", users);
            log.info("success in setting attributes");
            req.getRequestDispatcher("/WEB-INF/jsp/inspector/reports.jsp").forward(req, resp);
        } catch (SQLException e) {
            log.error(e.getMessage());
            resp.sendRedirect("WEB-INF/jsp/error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
