package com.domainlayer.module;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtToken {
    private JwtToken() {
        throw new AssertionError();
    }

    public static String GenerateToken(final String email) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String token = Jwts
                .builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(key)
                .compact();
        return token;
    }
}
