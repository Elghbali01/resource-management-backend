package com.university.fst.resourcemanagement.service.impl;

import com.university.fst.resourcemanagement.dto.LoginRequest;
import com.university.fst.resourcemanagement.dto.LoginResponse;
import com.university.fst.resourcemanagement.exception.UserInactiveException;
import com.university.fst.resourcemanagement.security.JwtUtils;
import com.university.fst.resourcemanagement.security.UserDetailsImpl;
import com.university.fst.resourcemanagement.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        // ── 1. Vérification credentials (email + password BCrypt) ─────────────
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new RuntimeException("Email ou mot de passe incorrect");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // ── 2. Vérification statut ACTIVE ─────────────────────────────────────
        if (!userDetails.isEnabled()) {
            throw new UserInactiveException("Votre compte est désactivé. Contactez l'administrateur.");
        }

        String roleReel = userDetails.getRoleStr();

        // ── 3. Cas CHEF_DEPARTEMENT — double rôle possible ────────────────────
        if ("CHEF_DEPARTEMENT".equals(roleReel)) {

            String roleChoisi = request.getRoleChoisi();

            // Étape 1 : le front n'a pas encore envoyé de roleChoisi
            // → on retourne le flag hasDoubleRole = true SANS token
            // → le front affiche la popup
            if (roleChoisi == null || roleChoisi.isBlank()) {
                return new LoginResponse(
                        userDetails.getId(),
                        userDetails.getNom(),
                        userDetails.getPrenom(),
                        userDetails.getEmail(),
                        true   // hasDoubleRole
                );
            }

            // Étape 2 : le front a envoyé le roleChoisi après la popup
            // → on valide que le choix est cohérent
            if (!roleChoisi.equals("CHEF_DEPARTEMENT") && !roleChoisi.equals("ENSEIGNANT")) {
                throw new RuntimeException("Rôle choisi invalide. Valeurs acceptées : CHEF_DEPARTEMENT, ENSEIGNANT");
            }

            // → on génère le token avec le rôle choisi
            String token = jwtUtils.generateTokenWithRole(userDetails, roleChoisi);
            return new LoginResponse(
                    token,
                    userDetails.getId(),
                    userDetails.getNom(),
                    userDetails.getPrenom(),
                    userDetails.getEmail(),
                    roleChoisi   // rôle choisi dans la réponse (pas le rôle réel)
            );
        }

        // ── 4. Cas normal (tous les autres rôles) ─────────────────────────────
        String token = jwtUtils.generateToken(userDetails);
        return new LoginResponse(
                token,
                userDetails.getId(),
                userDetails.getNom(),
                userDetails.getPrenom(),
                userDetails.getEmail(),
                roleReel
        );
    }
}