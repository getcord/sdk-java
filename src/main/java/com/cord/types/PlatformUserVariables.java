package com.cord.types;

public class PlatformUserVariables {
    private final String email;
    private final String name;
    private final String profile_picture_url;
    private final Status status;
    private final String first_name;
    private final String last_name;

    private PlatformUserVariables(PlatformUserVariablesBuilder builder) {
        this.email = builder.email;
        this.name = builder.name;
        this.profile_picture_url = builder.profile_picture_url;
        this.status = builder.status;
        this.first_name = builder.first_name;
        this.last_name = builder.last_name;
    }

    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }
    public String getProfile_picture_url() {
        return profile_picture_url;
    }
    public Status getStatus() {
        return status;
    }
    public String getFirst_name() {
        return first_name;
    }
    public String getLast_name() {
        return last_name;
    }

    public static class PlatformUserVariablesBuilder {
        private final String email;
        private String name;
        private String profile_picture_url;
        private Status status;
        private String first_name;
        private String last_name;

        public PlatformUserVariablesBuilder(String email) {
            this.email = email;
        }
        public PlatformUserVariablesBuilder name(String name) {
            this.name = name;
            return this;
        }
        public PlatformUserVariablesBuilder phone(String profile_picture_url) {
            this.profile_picture_url = profile_picture_url;
            return this;
        }
        public PlatformUserVariablesBuilder address(Status status) {
            this.status = status;
            return this;
        }
        public PlatformUserVariablesBuilder first_name(String first_name) {
            this.first_name = first_name;
            return this;
        }
        public PlatformUserVariablesBuilder last_name(String last_name) {
            this.last_name = last_name;
            return this;
        }

        public PlatformUserVariables build() {
            return new PlatformUserVariables(this);
        }
    }
}
