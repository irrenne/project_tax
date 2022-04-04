package com.epam.tax.dao.impl;


import com.epam.tax.dao.ReportDao;
import com.epam.tax.dao.mapper.ReportRowMapper;
import com.epam.tax.database.DBManager;
import com.epam.tax.entities.Report;
import com.epam.tax.entities.Type;
import com.epam.tax.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.tax.constants.Queries.*;

public class ReportDaoImpl implements ReportDao {
    private static int noOfRecords;

    @Override
    public boolean insertReport(Report report) {
        try (var connection = DBManager.getInstance().getConnection()) {
            try (var ps = connection.prepareStatement(SQL_INSERT_REPORT, Statement.RETURN_GENERATED_KEYS)) {
                ps.setDate(1, report.getDateOfCreation());
                ps.setInt(2, report.getStatusId());
                ps.setLong(3, report.getUserId());
                ps.setString(4, report.getFileName());
                ps.executeUpdate();
                try (var rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        long reportId = rs.getLong(1);
                        report.setId(reportId);
                    }
                }
            }

            reportType(report, connection, SQL_INSERT_REPORT_TYPE);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public List<Report> findAllReports(Long langId) {
        List<Report> reports = new ArrayList<>();

        try (var connection = DBManager.getInstance().getConnection();
             var statement = connection.prepareStatement(SQL_GET_REPORTS)) {
            statement.setLong(1, langId);
            ReportRowMapper mapper = new ReportRowMapper();
            try (var rs = statement.executeQuery()) {
                while (rs.next()) {
                    Report report = mapper.mapRow(rs);
                    reports.add(report);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reports;
    }

    @Override
    public List<Report> findAllReportsForUser(User user) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reports;
    }

    @Override
    public Report getReportById(Long id) {
        Report report = null;
        try (var con = DBManager.getInstance().getConnection();
             var pstmt = con.prepareStatement(SQL__FIND_REPORT_BY_ID)) {
            ReportRowMapper mapper = new ReportRowMapper();
            pstmt.setLong(1, id);
            try (var rs = pstmt.executeQuery()) {
                if (rs.next())
                    report = mapper.mapRow(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return report;
    }

    @Override
    public Report getReportByType(String type) {
        Report report = null;

        try (var connection = DBManager.getInstance().getConnection();
             var ps = connection.prepareStatement(SQL__FIND_REPORT_BY_TYPE)) {
            ReportRowMapper mapper = new ReportRowMapper();
            ps.setString(1, type);
            try (var rs = ps.executeQuery()) {
                if (rs.next())
                    report = mapper.mapRow(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return report;
    }

    @Override
    public boolean updateReport(Report report) {
        try (var connection = DBManager.getInstance().getConnection();
             var ps = connection.prepareStatement(SQL_UPDATE_REPORT)
        ) {
            ps.setDate(1, report.getDateOfCreation());
            ps.setInt(2, report.getStatus().getId());
            ps.setString(3, report.getComment());
            ps.setLong(4, report.getInspectorId());
            ps.setString(5, report.getFileName());
            ps.setLong(6, report.getId());

            reportType(report, connection, SQL_UPDATE_REPORT_TYPE);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to set report type in db table.
     *
     * @param report              is a report for which type is must be set.
     * @param connection          to a db.
     * @param sqlUpdateReportType query for inserting or updating type of report.
     */
    private void reportType(Report report, Connection connection, String sqlUpdateReportType) throws SQLException {
        try (var statement = connection.prepareStatement(sqlUpdateReportType)) {
            statement.setString(1, report.getType().toLowerCase());
            statement.setLong(2, report.getId());
            statement.setLong(3, 1L);
            statement.executeUpdate();

        }
        try (var statement = connection.prepareStatement(sqlUpdateReportType)) {
            statement.setString(1, Type.valueOf(report.getType()).getLocalization());
            statement.setLong(2, report.getId());
            statement.setLong(3, 2L);
            statement.executeUpdate();
        }
    }

    @Override
    public boolean updateReportInspector(Report report) {
        try (var connection = DBManager.getInstance().getConnection();
             var ps = connection.prepareStatement(SQL_UPDATE_REPORT_COMMENT)
        ) {
            ps.setInt(1, report.getStatus().getId());
            ps.setString(2, report.getComment());
            ps.setLong(3, report.getInspectorId());
            ps.setLong(4, report.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Report> viewAllReportsForUser(User user, Long langId, String query, Integer status) {
        List<Report> list = new ArrayList<>();
        try (var connection = DBManager.getInstance().getConnection();
             var ps = connection.prepareStatement(query)) {
            ps.setLong(1, langId);
            ps.setLong(2, user.getId());
            if (status != null) {
                ps.setInt(3, status);
            }
            reportMapp(list, ps);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<Report> viewAllReports(Long langId, String query, Integer status, Long userId) {
        List<Report> list = new ArrayList<>();
        try (var connection = DBManager.getInstance().getConnection();
             var ps = connection.prepareStatement(query)) {
            ps.setLong(1, langId);
            if (status != null && userId == null) {
                ps.setInt(2, status);
            } else if (userId != null && status == null) {
                ps.setLong(2, userId);
            } else if (userId != null) {
                ps.setInt(2, status);
                ps.setLong(3, userId);
            }
            reportMapp(list, ps);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /**
     * Method to map reports.
     *
     * @param list of reports.
     * @param ps   is a prepared statement.
     */
    private void reportMapp(List<Report> list, PreparedStatement ps) throws SQLException {
        Report report;
        ReportRowMapper mapper = new ReportRowMapper();
        try (var rs = ps.executeQuery()) {
            while (rs.next()) {
                report = mapper.mapRow(rs);
                list.add(report);
            }
        }
        try (ResultSet resultSet = ps.executeQuery(SQL_FOUND_ROWS)) {

            if (resultSet.next())
                ReportDaoImpl.noOfRecords = resultSet.getInt(1);
        }
    }

    @Override
    public boolean deleteReport(Report report) {
        try (var connection = DBManager.getInstance().getConnection();
             var ps = connection.prepareStatement(DELETE_REPORT)) {
            ps.setLong(1, report.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getNoOfRecords() {
        return noOfRecords;
    }
}


