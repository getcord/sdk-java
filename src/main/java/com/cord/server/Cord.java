package com.cord.server;

import com.cord.types.ClientAuthTokenData;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Cord {
    /**
     * This method should be used to sign a JWT token and send it
     * to the frontend part of the application, which will use the
     * said token to authenticate the web components.
     *
     * @param app_id The application id, found in console.cord.com
     * @param secret The secret corresponding to the application id,
     *               also found in console.cord.cord
     * @param payload The payload of type ClientAuthTokenData which
     *                should contain the user and organization information
     *                for the particular user you want to authenticate
     * @return A signed JWT token which should successfully authenticate
     * a user in the frontend web components
     * **/
    public static String getClientAuthToken(String app_id, String secret, ClientAuthTokenData payload) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim("app_id", app_id)
                .claim("user_id", payload.user_id)
                .claim("organization_id", payload.organization_id)
                .claim("user_details", payload.user_details == null ? payload.user_details : "{}")
                .claim("organization_details", payload.organization_details == null ? payload.organization_details : "{}")
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(1, ChronoUnit.MINUTES)))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    /**
     * This method should be used to sign a JWT token which is required
     * to authenticate for the Cord REST APIs. The relevant APIs are
     * used to sync your users and organizations with the Cord data
     *
     * @param app_id The application id, found in console.cord.com
     * @param secret The secret corresponding to the application id,
     *               also found in console.cord.cord
     * @return A signed JWT token which should successfully authenticate
     * all REST API requests
     * **/
    public static String getServerAuthToken(String app_id, String secret) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim("app_id", app_id)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(1, ChronoUnit.MINUTES)))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes(StandardCharsets.UTF_8))
                .compact();
    }
}