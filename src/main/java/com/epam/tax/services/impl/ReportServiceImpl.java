package com.epam.tax.services.impl;

import com.epam.tax.dao.impl.ReportDao;
import com.epam.tax.dao.impl.UserDao;
import com.epam.tax.entities.Report;
import com.epam.tax.entities.User;
import com.epam.tax.services.ReportService;
import com.epam.tax.xml.ReportXml;
import com.epam.tax.xml.SAXController;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportServiceImpl implements ReportService {
    @Override
    public boolean insert(Report report, ReportXml reportXml) throws SQLException, IOException {
        try {
            report.setDocument(getByteArrayFromFile(getGeneratedPdfFile(report, reportXml)));
            return ReportDao.insertReport(report);
        } catch (SQLException | FileNotFoundException | DocumentException | URISyntaxException e) {
            e.printStackTrace();
            throw new SQLException();
        }
    }

    @Override
    public boolean update(Report report, ReportXml reportXml) throws SQLException, IOException {
        try {
            report.setDocument(getByteArrayFromFile(getGeneratedPdfFile(report, reportXml)));
            return ReportDao.updateReport(report);
        } catch (SQLException | FileNotFoundException | DocumentException | URISyntaxException e) {
            e.printStackTrace();
            throw new SQLException();
        }
    }

    @Override
    public boolean delete(Report report) throws SQLException {
        try {
            return ReportDao.deleteReport(report);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }
    }

    @Override
    public boolean deleteById(Long id) throws SQLException {
        try {
            Report report = ReportDao.getReportById(id);
            return ReportDao.deleteReport(report);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }
    }

    @Override
    public Report getById(Long id) throws SQLException {
        try {
            return ReportDao.getReportById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }
    }

    @Override
    public Report getByType(String type) throws SQLException {
        try {
            return ReportDao.getReportByType(type);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }
    }

    @Override
    public List<Report> findAll() throws SQLException {
        try {
            return ReportDao.findAllReports();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }
    }

    @Override
    public List<Report> findAll(User user) throws SQLException {
        try {
            return ReportDao.findAllReportsForUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }
    }

    public boolean updateInspector(Report report) throws SQLException, IOException {
        try {
            return ReportDao.updateReportInspector(report);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }
    }

    public List<Report> findAllReportsForUser(int offset, int noOfRecords, User user) throws SQLException {
        try {
            return ReportDao.viewAllReportsForUser(offset, noOfRecords, user);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }
    }

    public List<Report> findAllReportsPagination(int offset, int noOfRecords) throws SQLException {
        try {
            return ReportDao.viewAllReports(offset, noOfRecords);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }
    }

    public List<User> findInspectors(List<Report> reports) throws SQLException {
        List<User> inspectors = new ArrayList<>();
        for (Report report : reports) {
            User inspector = UserDao.getUserById(report.getInspectorId());
            if (inspector != null && !inspectors.contains(inspector)) {
                inspectors.add(inspector);
            }
        }
        return inspectors;
    }


    private Document getGeneratedPdfFile(Report report, ReportXml reportXml) throws IOException, DocumentException, URISyntaxException, SQLException {
        User user = UserDao.getUserById(report.getUserId());
        //   Path targetPath = Paths.get(ReportServiceImpl.class.getResource("/").toURI()).getParent();
        Path targetPath = Path.of("/Users/macbook/Desktop/temp/");

        System.out.println(targetPath);


        OutputStream file = new FileOutputStream(targetPath + "/file.pdf");
        Document document = new Document();
        PdfWriter.getInstance(document, file);
        document.open();
        Paragraph paragraph = new Paragraph("Report");
        paragraph.add(Chunk.NEWLINE);
        paragraph.add("date: " + report.getDateOfCreation());
        paragraph.add(Chunk.NEWLINE);
        paragraph.add("This document is property of " + user.getName() + " " + user.getSurname() + ".");
        paragraph.add(Chunk.NEWLINE);
        paragraph.add("Works in " + reportXml.getCompany() + ".");
        paragraph.add(Chunk.NEWLINE);
        paragraph.add("Income for month " + reportXml.getSalary() + ".");

        document.add(paragraph);
        document.close();
        file.close();
        return document;
    }

    private byte[] getByteArrayFromFile(final Document handledDocument) throws IOException, URISyntaxException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        Path targetPath = Paths.get(ReportServiceImpl.class.getResource("/").toURI()).getParent();
        Path targetPath = Path.of("/Users/macbook/Desktop/temp/");

        final InputStream in = new FileInputStream(targetPath + "/file.pdf");
        final byte[] buffer = new byte[500];

        int read = -1;
        while ((read = in.read(buffer)) > 0) {
            baos.write(buffer, 0, read);
        }
        in.close();

        return baos.toByteArray();
    }


    public ReportXml readXml(String pathXmlFile) {
//        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//        Source schemaFile = new StreamSource(new File("report.xsd"));
//        try {
//            Schema schema = factory.newSchema(schemaFile);
//            Validator validator = schema.newValidator();
//            validator.validate(new StreamSource(new File(pathXmlFile)));
//        } catch (IOException | SAXException e) {
//            e.printStackTrace();
//        }

        SAXController saxController = new SAXController(pathXmlFile);
        ReportXml reportXml = null;
        try {
            reportXml = saxController.parseSAX();
            System.out.println(reportXml);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reportXml;
    }
}

