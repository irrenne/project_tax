package com.epam.tax.entities;

public enum Type {
    TYPE1("тип1"),
    TYPE2("тип2"),
    TYPE3("тип3"),
    TYPE4("тип4");

    private final String localization;

    Type(String localization) {
        this.localization = localization;
    }

    public String getLocalization() {
        return localization;
    }
}
