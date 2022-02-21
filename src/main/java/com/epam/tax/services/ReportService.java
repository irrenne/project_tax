package com.epam.tax.services;

import com.epam.tax.entities.Report;
import com.epam.tax.entities.User;
import com.epam.tax.xml.ReportXml;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ReportService {
    boolean insert(Report report, ReportXml reportXml) throws SQLException, IOException;

    boolean update(Report report, ReportXml reportXml) throws SQLException, IOException;

    boolean delete(Report report) throws SQLException;

    Report getById(Long id) throws SQLException;

    Report getByType(String type) throws SQLException;

    boolean deleteById(Long id) throws SQLException;

    List<Report> findAll() throws SQLException;

    List<Report> findAll(User user) throws SQLException;
}
