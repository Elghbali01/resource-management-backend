package com.university.fst.resourcemanagement.controller;

import com.university.fst.resourcemanagement.dto.EnseignantResponse;
import com.university.fst.resourcemanagement.security.UserDetailsImpl;
import com.university.fst.resourcemanagement.service.ChefDepartementService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints accessibles uniquement au CHEF_DEPARTEMENT.
 *
 * GET /api/chef/enseignants   → liste les enseignants de son département
 */
@RestController
@RequestMapping("/api/chef")
@PreAuthorize("hasRole('CHEF_DEPARTEMENT')")
public class ChefDepartementController {

    private final ChefDepartementService chefDepartementService;

    public ChefDepartementController(ChefDepartementService chefDepartementService) {
        this.chefDepartementService = chefDepartementService;
    }

    // ── Liste des enseignants du département du chef connecté ──────────────────
    @GetMapping("/enseignants")
    public ResponseEntity<List<EnseignantResponse>> listerEnseignants(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok(
                chefDepartementService.listerEnseignantsDuDepartement(userDetails.getId())
        );
    }
}