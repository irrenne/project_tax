package com.epam.tax.servlets.util;

public class Validation {
    private static final String VALID_NAME = "^[А-Я][-А-я]+";
    private static final String VALID_SURNAME = "^[А-Я][-А-я]+";
    private static final String VALID_LOGIN = "^(?=.*[A-Za-z])(d*)[A-Za-z\\d]{4,32}$";
    private static final String VALID_PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,32}$";

    public boolean validName(String input) {
        return input != null && !input.isEmpty() && input.matches(VALID_NAME);
    }

    public boolean validSurname(String input) {
        return input != null && !input.isEmpty() && input.matches(VALID_SURNAME);
    }

    public boolean validLogin(String input) {
        return input != null && !input.isEmpty() && input.matches(VALID_LOGIN);
    }

    public boolean validPassword(String input) {
        return input != null && !input.isEmpty() && input.matches(VALID_PASSWORD);
    }

}
