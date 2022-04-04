package com.epam.tax.test.services;

import com.epam.tax.entities.Role;
import com.epam.tax.entities.User;
import com.epam.tax.services.impl.UserServiceImpl;
import com.epam.tax.servlets.util.PasswordHash;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserServiceTest {
    private final UserServiceImpl userService = new UserServiceImpl();

    @Test
    public void validationTest() {
        String login = "login";
        String password = "password";
        User user = User.createUser(login, "name", "surname", PasswordHash.hash(password));

        assertTrue(userService.isValid(login, password, user));
        assertFalse(userService.isValid(login + "1", password, user));
    }

    @Test
    public void isClientTest() {
        User user = User.createUser("login", "name", "surname", "password");
        user.setRole(Role.CLIENT);
        assertTrue(userService.isClient(user));

        user.setRole(Role.INSPECTOR);
        assertFalse(userService.isClient(user));
    }

    @Test
    public void isInspectorTest() {
        User user = User.createUser("login", "name", "surname", "password");
        user.setRole(Role.CLIENT);
        assertFalse(userService.isInspector(user));

        user.setRole(Role.INSPECTOR);
        assertTrue(userService.isInspector(user));
    }

    @Test
    public void checkValidInputRegisterTest() {
        String name = "Ян";
        String surname = "Бобер";
        String login = "login";
        String password = "pass1234";
        assertTrue(userService.checkValidUserInputRegister(name, surname, login, password));

        String name2 = "ян";
        String surname2 = "бобер";
        String login2 = "login2";
        String password2 = "pass";
        assertFalse(userService.checkValidUserInputRegister(name2, surname2, login2, password2));
    }
}
