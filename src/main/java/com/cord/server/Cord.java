package com.cord.server;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.jackson.io.JacksonSerializer;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

public class Cord {
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .enable(SerializationFeature.INDENT_OUTPUT);

    private Cord() {
    }

    /**
     * This method should be used to sign a JWT token and send it
     * to the frontend part of the application, which will use the
     * said token to authenticate the web components.
     *
     * @param appId   The application id, found in console.cord.com
     * @param secret  The secret corresponding to the application id,
     *                also found in console.cord.cord
     * @param payload The payload of type ClientAuthTokenData which
     *                should contain the user and organization information
     *                for the particular user you want to authenticate
     * @return A signed JWT token which should successfully authenticate
     *         a user in the frontend web components
     **/
    public static String getClientAuthToken(String appId, String secret, ClientAuthTokenData payload) {
        byte[] paddedSecret = padSecretTo64Bytes(secret.getBytes(StandardCharsets.UTF_8));
        Key key = new SecretKeySpec(paddedSecret, SignatureAlgorithm.HS512.getJcaName());
        Instant now = Instant.now();
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .serializeToJsonWith(new JacksonSerializer<>(mapper))
                .claim("app_id", appId)
                .claim("user_id", payload.getUserId())
                .claim("organization_id", payload.getOrganizationId())
                .claim("user_details", payload.getUserDetails())
                .claim("organization_details", payload.getOrganizationDetails())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(1, ChronoUnit.MINUTES)))
                .signWith(key)
                .compact();
    }

    /**
     * This method should be used to sign a JWT token which is required
     * to authenticate for the Cord REST APIs. The relevant APIs are
     * used to sync your users and organizations with the Cord data
     *
     * @param appId  The application id, found in console.cord.com
     * @param secret The secret corresponding to the application id,
     *               also found in console.cord.cord
     * @return A signed JWT token which should successfully authenticate
     *         all REST API requests
     **/
    public static String getServerAuthToken(String appId, String secret) {
        byte[] paddedSecret = padSecretTo64Bytes(secret.getBytes(StandardCharsets.UTF_8));
        Key key = new SecretKeySpec(paddedSecret, SignatureAlgorithm.HS512.getJcaName());
        Instant now = Instant.now();
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim("app_id", appId)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(1, ChronoUnit.MINUTES)))
                .signWith(key)
                .compact();
    }

    /**
     * This method should be used to synchronise users from your
     * application into Cord. It handles the authentication for
     * you and will also perform an API call.
     *
     * @param appId  The application id, found in console.cord.com
     * @param secret The secret corresponding to the application id,
     *               also found in console.cord.cord
     * @param userId The ID - usually a UUID - of the user you are
     *               syncing
     * @param user   The user object of the current user. Please do
     *               use the provided PlatformUserVariables type
     *               provided by this library
     * @return The response body of the API call to the Cord server,
     *         indicating whether your call succeeded and if yes, how many
     *         users were synced.
     * @throws CordException if the the API call fails for any reason
     **/
    public static String syncCordUser(
            String appId,
            String secret,
            String userId,
            PlatformUserVariables user) throws CordException {
        String authToken = getServerAuthToken(appId, secret);

        String requestBody;
        try {
            requestBody = mapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(user);
        } catch (JsonProcessingException e) {
            // Our user objects should always be serializable
            throw new AssertionError(e);
        }
        System.out.println(requestBody);
        HttpRequest request = HttpRequest.newBuilder(
                URI.create(String.format("https://api.cord.com/v1/users/%s", userId)))
                .header("authorization", String.format("Bearer %s", authToken))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new CordException("Could not sync Cord user", e);
        }

        return response.body();
    }

    /**
     * This method should be used to synchronise organizations
     * from your application into Cord. It handles the
     * authentication for you and will also perform an API call.
     *
     * @param appId        The application id, found in console.cord.com
     * @param secret       The secret corresponding to the application id,
     *                     also found in console.cord.cord
     * @param orgId        The ID - usually a UUID - of the organization you are
     *                     syncing
     * @param organization The organization object of the current user.
     *                     Please do use the provided PlatformOrganizationVariables
     *                     type provided by this library
     * @return The response body of the API call to the Cord server,
     *         indicating whether your call succeeded and if yes, how many
     *         organizations were synced.
     * @throws CordException if the the API call fails for any reason
     **/
    public static String syncCordOrganization(
            String appId,
            String secret,
            String orgId,
            PlatformOrganizationVariables organization) throws CordException {
        String authToken = getServerAuthToken(appId, secret);

        String requestBody;
        try {
            requestBody = mapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(organization);
        } catch (JsonProcessingException e) {
            // Our org objects should always be serializable
            throw new AssertionError(e);
        }
        HttpRequest request = HttpRequest.newBuilder(
                URI.create(String.format("https://api.cord.com/v1/organizations/%s", orgId)))
                .header("authorization", String.format("Bearer %s", authToken))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new CordException("Could not sync Cord organization", e);
        }

        return response.body();
    }

    /**
     * This method should be used to batch synchronise
     * users and organizations from your application into Cord.
     * It handles the authentication for you and will also perform
     * an API call.
     *
     * Please do make sure to include the ID field in both the user
     * and organization.
     *
     * @param appId         The application id, found in console.cord.com
     * @param secret        The secret corresponding to the application id,
     *                      also found in console.cord.cord
     * @param users         A list of PlatformUserVariables type - make sure
     *                      to include the ID for each
     * @param organizations A list of PlatformOrganizationVariables type
     *                      - make sure to include the ID for each
     * @return The response body of the API call to the Cord server,
     *         indicating whether your call succeeded and if yes, how many
     *         users and organizations were synced.
     * @throws CordException if the the API call fails for any reason
     **/
    public static String batchSyncCordUsersAndOrganizations(
            String appId,
            String secret,
            List<PlatformUserVariables> users,
            List<PlatformOrganizationVariables> organizations) throws CordException {
        String authToken = getServerAuthToken(appId, secret);

        String requestBody;
        try {
            requestBody = String.format(
                    "{ \"organizations\": %s, \"users\": %s }",
                    mapper.writerWithDefaultPrettyPrinter().writeValueAsString(organizations),
                    mapper.writerWithDefaultPrettyPrinter().writeValueAsString(users));
        } catch (JsonProcessingException e) {
            // Our user and org objects should always be serializable
            throw new AssertionError(e);
        }

        HttpRequest request = HttpRequest.newBuilder(
                URI.create("https://api.cord.com/v1/batch"))
                .header("authorization", String.format("Bearer %s", authToken))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new CordException("Could not batch sync Cord users and organizations", e);
        }

        return response.body();
    }

    private static final int MIN_SECRET_LENGTH = 64;

    private static byte[] padSecretTo64Bytes(byte[] secret) {
        if (MIN_SECRET_LENGTH <= secret.length) {
            return secret;
        }
        byte[] newData = new byte[MIN_SECRET_LENGTH];
        System.arraycopy(secret, 0, newData, 0, secret.length);
        return newData;
    }
}