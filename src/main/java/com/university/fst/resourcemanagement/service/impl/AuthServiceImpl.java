package com.university.fst.resourcemanagement.service.impl;

import com.university.fst.resourcemanagement.dto.ChangePasswordRequest;
import com.university.fst.resourcemanagement.dto.LoginRequest;
import com.university.fst.resourcemanagement.dto.LoginResponse;
import com.university.fst.resourcemanagement.entity.User;
import com.university.fst.resourcemanagement.exception.UserInactiveException;
import com.university.fst.resourcemanagement.repository.UserRepository;
import com.university.fst.resourcemanagement.security.JwtUtils;
import com.university.fst.resourcemanagement.security.UserDetailsImpl;
import com.university.fst.resourcemanagement.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils              jwtUtils;
    private final UserRepository        userRepository;
    private final PasswordEncoder       passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           JwtUtils jwtUtils,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils              = jwtUtils;
        this.userRepository        = userRepository;
        this.passwordEncoder       = passwordEncoder;
    }

    // ── LOGIN ─────────────────────────────────────────────────────────────────
    @Override
    public LoginResponse login(LoginRequest request) {

        // 1. Vérification credentials
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

        // 2. Vérification statut ACTIVE
        if (!userDetails.isEnabled()) {
            throw new UserInactiveException("Votre compte est désactivé. Contactez l'administrateur.");
        }

        String roleReel = userDetails.getRoleStr();

        // 3. Récupération du flag mustChangePassword
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        boolean mustChange = user.isMustChangePassword();

        // 4. Cas CHEF_DEPARTEMENT — double rôle possible
        if ("CHEF_DEPARTEMENT".equals(roleReel)) {

            String roleChoisi = request.getRoleChoisi();

            // Étape 1 : le front n'a pas encore envoyé de roleChoisi → popup
            if (roleChoisi == null || roleChoisi.isBlank()) {
                return new LoginResponse(
                        userDetails.getId(),
                        userDetails.getNom(),
                        userDetails.getPrenom(),
                        userDetails.getEmail(),
                        true  // hasDoubleRole
                );
            }

            // Étape 2 : validation du choix
            if (!roleChoisi.equals("CHEF_DEPARTEMENT") && !roleChoisi.equals("ENSEIGNANT")) {
                throw new RuntimeException("Rôle choisi invalide. Valeurs acceptées : CHEF_DEPARTEMENT, ENSEIGNANT");
            }

            String token = jwtUtils.generateTokenWithRole(userDetails, roleChoisi);
            return new LoginResponse(
                    token,
                    userDetails.getId(),
                    userDetails.getNom(),
                    userDetails.getPrenom(),
                    userDetails.getEmail(),
                    roleChoisi,
                    mustChange
            );
        }

        // 5. Cas normal (tous les autres rôles)
        String token = jwtUtils.generateToken(userDetails);
        return new LoginResponse(
                token,
                userDetails.getId(),
                userDetails.getNom(),
                userDetails.getPrenom(),
                userDetails.getEmail(),
                roleReel,
                mustChange
        );
    }

    // ── CHANGE PASSWORD ───────────────────────────────────────────────────────
    @Override
    public void changePassword(String email, ChangePasswordRequest request) {

        // 1. Récupérer l'utilisateur
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        // 2. Vérifier l'ancien mot de passe
        if (!passwordEncoder.matches(request.getAncienMotDePasse(), user.getPassword())) {
            throw new RuntimeException("Ancien mot de passe incorrect");
        }

        // 3. Le nouveau mot de passe doit être différent de l'ancien
        if (passwordEncoder.matches(request.getNouveauMotDePasse(), user.getPassword())) {
            throw new RuntimeException("Le nouveau mot de passe doit être différent de l'ancien");
        }

        // 4. Vérifier la confirmation
        if (!request.getNouveauMotDePasse().equals(request.getConfirmerMotDePasse())) {
            throw new RuntimeException("Le nouveau mot de passe et la confirmation ne correspondent pas");
        }

        // 5. Sauvegarder + lever l'obligation
        user.setPassword(passwordEncoder.encode(request.getNouveauMotDePasse()));
        user.setMustChangePassword(false);
        userRepository.save(user);
    }
}