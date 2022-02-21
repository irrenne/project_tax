package com.epam.tax.constants;

public class Queries {
    public static final String SQL__FIND_USER_BY_LOGIN = "SELECT users.*, roles.role_name FROM users LEFT JOIN roles ON roles.id=users.role_id WHERE users.username=?";
    public static final String SQL__FIND_USER_BY_ID = "SELECT users.*, roles.role_name FROM users LEFT JOIN roles ON roles.id=users.role_id WHERE users.id=?";
    public static final String SQL_INSERT_USER = "INSERT INTO users (username, password, name, surname, role_id ) VALUES (?,?,?,?,?)";
    public static final String SQL_UPDATE_USER = "UPDATE users SET password=?, name=?, username=?, surname=? WHERE id=?";
    public static final String DELETE_USER = "DELETE FROM users WHERE id=?";
    public static final String SQL_GET_USERS = "SELECT users.*, roles.role_name FROM users LEFT JOIN roles ON roles.id=users.role_id WHERE users.role_id=2";

    public static final String SQL__FIND_REPORT_BY_TYPE = "SELECT reports.*, statuses.status_name FROM reports LEFT JOIN statuses ON statuses.id=reports.status_id WHERE reports.type=?";
    public static final String SQL__FIND_REPORT_BY_ID = "SELECT reports.*, statuses.status_name FROM reports LEFT JOIN statuses ON statuses.id=reports.status_id WHERE reports.id=?";
    public static final String SQL_INSERT_REPORT = "INSERT INTO reports (date, status_id, type, user_id, document_blob, file_name) VALUES (?,?,?,?,?,?)";
    public static final String SQL_UPDATE_REPORT_COMMENT = "UPDATE reports SET status_id=?, comment=?, inspector_id=? WHERE id=?";
    public static final String SQL_UPDATE_REPORT = "UPDATE reports SET date=?, status_id=?, type=?, comment=?, document_blob=?, inspector_id=?, file_name=? WHERE id=?";
    public static final String DELETE_REPORT = "DELETE FROM reports WHERE id=?";
    public static final String SQL_GET_REPORTS = "SELECT reports.*, statuses.status_name FROM reports LEFT JOIN statuses ON statuses.id=reports.status_id LEFT JOIN users ON users.id=reports.user_id";
    public static final String SQL_GET_REPORTS_PAGINATION = "select SQL_CALC_FOUND_ROWS schema_tax.reports.*, schema_tax.statuses.status_name FROM schema_tax.reports LEFT JOIN schema_tax.statuses ON statuses.id=reports.status_id LEFT JOIN schema_tax.users ON users.id=reports.user_id limit ";
    public static final String SQL_GET_REPORTS_PAGINATION_FOR_USER = "select SQL_CALC_FOUND_ROWS schema_tax.reports.*, schema_tax.statuses.status_name FROM schema_tax.reports LEFT JOIN schema_tax.statuses ON statuses.id=reports.status_id LEFT JOIN schema_tax.users ON users.id=reports.user_id WHERE users.id=? limit ";
    public static final String SQL_GET_REPORTS_FOR_USER = "SELECT reports.*, statuses.status_name FROM reports LEFT JOIN statuses ON statuses.id=reports.status_id LEFT JOIN users ON users.id=reports.user_id WHERE users.id=?";
}
