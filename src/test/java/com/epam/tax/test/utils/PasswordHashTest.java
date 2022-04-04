package com.epam.tax.test.utils;

import com.epam.tax.servlets.util.PasswordHash;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PasswordHashTest {

    @Test
    public void test(){
        String password = "password";
        String hash = PasswordHash.hash(password);
        assertTrue(PasswordHash.checkPassword(password,hash));
    }
}
