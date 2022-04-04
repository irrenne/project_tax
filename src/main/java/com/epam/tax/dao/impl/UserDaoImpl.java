package com.epam.tax.dao.impl;

import com.epam.tax.dao.UserDao;
import com.epam.tax.dao.mapper.UserRowMapper;
import com.epam.tax.database.DBManager;
import com.epam.tax.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.tax.constants.Queries.*;

public class UserDaoImpl implements UserDao {

    @Override
    public boolean insertUser(User user) {
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
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();

        try (var connection = DBManager.getInstance().getConnection();
             var statement = connection.createStatement();
             var rs = statement.executeQuery(SQL_GET_USERS)) {
            UserRowMapper mapper = new UserRowMapper();
            while (rs.next()) {
                User user = mapper.mapRow(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    @Override
    public User getUserById(Long id) {
        User user = null;
        try (var con = DBManager.getInstance().getConnection();
             var pstmt = con.prepareStatement(SQL__FIND_USER_BY_ID)) {
            UserRowMapper mapper = new UserRowMapper();
            pstmt.setLong(1, id);
            try (var rs = pstmt.executeQuery()) {
                if (rs.next())
                    user = mapper.mapRow(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public User getUserByLogin(String login) {
        User user = null;

        try (var connection = DBManager.getInstance().getConnection();
             var ps = connection.prepareStatement(SQL__FIND_USER_BY_LOGIN)) {
            UserRowMapper mapper = new UserRowMapper();
            ps.setString(1, login);
            try (var rs = ps.executeQuery()) {
                if (rs.next())
                    user = mapper.mapRow(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public boolean updateUser(User user) {
        try (var connection = DBManager.getInstance().getConnection();
             var ps = connection.prepareStatement(SQL_UPDATE_USER)
        ) {
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getName());
            ps.setString(3, user.getLogin());
            ps.setString(4, user.getSurname());
            ps.setLong(5, user.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteUser(User user) {
        try (var connection = DBManager.getInstance().getConnection();
             var ps = connection.prepareStatement(DELETE_USER)) {
            ps.setLong(1, user.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

