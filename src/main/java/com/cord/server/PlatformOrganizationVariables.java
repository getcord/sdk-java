package com.cord.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Variables used to describe a platform organization
 *
 * The only required variable is "name".
 **/
public class PlatformOrganizationVariables {
    private final String name;
    private final String id;
    private final Status status;
    private final List<String> members;

    private PlatformOrganizationVariables(PlatformOrganizationVariablesBuilder builder) {
        this.name = builder.name;
        this.id = builder.id;
        this.status = builder.status;
        this.members = builder.members;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public List<String> getMembers() {
        return members;
    }

    public static class PlatformOrganizationVariablesBuilder {
        private final String name;
        private String id;
        private Status status;
        private List<String> members;

        public PlatformOrganizationVariablesBuilder(String name) {
            this.name = name;
        }

        public PlatformOrganizationVariablesBuilder id(String id) {
            this.id = id;
            return this;
        }

        public PlatformOrganizationVariablesBuilder status(Status status) {
            this.status = status;
            return this;
        }

        public PlatformOrganizationVariablesBuilder members(List<String> members) {
            this.members = Collections.unmodifiableList(new ArrayList<>(members));
            return this;
        }

        public PlatformOrganizationVariables build() {
            return new PlatformOrganizationVariables(this);
        }
    }
}
