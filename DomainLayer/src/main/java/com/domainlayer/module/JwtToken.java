package com.domainlayer.module;

import com.google.gson.Gson;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtToken {
    private JwtToken() {
        throw new AssertionError();
    }

    private static String GetKeyString(final String passwd) {
        String passwdReverse = new StringBuilder(passwd).reverse().toString();
        byte[] keyBytes = DatatypeConverter.parseBase64Binary(passwd);
        byte[] keyReverseBytes = DatatypeConverter.parseBase64Binary(passwdReverse);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            outputStream.write(keyBytes);
            outputStream.write(keyReverseBytes);
            outputStream.write(keyBytes);
            outputStream.write(keyReverseBytes);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    private static Key StringToKey(final String key) {
        byte[] decodedKey = Base64.getDecoder().decode(key);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "HMACSHA256");
    }

    private static Map DecodeToken(final String token) {
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        Map<String, Object> header = new Gson().fromJson(new String(decoder.decode(chunks[0])), HashMap.class);
        Map<String, Object> payload = new Gson().fromJson(new String(decoder.decode(chunks[1])), HashMap.class);
        return Map.of(
                "header", header,
                "payload", payload
        );
    }

    public static String GenerateToken(final String subject, final String passwd) {
        String secretKey = GetKeyString(passwd);
        JwtBuilder jwtBuilder = Jwts.builder()
                .setHeaderParam("key", secretKey)
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(StringToKey(secretKey));

        return jwtBuilder.compact();
    }

    public static void ValidateToken(final String token) throws JwtException {
        var decodedToken = DecodeToken(token);
        Map header = (Map) decodedToken.get("header");
        Key signingKey = StringToKey((String) header.get("key"));
        Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token);
    }
}
