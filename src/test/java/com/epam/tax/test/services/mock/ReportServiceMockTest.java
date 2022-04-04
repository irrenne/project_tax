package com.epam.tax.test.services.mock;

import com.epam.tax.dao.impl.ReportDaoImpl;
import com.epam.tax.entities.Report;
import com.epam.tax.entities.User;
import com.epam.tax.services.impl.ReportServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.List;

import static com.epam.tax.constants.Queries.SQL_GET_REPORTS_PAGINATION;
import static com.epam.tax.constants.Queries.SQL_GET_REPORTS_PAGINATION_FOR_USER;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReportServiceMockTest {

    private ReportServiceImpl reportService;

    @Mock
    private ReportDaoImpl reportDaoMock;

    @Before
    public void initializeMock() throws Exception {
        reportService = spy(new ReportServiceImpl());
        Field reportDaoField = ReportServiceImpl.class
                .getDeclaredField("reportDao");
        reportDaoField.setAccessible(true);
        reportDaoField.set(reportService, reportDaoMock);
    }

    @Test
    public void saveReportTest() throws Exception {
        Report reportMock = mock(Report.class);
        doReturn(true).when(reportDaoMock).insertReport(reportMock);
        assertTrue(reportService.insert(reportMock));
    }

    @Test
    public void updateReportTest() throws Exception {
        Report reportMock = mock(Report.class);
        doReturn(true).when(reportDaoMock).updateReport(reportMock);
        assertTrue(reportService.update(reportMock));
    }

    @Test
    public void deleteReportTest() throws Exception {
        Report reportMock = mock(Report.class);
        doReturn(true).when(reportDaoMock).deleteReport(reportMock);
        assertTrue(reportService.delete(reportMock));
    }

    @Test
    public void deleteReportByIdTest() throws Exception {
        Report reportMock = mock(Report.class);
        Long id = 1L;
        doReturn(reportMock).when(reportDaoMock).getReportById(id);
        doReturn(true).when(reportDaoMock).deleteReport(reportMock);
        assertTrue(reportService.deleteById(id));
    }

    @Test
    public void getReportByIdTest() throws Exception {
        Report reportMock = mock(Report.class);
        Long id = 1L;
        doReturn(reportMock).when(reportDaoMock).getReportById(id);
        Assert.assertEquals(reportMock, reportService.getById(id));
    }

    @Test
    public void getReportByTypeTest() throws Exception {
        Report reportMock = mock(Report.class);
        String type = "type1";
        doReturn(reportMock).when(reportDaoMock).getReportByType(type);
        Assert.assertEquals(reportMock, reportService.getByType(type));
    }

    @Test
    public void updateReportInspectorTest() throws Exception {
        Report reportMock = mock(Report.class);
        doReturn(true).when(reportDaoMock).updateReportInspector(reportMock);
        assertTrue(reportService.updateInspector(reportMock));
    }

    @Test
    public void getReportsTest() throws Exception {
        Report reportMock1 = mock(Report.class);
        Report reportMock2 = mock(Report.class);
        List<Report> reports = List.of(new Report[]{reportMock1, reportMock2});
        Long id = 1L;
        doReturn(reports).when(reportDaoMock).findAllReports(id);
        Assert.assertEquals(reports, reportService.findAll(id));
    }

    @Test
    public void getReportsForUserTest() throws Exception {
        Report reportMock1 = mock(Report.class);
        Report reportMock2 = mock(Report.class);
        User userMock = mock(User.class);
        List<Report> reports = List.of(new Report[]{reportMock1, reportMock2});

        doReturn(reports).when(reportDaoMock).findAllReportsForUser(userMock);
        Assert.assertEquals(reports, reportService.findAll(userMock));
    }

    @Test
    public void getReportsForUserPaginationTest() throws Exception {
        Report reportMock1 = mock(Report.class);
        Report reportMock2 = mock(Report.class);
        User userMock = mock(User.class);
        List<Report> reports = List.of(new Report[]{reportMock1, reportMock2});
        int offset = 2, noOfRecords = 5;
        Long langId = 1L;
        Integer status = 1;
        String order = "type", q = SQL_GET_REPORTS_PAGINATION_FOR_USER + "AND reports.status_id=? "
                + "ORDER BY " + order + " limit " + offset + ", " + noOfRecords;

        doReturn(reports).when(reportDaoMock).viewAllReportsForUser(userMock, langId, q, status);
        Assert.assertEquals(reports, reportService.findAllReportsOrderByTypeForUser(offset, noOfRecords, userMock, langId, status, order));
    }

    @Test
    public void getReportsPaginationTest() throws Exception {
        Report reportMock1 = mock(Report.class);
        Report reportMock2 = mock(Report.class);
        List<Report> reports = List.of(new Report[]{reportMock1, reportMock2});
        int offset = 2, noOfRecords = 5;
        Long langId = 1L, userId = 2L;
        Integer status = 1;
        String order = "type", q = SQL_GET_REPORTS_PAGINATION + "AND reports.status_id=? " + "AND reports.user_id=? "
                + "ORDER BY " + order + " limit " + offset + ", " + noOfRecords;

        doReturn(reports).when(reportDaoMock).viewAllReports(langId, q, status, userId);
        Assert.assertEquals(reports, reportService.findAllReportsPagination(offset, noOfRecords, langId, status, order, userId));
    }
}