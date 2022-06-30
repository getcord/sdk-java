package com.cord.types;
/**
 * Marks the status of a user or an organization.
 * Mainly used to "soft" delete entities.
 * **/
public enum Status {
    ACTIVE("active"),
    DELETED("deleted");

    public final String s;
    private Status(String s) {
        this.s = s;
    }
}
