package com.epam.tax.dao.impl;


import com.epam.tax.dao.mapper.ReportRowMapper;
import com.epam.tax.database.DBManager;
import com.epam.tax.entities.Report;
import com.epam.tax.entities.User;

import java.io.ByteArrayInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.epam.tax.constants.Queries.*;

public class ReportDao {
    private static int noOfRecords;

    public static boolean insertReport(Report report) throws SQLException {
        try (var connection = DBManager.getInstance().getConnection();
             var ps = connection.prepareStatement(SQL_INSERT_REPORT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDate(1, report.getDateOfCreation());
            ps.setInt(2, report.getStatusId());
            ps.setString(3, report.getType());
            ps.setLong(4, report.getUserId());
            ps.setBlob(5, new ByteArrayInputStream(report.getDocument()));
            ps.setString(6, report.getFileName());
            ps.executeUpdate();
            try (var rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    long reportId = rs.getLong(1);
                    report.setId(reportId);
                    return true;
                }
                return false;
            }
        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    public static List<Report> findAllReports() throws SQLException {
        List<Report> reports = new ArrayList<>();

        try (var connection = DBManager.getInstance().getConnection();
             var statement = connection.createStatement();
             var rs = statement.executeQuery(SQL_GET_REPORTS)) {
            ReportRowMapper mapper = new ReportRowMapper();
            while (rs.next()) {
                Report report = mapper.mapRow(rs);
                reports.add(report);
            }
        } catch (SQLException throwables) {
            throw new SQLException();
        }
        return reports;
    }

    public static List<Report> findAllReportsForUser(User user) throws SQLException {
        List<Report> reports = new ArrayList<>();

        try (var connection = DBManager.getInstance().getConnection();
             var ps = connection.prepareStatement(SQL_GET_REPORTS_FOR_USER)) {
            ps.setLong(1, user.getId());
            ReportRowMapper mapper = new ReportRowMapper();
            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    Report report = mapper.mapRow(rs);
                    reports.add(report);
                }
            }
        } catch (SQLException throwables) {
            throw new SQLException();
        }
        return reports;
    }

    public static Report getReportById(Long id) throws SQLException {
        Report report = null;
        try (var con = DBManager.getInstance().getConnection();
             var pstmt = con.prepareStatement(SQL__FIND_REPORT_BY_ID)) {
            ReportRowMapper mapper = new ReportRowMapper();
            pstmt.setLong(1, id);
            try (var rs = pstmt.executeQuery()) {
                if (rs.next())
                    report = mapper.mapRow(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException();
        }
        return report;
    }

    public static Report getReportByType(String type) throws SQLException {
        Report report = null;

        try (var connection = DBManager.getInstance().getConnection();
             var ps = connection.prepareStatement(SQL__FIND_REPORT_BY_TYPE)) {
            ReportRowMapper mapper = new ReportRowMapper();
            ps.setString(1, type);
            try (var rs = ps.executeQuery()) {
                if (rs.next())
                    report = mapper.mapRow(rs);
            }
        } catch (SQLException throwables) {
            throw new SQLException();
        }
        return report;
    }

    public static boolean updateReport(Report report) throws SQLException {
        try (var connection = DBManager.getInstance().getConnection();
             var ps = connection.prepareStatement(SQL_UPDATE_REPORT)
        ) {
            ps.setDate(1, report.getDateOfCreation());
            ps.setInt(2, report.getStatus().getId());
            ps.setString(3, report.getType());
            ps.setString(4, report.getComment());
            ps.setBlob(5, new ByteArrayInputStream(report.getDocument()));
            ps.setLong(6, report.getInspectorId());
            ps.setString(7, report.getFileName());
            ps.setLong(8, report.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new SQLException();
        }
    }

    public static boolean updateReportInspector(Report report) throws SQLException {
        try (var connection = DBManager.getInstance().getConnection();
             var ps = connection.prepareStatement(SQL_UPDATE_REPORT_COMMENT)
        ) {
            ps.setInt(1, report.getStatus().getId());
            ps.setString(2, report.getComment());
            ps.setLong(3, report.getInspectorId());
            ps.setLong(4, report.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new SQLException();
        }
    }

    public static List<Report> viewAllReportsForUser(int offset, int noOfRecords, User user) throws SQLException {

        String query = SQL_GET_REPORTS_PAGINATION_FOR_USER + offset + ", " + noOfRecords;
        List<Report> list = new ArrayList<>();
        Report employee;
        try (var connection = DBManager.getInstance().getConnection();
             var ps = connection.prepareStatement(query)) {
            ps.setLong(1, user.getId());
            reportMapp(list, ps);
        }
        return list;
    }

    public static List<Report> viewAllReports(int offset, int noOfRecords) throws SQLException {

        String query = SQL_GET_REPORTS_PAGINATION + offset + ", " + noOfRecords;
        List<Report> list = new ArrayList<>();
        Report employee;
        try (var connection = DBManager.getInstance().getConnection();
             var ps = connection.prepareStatement(query)) {
            reportMapp(list, ps);
        }
        return list;
    }

    private static void reportMapp(List<Report> list, PreparedStatement ps) throws SQLException {
        Report report;
        ReportRowMapper mapper = new ReportRowMapper();
        try (var rs = ps.executeQuery()) {
            while (rs.next()) {
                report = mapper.mapRow(rs);
                list.add(report);
            }
        }
        try (ResultSet resultSet = ps.executeQuery("SELECT FOUND_ROWS()")) {

            if (resultSet.next())
                ReportDao.noOfRecords = resultSet.getInt(1);

        }
    }

    public static int getNoOfRecords() {
        return noOfRecords;
    }

    public static boolean deleteReport(Report report) throws SQLException {
        try (var connection = DBManager.getInstance().getConnection();
             var ps = connection.prepareStatement(DELETE_REPORT)) {
            ps.setLong(1, report.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new SQLException();
        }
    }
}


