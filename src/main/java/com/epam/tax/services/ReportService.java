package com.epam.tax.services;

import com.epam.tax.entities.Report;
import com.epam.tax.entities.User;
import com.epam.tax.exceptions.XMLException;
import com.epam.tax.xml.ReportXml;
import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Interface, that contains methods for
 * multiply operations related to entity reports.
 */
public interface ReportService {

    /**
     * Method for inserting report in database.
     *
     * @param report report, that must be saved.
     * @return true if report was saved to database.
     */
    boolean insert(Report report);

    /**
     * Method for updating report in database.
     *
     * @param report report, that must be updated.
     * @return true if report was updated to database.
     */
    boolean update(Report report);

    /**
     * Method for deleting report from database.
     *
     * @param report report, that must be deleted.
     * @return true if report was deleted from database.
     */
    boolean delete(Report report);

    /**
     * Method for getting report from database by id.
     *
     * @param id is an id of the report which should be returned.
     * @return entity report.
     */
    Report getById(Long id);

    /**
     * Method for getting report from database by type.
     *
     * @param type is a type of the report which should be returned.
     * @return entity report.
     */
    Report getByType(String type);

    /**
     * Method for getting report from database by id.
     *
     * @param id is id of the report which should be deleted.
     * @return true if report was deleted from db.
     */
    boolean deleteById(Long id);

    /**
     * Method for finding all reports.
     *
     * @param langId is id of the certain language in which reports should be returned.
     * @return list of all reports.
     */
    List<Report> findAll(Long langId);

    /**
     * Method for finding all reports.
     *
     * @param user for whom user reports must be found.
     * @return list of all reports for a certain user.
     */
    List<Report> findAll(User user);

    /**
     * Method for updating report in database from inspector's side.
     *
     * @param report report, that must be updated.
     * @return true if report was updated to database.
     */
    boolean updateInspector(Report report);

    /**
     * Method for finding all inspectors, who checked the reports.
     *
     * @param reports is list of reports.
     * @return list of inspectors who checked the reports.
     */
    List<User> findInspectors(List<Report> reports);

    /**
     * Method to generate pdf-format report.
     *
     * @param report            is report to be generated.
     * @param outputStream      where to write pdf.
     * @param pathTargetFolders path to where xml file of report is located.
     */
    void generatePDF(Report report, OutputStream outputStream, String pathTargetFolders) throws DocumentException, IOException, XMLException;

    /**
     * Method to read and parse xml file.
     *
     * @param pathXmlFile path to where xml file of report is located.
     * @return entity of parsed xml file.
     */
    ReportXml readXml(String pathXmlFile) throws XMLException;

    /**
     * Method to return id for certain languages.
     *
     * @param lang name of the language.
     * @return id of certain language.
     */
    Long setLanguage(String lang);

    /**
     * Method for finding all reports for certain user with pagination, sorting and filtering.
     *
     * @param offset      from which record to return.
     * @param noOfRecords amount of records to return.
     * @param user        for whom user reports must be found.
     * @param langId      is id of the certain language in which reports should be returned.
     * @param status      for filtering by status.
     * @param order       for sorting by certain order.
     * @return list of reports with pagination for certain user and filtered/sorted or not.
     */
    List<Report> findAllReportsOrderByTypeForUser(int offset, int noOfRecords, User user, Long langId, Integer status, String order);

    /**
     * Method for finding all reports with pagination, sorting and filtering.
     *
     * @param offset      from which record to return.
     * @param noOfRecords amount of records to return.
     * @param langId      is id of the certain language in which reports should be returned.
     * @param status      for filtering by status.
     * @param order       for sorting by certain order.
     * @return list of reports with pagination and filtered/sorted or not.*
     */
    List<Report> findAllReportsPagination(int offset, int noOfRecords, Long langId, Integer status, String order, Long userId);

    /**
     * Method to return amount of submitted reports.
     *
     * @param reports list of reports.
     * @param userId  is if amount should be calculated for certain user's reports
     * @return int value of submitted reports, if userId is set then amount of submitted reports for user.
     */
    int submittedReports(List<Report> reports, Long userId);

    /**
     * Method to return amount of confirmed reports.
     *
     * @param reports list of reports.
     * @param userId  is if amount should be calculated for certain user's reports
     * @return int value of confirmed reports, if userId is set then amount of confirmed reports for user.
     */
    int confirmedReports(List<Report> reports, Long userId);

    /**
     * Method to return amount of denied reports.
     *
     * @param reports list of reports.
     * @param userId  is if amount should be calculated for certain user's reports
     * @return int value of denied reports, if userId is set then amount of denied reports for user.
     */
    int deniedReports(List<Report> reports, Long userId);

    /**
     * Method to check if input is valid as a type.
     *
     * @param test input to check.
     * @return true if input is valid as a type.
     */
    boolean checkValidType(String test);
}
