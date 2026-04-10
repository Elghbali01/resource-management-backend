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
        // Spring Security vérifie email + mot de passe (BCrypt automatiquement)
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

        // Vérification statut ACTIVE
        if (!userDetails.isEnabled()) {
            throw new UserInactiveException("Votre compte est désactivé. Contactez l'administrateur.");
        }

        String token = jwtUtils.generateToken(userDetails);

        // on return l'id /nom complect / email et aussi le role !!! (role pour diriger vers leur dashbord)
        return new LoginResponse(
                token,
                userDetails.getId(),
                userDetails.getNom(),
                userDetails.getPrenom(),
                userDetails.getEmail(),
                userDetails.getRoleStr()
        );
    }
}