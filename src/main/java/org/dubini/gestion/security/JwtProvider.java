package org.dubini.gestion.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.dubini.gestion.config.JwtProperties;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {

    private final SecretKey key;
    private final long jwtExpirationMs;

    public JwtProvider(JwtProperties props) {
        String secret = props.getJwtSecret();
        SecretKey computedKey;
        try {
            computedKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
        } catch (IllegalArgumentException e) {
            computedKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        }
        this.key = computedKey;
        this.jwtExpirationMs = props.getJwtExpirationMs() > 0 ? props.getJwtExpirationMs() : 86400000L;
    }

    public String generateToken() {
        return Jwts.builder()
                .subject("backoffice")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String subject = claims.getSubject();
            Date expiration = claims.getExpiration();
            Date now = new Date();

            return "backoffice".equals(subject) && expiration.after(now);
        } catch (ExpiredJwtException e) {
            System.err.println("ERROR: Token expired: " + e.getMessage());
            return false;
        } catch (MalformedJwtException e) {
            System.err.println("ERROR: Malformed JWT token: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("ERROR: Token validation failed: " + e.getMessage());
            return false;
        }
    }
}
