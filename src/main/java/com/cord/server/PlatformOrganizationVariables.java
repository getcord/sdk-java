package com.cord.server;

import java.util.Collections;
import java.util.List;

/**
 * Variables used to describe a platform organization
 *
 * The only required variable is "name".
 * **/
public class PlatformOrganizationVariables {
    private final String name;
    private final Status status;
    private final List<String> members;

    private PlatformOrganizationVariables(PlatformOrganizationVariablesBuilder builder) {
        this.name = builder.name;
        this.status = builder.status;
        this.members = builder.members;
    }

    private String getName() {
        return name;
    }
    private Status getStatus() {
        return status;
    }
    private List<String> getMembers() {
        return members;
    }


    public static class PlatformOrganizationVariablesBuilder {
        private final String name;
        private Status status;
        private List<String> members;

        public PlatformOrganizationVariablesBuilder(String name) {
            this.name = name;
        }
        public PlatformOrganizationVariablesBuilder status(Status status) {
            this.status = status;
            return this;
        }
        public PlatformOrganizationVariablesBuilder members(List<String> members) {
            this.members = Collections.unmodifiableList(members);
            return this;
        }

        public PlatformOrganizationVariables build() {
            return new PlatformOrganizationVariables(this);
        }
    }
}
