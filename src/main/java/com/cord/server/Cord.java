package com.cord.server;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;

public class Cord {
    /**
     * This method should be used to sign a JWT token and send it
     * to the frontend part of the application, which will use the
     * said token to authenticate the web components.
     *
     * @param appId The application id, found in console.cord.com
     * @param secret The secret corresponding to the application id,
     *               also found in console.cord.cord
     * @param payload The payload of type ClientAuthTokenData which
     *                should contain the user and organization information
     *                for the particular user you want to authenticate
     * @return A signed JWT token which should successfully authenticate
     * a user in the frontend web components
     * **/
    public static String getClientAuthToken(String appId, String secret, ClientAuthTokenData payload) {
        byte[] paddedSecret = padSecretTo64Bytes(secret.getBytes(StandardCharsets.UTF_8));
        Key key = new SecretKeySpec(paddedSecret, SignatureAlgorithm.HS512.getJcaName());
        Instant now = Instant.now();
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim("app_id", appId)
                .claim("user_id", payload.userId)
                .claim("organization_id", payload.organizationId)
                .claim("user_details", payload.userDetails == null ? payload.userDetails : "{}")
                .claim("organization_details", payload.organizationDetails == null ? payload.organizationDetails : "{}")
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
     * @param appId The application id, found in console.cord.com
     * @param secret The secret corresponding to the application id,
     *               also found in console.cord.cord
     * @return A signed JWT token which should successfully authenticate
     * all REST API requests
     * **/
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

    private static byte[] padSecretTo64Bytes(byte[] secret) {
        int BYTES = 64;
        if (BYTES <= secret.length) {
            return secret;
        }
        byte[] newData = new byte[BYTES];
        System.arraycopy(secret, 0, newData, 0, secret.length);
        for (int i = secret.length; i < BYTES; i++) {
            newData[i] = 0;
        }

        return newData;
    }
}