package com.epam.tax.entities;

public enum Status {
    SUBMITTED(0),
    NOT_CONFIRMED(2),
    CONFIRMED(1);

    private final int id;

    Status(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name().toLowerCase();
    }
}
