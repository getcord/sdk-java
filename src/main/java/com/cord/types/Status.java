package com.cord.types;

public enum Status {
    ACTIVE("active"),
    DELETED("deleted");

    public final String s;
    private Status(String s) {
        this.s = s;
    }
}
