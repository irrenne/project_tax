package com.epam.tax.entities;

public enum Role {
    INSPECTOR(1), CLIENT(2);

    private final long id;

    Role(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name().toLowerCase();
    }
}
