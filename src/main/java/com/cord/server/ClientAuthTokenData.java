package com.cord.server;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Variables used for the information needed in the
 * authentication token data
 *
 * The required variables are "user_id" and "organization_id".
 **/
public class ClientAuthTokenData {
    private final String userId;
    private final String organizationId;
    private final PlatformUserVariables userDetails;
    private final PlatformOrganizationVariables organizationDetails;

    private ClientAuthTokenData(ClientAuthTokenDataBuilder builder) {
        this.userId = builder.userId;
        this.organizationId = builder.organizationId;
        this.userDetails = builder.userDetails;
        this.organizationDetails = builder.organizationDetails;
    }

    @JsonProperty("user_id")
    public String getUserId() {
        return userId;
    }

    @JsonProperty("organization_id")
    public String getOrganizationId() {
        return organizationId;
    }

    @JsonProperty("user_details")
    public PlatformUserVariables getUserDetails() {
        return userDetails;
    }

    @JsonProperty("organization_details")
    public PlatformOrganizationVariables getOrganizationDetails() {
        return organizationDetails;
    }

    public static class ClientAuthTokenDataBuilder {
        private final String userId;
        private final String organizationId;
        private PlatformUserVariables userDetails;
        private PlatformOrganizationVariables organizationDetails;

        public ClientAuthTokenDataBuilder(String userId, String organizationId) {
            this.userId = userId;
            this.organizationId = organizationId;
        }

        public ClientAuthTokenDataBuilder userDetails(PlatformUserVariables userDetails) {
            this.userDetails = userDetails;
            return this;
        }

        public ClientAuthTokenDataBuilder organizationDetails(PlatformOrganizationVariables organizationDetails) {
            this.organizationDetails = organizationDetails;
            return this;
        }

        public ClientAuthTokenData build() {
            return new ClientAuthTokenData(this);
        }
    }
}
