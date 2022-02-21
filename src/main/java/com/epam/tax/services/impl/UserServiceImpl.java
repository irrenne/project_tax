package com.epam.tax.services.impl;

import com.epam.tax.dao.impl.UserDao;
import com.epam.tax.entities.Role;
import com.epam.tax.entities.User;
import com.epam.tax.services.UserService;
import com.epam.tax.servlets.util.PasswordHash;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {

    @Override
    public boolean insert(User user) throws SQLException {
        return UserDao.insertUser(user);
    }

    @Override
    public boolean update(User user) throws SQLException {
        return UserDao.updateUser(user);
    }

    @Override
    public boolean delete(User user) throws SQLException {
        return UserDao.deleteUser(user);
    }

    @Override
    public User getById(Long id) throws SQLException {
        return UserDao.getUserById(id);
    }

    @Override
    public User getByLogin(String login) throws SQLException {
        return UserDao.getUserByLogin(login);
    }

    @Override
    public List<User> findAll() throws SQLException {
        return UserDao.findAllUsers();
    }

    @Override
    public boolean isInspector(User user) {
        return user.getRole().equals(Role.INSPECTOR);
    }

    @Override
    public boolean isClient(User user) {
        return user.getRole().equals(Role.CLIENT);
    }

    @Override
    public boolean isValid(String login, String password, User user) throws Exception {
        if (checkValidInput(login) && checkValidInput(password)) {
            return (user != null && PasswordHash.checkPassword(password, user.getPassword()));
        }
        throw new Exception("invalid input");
    }

    public boolean checkValidInput(String input) {
        return input != null && !input.isEmpty();
    }

}
