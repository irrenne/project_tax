package com.epam.tax.services.impl;

import com.epam.tax.dao.impl.UserDaoImpl;
import com.epam.tax.entities.Role;
import com.epam.tax.entities.User;
import com.epam.tax.services.UserService;
import com.epam.tax.servlets.util.PasswordHash;
import com.epam.tax.servlets.util.Validation;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final Validation validation;
    private final UserDaoImpl userDao;

    public UserServiceImpl() {
        validation = new Validation();
        userDao = new UserDaoImpl();
    }

    @Override
    public boolean insert(User user) {
        return userDao.insertUser(user);
    }

    @Override
    public boolean update(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public boolean delete(User user) {
        return userDao.deleteUser(user);
    }

    @Override
    public User getById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    public User getByLogin(String login) {
        return userDao.getUserByLogin(login);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAllUsers();
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
    public boolean isValid(String login, String password, User user) {
        if (checkValidInput(login) && checkValidInput(password)) {
            return (user != null && PasswordHash.checkPassword(password, user.getPassword()) && login.equals(user.getLogin()));
        }
        return false;
    }

    @Override
    public boolean checkValidUserInputRegister(String name, String surname, String login, String password) {
        return validation.validName(name) && validation.validSurname(surname)
                && validation.validLogin(login) && validation.validPassword(password);
    }

    @Override
    public boolean checkValidInput(String input) {
        return input != null && !input.isEmpty();
    }

}
