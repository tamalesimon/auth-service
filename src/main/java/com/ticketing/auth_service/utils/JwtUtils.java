package com.ticketing.auth_service.utils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    private final Key secretKey;

    public JwtUtils(@Value("${security.secret-key}") String secretKeyString) {
        byte[] keyBytes = Base64.getDecoder().decode(secretKeyString.getBytes(StandardCharsets.UTF_8));
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email, String role, int expirationTime) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime * 60 * 1000))
                .claim("role", role)
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token, String username) {
        String email = getEmailFromToken(token);
        return (email.equals(username) && !isTokenExpired(token));
    }

    public void verifyToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
        } catch (io.jsonwebtoken.SignatureException e) {
            throw new JwtException("Invalid JWT signature", e);
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            throw new JwtException("JWT token has expired", e);
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            throw new JwtException("Malformed JWT token", e);
        } catch (Exception e) {
            throw new JwtException("Something went wrong with the JWT token", e);
        }
    }

    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getRoleFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

}
