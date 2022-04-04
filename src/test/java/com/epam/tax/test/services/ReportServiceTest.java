package com.epam.tax.test.services;

import com.epam.tax.entities.Report;
import com.epam.tax.entities.User;
import com.epam.tax.exceptions.XMLException;
import com.epam.tax.services.impl.ReportServiceImpl;
import com.epam.tax.xml.ReportXml;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


public class ReportServiceTest {
    private final ReportServiceImpl reportService = new ReportServiceImpl();

    @Test
    public void deniedReportsTest() {
        int expected = 1;
        List<Report> reports = generateReports();
        int actual = reportService.deniedReports(reports, null);
        assertEquals(expected, actual);
    }

    @Test
    public void confirmedReportsTest() {
        int expected = 1;
        List<Report> reports = generateReports();
        int actual = reportService.confirmedReports(reports, null);
        assertEquals(expected, actual);
    }

    @Test
    public void submittedReportsTest() {
        int expected = 1;
        List<Report> reports = generateReports();
        int actual = reportService.submittedReports(reports, null);
        assertEquals(expected, actual);
    }

    @Test
    public void validInputTest() {
        String input = "";
        assertFalse(reportService.checkValidInput(input));

        input = "test";
        assertTrue(reportService.checkValidInput(input));
    }

    @Test
    public void readXmlTest() throws XMLException {
        ReportXml expectedReportXml = new ReportXml();
        expectedReportXml.setSalary(100);
        expectedReportXml.setCompany("Company name");

        ReportXml actualReportXml = reportService.readXml("input.xml");
        assertEquals(expectedReportXml, actualReportXml);
    }

    @Test(expected = XMLException.class)
    public void readXmlIllegalTest() throws XMLException {
        reportService.readXml("input1.xml");
    }

    @Test
    public void checkValidTypeTest() {
        String type = "TYPE1";
        assertTrue(reportService.checkValidType(type));
        assertFalse(reportService.checkValidType(type + "test"));
    }

    @Test
    public void deniedReportsForUserTest() {
        int expected = 1;
        User user = User.createUser("login", "name", "surname", "123");
        List<Report> reports = generateReportsForUser(user);
        int actual = reportService.deniedReports(reports, user.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void confirmedReportsForUserTest() {
        int expected = 1;
        User user = User.createUser("login", "name", "surname", "123");
        List<Report> reports = generateReportsForUser(user);
        int actual = reportService.confirmedReports(reports, user.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void submittedReportsForUserTest() {
        int expected = 1;
        User user = User.createUser("login", "name", "surname", "123");
        List<Report> reports = generateReportsForUser(user);
        int actual = reportService.submittedReports(reports, user.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void languageEnIdTest() {
        String lang = "en";
        Long expected = 1L;

        Long actual = reportService.setLanguage(lang);
        assertEquals(expected, actual);
    }

    @Test
    public void languageUkIdTest() {
        String lang = "uk";
        Long expected = 2L;

        Long actual = reportService.setLanguage(lang);
        assertEquals(expected, actual);
    }

    private List<Report> generateReports() {
        Report report1 = Report.createReport("type1", Date.valueOf(LocalDate.of(2026, 1, 23)), "file1");
        report1.setStatusId(0);
        Report report2 = Report.createReport("type1", Date.valueOf(LocalDate.of(2026, 1, 23)), "file2");
        report2.setStatusId(1);
        Report report3 = Report.createReport("type2", Date.valueOf(LocalDate.of(2026, 1, 23)), "file3");
        report3.setStatusId(2);
        return List.of(new Report[]{report1, report2, report3});
    }

    private List<Report> generateReportsForUser(User user) {
        List<Report> reports = generateReports();
        for (Report report : reports) {
            report.setUserId(user.getId());
        }
        return reports;
    }
}
