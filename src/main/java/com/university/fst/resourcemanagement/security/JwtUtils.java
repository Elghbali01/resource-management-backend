package com.university.fst.resourcemanagement.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms}")
    private long jwtExpirationMs;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // ── Token normal (rôle réel du user) ──────────────────────────────────────
    public String generateToken(UserDetailsImpl userDetails) {
        return buildToken(
                userDetails.getUsername(),
                userDetails.getRoleStr(),
                userDetails.getNom(),
                userDetails.getPrenom()
        );
    }

    // ── Token avec rôle choisi (popup chef/enseignant) ────────────────────────
    public String generateTokenWithRole(UserDetailsImpl userDetails, String roleChoisi) {
        return buildToken(
                userDetails.getUsername(),
                roleChoisi,
                userDetails.getNom(),
                userDetails.getPrenom()
        );
    }

    // ── Builder commun ────────────────────────────────────────────────────────
    private String buildToken(String email, String role, String nom, String prenom) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .claim("nom", nom)
                .claim("prenom", prenom)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getEmailFromToken(String token) {
        return getClaims(token).getSubject();
    }

    public String getRoleFromToken(String token) {
        Object role = getClaims(token).get("role");
        return role != null ? role.toString() : null;
    }

    public String getNomFromToken(String token) {
        Object nom = getClaims(token).get("nom");
        return nom != null ? nom.toString() : null;
    }

    public String getPrenomFromToken(String token) {
        Object prenom = getClaims(token).get("prenom");
        return prenom != null ? prenom.toString() : null;
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}