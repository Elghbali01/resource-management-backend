package com.university.fst.resourcemanagement.controller;

import com.university.fst.resourcemanagement.dto.FournisseurRegisterRequest;
import com.university.fst.resourcemanagement.dto.FournisseurRegisterResponse;
import com.university.fst.resourcemanagement.service.FournisseurService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fournisseurs")
@CrossOrigin(origins = "*")
public class FournisseurController {

    private final FournisseurService fournisseurService;

    public FournisseurController(FournisseurService fournisseurService) {
        this.fournisseurService = fournisseurService;
    }

    /**
     * POST /api/fournisseurs/inscription
     * Accessible sans authentification (public) – à autoriser dans SecurityConfig
     * Body: { "nomSociete": "...", "email": "...", "password": "...", "confirmPassword": "..." }
     */
    @PostMapping("/inscription")
    public ResponseEntity<FournisseurRegisterResponse> inscrire(
            @Valid @RequestBody FournisseurRegisterRequest request) {

        FournisseurRegisterResponse response = fournisseurService.inscrire(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}