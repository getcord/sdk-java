package com.cord.server;

/**
 * Variables used to describe a platform user
 *
 * The only required variable is "email".
 * **/
public class PlatformUserVariables {
    private final String email;
    private final String name;
    private final String profilePictureUrl;
    private final Status status;
    private final String firstName;
    private final String lastName;

    private PlatformUserVariables(PlatformUserVariablesBuilder builder) {
        this.email = builder.email;
        this.name = builder.name;
        this.profilePictureUrl = builder.profilePictureUrl;
        this.status = builder.status;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
    }

    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }
    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }
    public Status getStatus() {
        return status;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

    public static class PlatformUserVariablesBuilder {
        private final String email;
        private String name;
        private String profilePictureUrl;
        private Status status;
        private String firstName;
        private String lastName;

        public PlatformUserVariablesBuilder(String email) {
            this.email = email;
        }
        public PlatformUserVariablesBuilder name(String name) {
            this.name = name;
            return this;
        }
        public PlatformUserVariablesBuilder profilePictureUrl(String profilePictureUrl) {
            this.profilePictureUrl = profilePictureUrl;
            return this;
        }
        public PlatformUserVariablesBuilder address(Status status) {
            this.status = status;
            return this;
        }
        public PlatformUserVariablesBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        public PlatformUserVariablesBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public PlatformUserVariables build() {
            return new PlatformUserVariables(this);
        }
    }
}
