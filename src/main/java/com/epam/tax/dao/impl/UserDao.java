package com.epam.tax.dao.impl;

import com.epam.tax.dao.mapper.UserRowMapper;
import com.epam.tax.database.DBManager;
import com.epam.tax.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.tax.constants.Queries.*;

public class UserDao {
    private static int noOfRecords;

    public static boolean insertUser(User user) throws SQLException {

        try (var connection = DBManager.getInstance().getConnection();
             var ps = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getSurname());
            ps.setLong(5, user.getRole().getId());
            ps.executeUpdate();
            try (var rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    long userId = rs.getLong(1);
                    user.setId(userId);
                    return true;
                }
                return false;
            }
        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    public static List<User> findAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();

        try (var connection = DBManager.getInstance().getConnection();
             var statement = connection.createStatement();
             var rs = statement.executeQuery(SQL_GET_USERS)) {
            UserRowMapper mapper = new UserRowMapper();
            while (rs.next()) {
                User user = mapper.mapRow(rs);
                users.add(user);
            }
        } catch (SQLException throwables) {
            throw new SQLException();
        }

        return users;
    }

    public static User getUserById(Long id) throws SQLException {
        User user = null;
        try (var con = DBManager.getInstance().getConnection();
             var pstmt = con.prepareStatement(SQL__FIND_USER_BY_ID)) {
            UserRowMapper mapper = new UserRowMapper();
            pstmt.setLong(1, id);
            try (var rs = pstmt.executeQuery()) {
                if (rs.next())
                    user = mapper.mapRow(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    public static User getUserByLogin(String login) throws SQLException {
        User user = null;

        try (var connection = DBManager.getInstance().getConnection();
             var ps = connection.prepareStatement(SQL__FIND_USER_BY_LOGIN)) {
            UserRowMapper mapper = new UserRowMapper();
            ps.setString(1, login);
            try (var rs = ps.executeQuery()) {
                if (rs.next())
                    user = mapper.mapRow(rs);
            }
        } catch (SQLException throwables) {
            throw new SQLException();
        }
        return user;
    }

    public static boolean updateUser(User user) throws SQLException {
        try (var connection = DBManager.getInstance().getConnection();
             var ps = connection.prepareStatement(SQL_UPDATE_USER)
        ) {
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getName());
            ps.setString(3, user.getLogin());
            ps.setString(4, user.getSurname());
            ps.setLong(5, user.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new SQLException();
        }
    }

    public static boolean deleteUser(User user) throws SQLException {
        try (var connection = DBManager.getInstance().getConnection();
             var ps = connection.prepareStatement(DELETE_USER)) {
            ps.setLong(1, user.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new SQLException();
        }
    }

    public static List<User> viewAllEmployees(int offset, int noOfRecords) throws SQLException {

        String query = "select SQL_CALC_FOUND_ROWS schema_tax.users.*, schema_tax.roles.role_name FROM schema_tax.users LEFT JOIN schema_tax.roles ON roles.id=users.role_id limit "
                + offset + ", " + noOfRecords;
        List<User> list = new ArrayList<User>();
        User employee = null;
        try (var connection = DBManager.getInstance().getConnection();
             var ps = connection.createStatement()) {
            UserRowMapper mapper = new UserRowMapper();
            try (var rs = ps.executeQuery(query)) {
                while (rs.next()) {

                    employee = mapper.mapRow(rs);
                    list.add(employee);
                }

            }   try (ResultSet resultSet = ps.executeQuery("SELECT FOUND_ROWS()")) {

                if (resultSet.next())
                    UserDao.noOfRecords = resultSet.getInt(1);

            }
        }
        return list;
    }

    public static int getNoOfRecords() {
        return noOfRecords;
    }
}

