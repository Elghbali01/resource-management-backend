package com.university.fst.resourcemanagement.controller;

import com.university.fst.resourcemanagement.dto.DemandeCollecteRequest;
import com.university.fst.resourcemanagement.dto.DemandeCollecteResponse;
import com.university.fst.resourcemanagement.security.UserDetailsImpl;
import com.university.fst.resourcemanagement.service.DemandeCollecteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * POST   /api/chef/demandes                  → créer une demande en BROUILLON
 * PATCH  /api/chef/demandes/{id}/ouvrir      → ouvrir la collecte
 * PATCH  /api/chef/demandes/{id}/fermer      → fermer la collecte
 * GET    /api/chef/demandes                  → liste les demandes du département du chef
 */
@RestController
@RequestMapping("/api/chef/demandes")
@PreAuthorize("hasRole('CHEF_DEPARTEMENT')")
public class DemandeCollecteController {

    private final DemandeCollecteService demandeCollecteService;

    public DemandeCollecteController(DemandeCollecteService demandeCollecteService) {
        this.demandeCollecteService = demandeCollecteService;
    }

    @PostMapping
    public ResponseEntity<DemandeCollecteResponse> creer(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody DemandeCollecteRequest request) {

        DemandeCollecteResponse response =
                demandeCollecteService.creerDemande(userDetails.getId(), request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}/ouvrir")
    public ResponseEntity<DemandeCollecteResponse> ouvrir(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok(
                demandeCollecteService.ouvrirDemande(userDetails.getId(), id)
        );
    }

    @PatchMapping("/{id}/fermer")
    public ResponseEntity<DemandeCollecteResponse> fermer(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok(
                demandeCollecteService.fermerDemande(userDetails.getId(), id)
        );
    }

    @GetMapping
    public ResponseEntity<List<DemandeCollecteResponse>> lister(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok(
                demandeCollecteService.listerDemandesChef(userDetails.getId())
        );
    }
}