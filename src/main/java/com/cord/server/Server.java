package com.cord.server;

import com.cord.types.ClientAuthTokenData;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Server {

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
                .setExpiration(Date.from(now.plus(10, ChronoUnit.MINUTES)))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

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