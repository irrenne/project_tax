package com.epam.tax.test.utils;

import com.epam.tax.servlets.util.Validation;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidationTest {
    private final Validation validation = new Validation();

    @Test
    public void testValidName() {
        String name = "Ян";
        String name2 = "ян";

        assertTrue(validation.validName(name));
        assertFalse(validation.validName(name2));
    }

    @Test
    public void testValidSurname() {
        String surname = "Бобер";
        String surname2 = "бобер";

        assertTrue(validation.validSurname(surname));
        assertFalse(validation.validSurname(surname2));
    }

    @Test
    public void testValidLogin() {
        String login = "login";
        String login2 = "login2";

        assertTrue(validation.validLogin(login));
        assertTrue(validation.validLogin(login2));
    }

    @Test
    public void testValidPassword() {
        String password = "pass1234";
        String password2 = "pass";

        assertTrue(validation.validPassword(password));
        assertFalse(validation.validPassword(password2));
    }
}
