package com.cord.types;

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
            this.members = members;
            return this;
        }

        public PlatformOrganizationVariables build() {
            return new PlatformOrganizationVariables(this);
        }
    }
}
