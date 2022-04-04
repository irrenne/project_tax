package com.epam.tax.services.impl;

import com.epam.tax.dao.impl.ReportDaoImpl;
import com.epam.tax.dao.impl.UserDaoImpl;
import com.epam.tax.entities.Report;
import com.epam.tax.entities.Type;
import com.epam.tax.entities.User;
import com.epam.tax.exceptions.XMLException;
import com.epam.tax.services.ReportService;
import com.epam.tax.xml.ReportXml;
import com.epam.tax.xml.SAXController;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.epam.tax.constants.Constants.FONT_FILENAME;
import static com.epam.tax.constants.Queries.SQL_GET_REPORTS_PAGINATION;
import static com.epam.tax.constants.Queries.SQL_GET_REPORTS_PAGINATION_FOR_USER;

public class ReportServiceImpl implements ReportService {
    private final ReportDaoImpl reportDao;
    private final UserDaoImpl userDao;

    public ReportServiceImpl() {
        reportDao = new ReportDaoImpl();
        userDao = new UserDaoImpl();
    }

    @Override
    public boolean insert(Report report) {
        return reportDao.insertReport(report);
    }

    @Override
    public boolean update(Report report) {
        return reportDao.updateReport(report);
    }

    @Override
    public boolean delete(Report report) {
        return reportDao.deleteReport(report);
    }

    @Override
    public boolean deleteById(Long id) {
        Report report = reportDao.getReportById(id);
        return reportDao.deleteReport(report);
    }

    @Override
    public Report getById(Long id) {
        return reportDao.getReportById(id);
    }

    @Override
    public Report getByType(String type) {
        return reportDao.getReportByType(type);
    }

    @Override
    public List<Report> findAll(Long langId) {
        return reportDao.findAllReports(langId);
    }

    @Override
    public List<Report> findAll(User user) {
        return reportDao.findAllReportsForUser(user);
    }

    @Override
    public boolean updateInspector(Report report) {
        return reportDao.updateReportInspector(report);
    }

    @Override
    public List<User> findInspectors(List<Report> reports) {
        List<User> inspectors = new ArrayList<>();
        for (Report report : reports) {
            User inspector = userDao.getUserById(report.getInspectorId());
            if (inspector != null && !inspectors.contains(inspector)) {
                inspectors.add(inspector);
            }
        }
        return inspectors;
    }

    @Override
    public void generatePDF(Report report, OutputStream outputStream, String pathTargetFolders) throws DocumentException, IOException, XMLException {
        User user = userDao.getUserById(report.getUserId());

        BaseFont bf = BaseFont.createFont(FONT_FILENAME, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bf, 20, Font.NORMAL);
        ReportXml reportXml = readXml(pathTargetFolders + user.getLogin() + "/" + report.getFileName());

        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();
        Paragraph paragraph = new Paragraph("Звіт", font);
        paragraph.add(Chunk.NEWLINE);
        paragraph.add("Дата: " + report.getDateOfCreation());
        paragraph.add(Chunk.NEWLINE);
        paragraph.add("Власник звіту " + user.getName() + " " + user.getSurname() + ".");
        paragraph.add(Chunk.NEWLINE);
        paragraph.add("Працює в " + reportXml.getCompany() + ".");
        paragraph.add(Chunk.NEWLINE);
        paragraph.add("Місячний дохід становить " + reportXml.getSalary() + " гривників.");

        document.add(paragraph);
        document.close();
    }

    @Override
    public ReportXml readXml(String pathXmlFile) throws XMLException {
        SAXController saxController = new SAXController(pathXmlFile);
        ReportXml reportXml;
        try {
            reportXml = saxController.parseSAX();
        } catch (Exception e) {
            throw new XMLException("problem with xml file", e);
        }
        return reportXml;
    }

    @Override
    public Long setLanguage(String lang) {
        if (lang != null && lang.equals("en")) {
            return 1L;
        }
        return 2L;
    }

    @Override
    public List<Report> findAllReportsOrderByTypeForUser(int offset, int noOfRecords, User user, Long langId, Integer status, String order) {
        StringBuilder query = new StringBuilder(SQL_GET_REPORTS_PAGINATION_FOR_USER);
        if (status != null) {
            query.append("AND reports.status_id=? ");
        }
        if (order != null) {
            query.append("ORDER BY ").append(order);
        }
        query.append(" limit ").append(offset).append(", ").append(noOfRecords);

        return reportDao.viewAllReportsForUser(user, langId, query.toString(), status);

    }

    @Override
    public List<Report> findAllReportsPagination(int offset, int noOfRecords, Long langId, Integer status, String order, Long userId) {
        StringBuilder query = new StringBuilder(SQL_GET_REPORTS_PAGINATION);
        if (status != null) {
            query.append("AND reports.status_id=? ");
        }
        if (userId != null) {
            query.append("AND reports.user_id=? ");
        }
        if (order != null) {
            query.append("ORDER BY ").append(order);
        }
        query.append(" limit ").append(offset).append(", ").append(noOfRecords);

        return reportDao.viewAllReports(langId, query.toString(), status, userId);

    }

    @Override
    public int submittedReports(List<Report> reports, Long userId) {
        int count = 0;
        if (userId == null) {
            for (Report report : reports) {
                if (report.getStatusId() == 0) {
                    count++;
                }
            }
            return count;
        }
        for (Report report : reports) {
            if (report.getStatusId() == 0 && report.getUserId() == userId) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int confirmedReports(List<Report> reports, Long userId) {
        int count = 0;
        if (userId == null) {
            for (Report report : reports) {
                if (report.getStatusId() == 1) {
                    count++;
                }
            }
            return count;
        }
        for (Report report : reports) {
            if (report.getStatusId() == 1 && report.getUserId() == userId) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int deniedReports(List<Report> reports, Long userId) {
        int count = 0;
        if (userId == null) {
            for (Report report : reports) {
                if (report.getStatusId() == 2) {
                    count++;
                }
            }
            return count;
        }
        for (Report report : reports) {
            if (report.getStatusId() == 2 && report.getUserId() == userId) {
                count++;
            }
        }
        return count;
    }

    public boolean checkValidInput(String input) {
        return input != null && !input.isEmpty();
    }

    @Override
    public boolean checkValidType(String test) {
        for (Type c : Type.values()) {
            if (c.name().equals(test)) {
                return true;
            }
        }
        return false;
    }
}