package com.example.PortalMicroservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${app.security.token.secret}")
    private String SECRET_KEY;

    public boolean isTokenValid(String authHeader, String expectedUsername) {
        String token = authHeader.replace("Bearer ", "");
        final String username = extractUsername(token);
        return (username.equals(expectedUsername) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        Claims claims = Jwts.parser().
                setSigningKey(SECRET_KEY).
                parseClaimsJws(token).
                getBody();

        return claims.getSubject();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }
}