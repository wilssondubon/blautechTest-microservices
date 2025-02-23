package com.blautech.auth_microservice.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;


@Component
public class JWTService {
  
    public static final String SECRET = "camellocaballocabellomazda321-09-creedence";

    public void validateToken(final String token) {
        Jwts.parser().verifyWith((SecretKey) getSignKey()).build().parseSignedClaims(token);
    }

    public String generateToken(Integer userId, String email) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("userId", userId);
        return createToken(claims, email);
    }

    private String createToken(Map<String, Object> claims, final String email) {
        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey())
                .compact();
    }

    private Key getSignKey() {
        byte[] textBytes = SECRET.getBytes();
        return Keys.hmacShaKeyFor(textBytes);
    }
    
}
