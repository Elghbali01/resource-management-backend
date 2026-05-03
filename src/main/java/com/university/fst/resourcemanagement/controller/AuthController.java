package com.university.fst.resourcemanagement.controller;

import com.university.fst.resourcemanagement.dto.ChangePasswordRequest;
import com.university.fst.resourcemanagement.dto.LoginRequest;
import com.university.fst.resourcemanagement.dto.LoginResponse;
import com.university.fst.resourcemanagement.security.UserDetailsImpl;
import com.university.fst.resourcemanagement.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    /**
     * PUT /api/auth/change-password
     * Header : Authorization: Bearer <token>
     * Body   : { ancienMotDePasse, nouveauMotDePasse, confirmerMotDePasse }
     */
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @AuthenticationPrincipal UserDetailsImpl principal,
            @Valid @RequestBody ChangePasswordRequest request) {
        authService.changePassword(principal.getEmail(), request);
        return ResponseEntity.ok("Mot de passe modifié avec succès");
    }
}