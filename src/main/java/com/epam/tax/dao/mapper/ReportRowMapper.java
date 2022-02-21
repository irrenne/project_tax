package com.epam.tax.dao.mapper;


import com.epam.tax.entities.Report;
import com.epam.tax.entities.Status;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportRowMapper implements RowMapper<Report> {
    @Override
    public Report mapRow(ResultSet resultSet) {
        Report report = new Report();
        try {
            report.setId(resultSet.getLong("id"));
            report.setDateOfCreation(resultSet.getDate("date"));
            report.setType(resultSet.getString("type"));
            report.setDocument(resultSet.getBytes("document_blob"));
            report.setStatus(Status.valueOf(resultSet.getString("status_name").toUpperCase()));
            report.setUserId(resultSet.getLong("user_id"));
            report.setComment(resultSet.getString("comment"));
            report.setInspectorId(resultSet.getLong("inspector_id"));
            report.setFileName(resultSet.getString("file_name"));
            return report;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
