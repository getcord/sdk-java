package com.cord.server;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Variables used to describe a platform user
 *
 * The only required variable is "email".
 **/
public class PlatformUserVariables {
    private final String email;
    private final String id;
    private final String name;
    private final String profilePictureUrl;
    private final Status status;
    private final String firstName;
    private final String lastName;

    private PlatformUserVariables(PlatformUserVariablesBuilder builder) {
        this.email = builder.email;
        this.id = builder.id;
        this.name = builder.name;
        this.profilePictureUrl = builder.profilePictureUrl;
        this.status = builder.status;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("profile_picture_url")
    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    @JsonProperty("status")
    public Status getStatus() {
        return status;
    }

    @JsonProperty("first_name")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("last_name")
    public String getLastName() {
        return lastName;
    }

    public static class PlatformUserVariablesBuilder {
        private final String email;
        private String id;
        private String name;
        private String profilePictureUrl;
        private Status status;
        private String firstName;
        private String lastName;

        public PlatformUserVariablesBuilder(String email) {
            this.email = email;
        }

        public PlatformUserVariablesBuilder id(String id) {
            this.id = id;
            return this;
        }

        public PlatformUserVariablesBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PlatformUserVariablesBuilder profilePictureUrl(String profilePictureUrl) {
            this.profilePictureUrl = profilePictureUrl;
            return this;
        }

        public PlatformUserVariablesBuilder status(Status status) {
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
