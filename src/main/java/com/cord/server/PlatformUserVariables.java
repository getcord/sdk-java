package com.cord.server;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Variables used to describe a platform user
 *
 * The only required variable is "email".
 * **/
public class PlatformUserVariables {
    @JsonProperty("email")
    private final String email;
    @JsonProperty("id")
    private final String id;
    @JsonProperty("name")
    private final String name;
    @JsonProperty("profile_picture_url")
    private final String profilePictureUrl;
    @JsonProperty("status")
    private final Status status;
    @JsonProperty("first_name")
    private final String firstName;
    @JsonProperty("last_name")
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

    public String getEmail() {
        return email;
    }
    public String getId() { return id; }
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
