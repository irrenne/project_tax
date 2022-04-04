package com.epam.tax.dao;

import com.epam.tax.entities.Report;
import com.epam.tax.entities.User;

import java.util.List;

/**
 * Interface, that contains methods for
 * creating, updating, deleting, finding entity report from database.
 */
public interface ReportDao {

    /**
     * Method to insert report in database.
     *
     * @param report report, that must be saved.
     * @return true if report was saved to database.
     */
    boolean insertReport(Report report);

    /**
     * Method for finding all reports.
     *
     * @param langId is id of the certain language in which reports should be returned.
     * @return list of all reports.
     */
    List<Report> findAllReports(Long langId);

    /**
     * Method for finding all reports.
     *
     * @param user for whom user reports must be found.
     * @return list of all reports for a certain user.
     */
    List<Report> findAllReportsForUser(User user);

    /**
     * Method for getting report from database by id.
     *
     * @param id is an id of the report which should be returned.
     * @return entity report.
     */
    Report getReportById(Long id);

    /**
     * Method for getting report from database by type.
     *
     * @param type is a type of the report which should be returned.
     * @return entity report.
     */
    Report getReportByType(String type);

    /**
     * Method to update report in database.
     *
     * @param report report, that must be updated.
     * @return true if report was updated to database.
     */
    boolean updateReport(Report report);

    /**
     * Method for updating report in database from inspector's side.
     *
     * @param report report, that must be updated.
     * @return true if report was updated to database.
     */
    boolean updateReportInspector(Report report);

    /**
     * Method for finding all reports for certain user with pagination, sorting and filtering.
     *
     * @param user   for whom user reports must be found.
     * @param langId is id of the certain language in which reports should be returned.
     * @param status for filtering by status.
     * @param query  is built query to get certain records.
     * @return list of reports with pagination for certain user and filtered/sorted or not.
     */
    List<Report> viewAllReportsForUser(User user, Long langId, String query, Integer status);

    /**
     * Method for finding all reports with pagination, sorting and filtering.
     *
     * @param langId is id of the certain language in which reports should be returned.
     * @param status for filtering by status.
     * @param query  is built query to get certain records.
     * @return list of reports with pagination and filtered/sorted or not.
     */
    List<Report> viewAllReports(Long langId, String query, Integer status, Long userId);

    /**
     * Method for deleting report from database.
     *
     * @param report report, that must be deleted.
     * @return true if report was deleted from database.
     */
    boolean deleteReport(Report report);
}
