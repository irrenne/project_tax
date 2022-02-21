package com.epam.tax.servlets.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHash {
    public PasswordHash() {
    }

    public static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}
