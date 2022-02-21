package com.epam.tax.services;

import com.epam.tax.entities.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    boolean insert(User user) throws SQLException;

    boolean update(User user) throws SQLException;

    boolean delete(User user) throws SQLException;

    User getById(Long id) throws SQLException;

    User getByLogin(String login) throws SQLException;

    List<User> findAll() throws SQLException;

    boolean isInspector(User user);

    boolean isClient(User user);

    boolean isValid(String login, String password, User user) throws Exception;
}
