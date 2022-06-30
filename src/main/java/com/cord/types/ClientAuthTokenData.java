package com.cord.types;
/**
 * Variables used for the information needed in the
 * authentication token data
 *
 * The required variables are "app_id", "user_id" and "organization_id".
 * **/
public class ClientAuthTokenData {
    public final String app_id;
    public final String user_id;
    public final String organization_id;
    public final PlatformUserVariables user_details;
    public final PlatformOrganizationVariables organization_details;

    private ClientAuthTokenData(ClientAuthTokenDataBuilder builder) {
        this.app_id = builder.app_id;
        this.user_id = builder.user_id;
        this.organization_id = builder.organization_id;
        this.user_details = builder.user_details;
        this.organization_details = builder.organization_details;
    }

    public String getApp_id() {
        return app_id;
    }
    public String getUser_id() {
        return user_id;
    }
    public String getOrganization_id() {
        return organization_id;
    }
    public PlatformUserVariables getUser_details() {
        return user_details;
    }
    public PlatformOrganizationVariables getOrganization_details() {
        return organization_details;
    }


    public static class ClientAuthTokenDataBuilder {
        private final String app_id;
        private final String user_id;
        private final String organization_id;
        private PlatformUserVariables user_details;
        private PlatformOrganizationVariables organization_details;

        public ClientAuthTokenDataBuilder(String app_id, String user_id, String organization_id) {
            this.app_id = app_id;
            this.user_id = user_id;
            this.organization_id = organization_id;
        }
        public ClientAuthTokenDataBuilder user_details(PlatformUserVariables user_details) {
            this.user_details = user_details;
            return this;
        }
        public ClientAuthTokenDataBuilder organization_details(PlatformOrganizationVariables organization_details) {
            this.organization_details = organization_details;
            return this;
        }

        public ClientAuthTokenData build() {
            return new ClientAuthTokenData(this);
        }
    }
}
