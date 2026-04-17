package com.university.fst.resourcemanagement.controller;

import com.university.fst.resourcemanagement.dto.BesoinRequest;
import com.university.fst.resourcemanagement.dto.BesoinResponse;
import com.university.fst.resourcemanagement.security.UserDetailsImpl;
import com.university.fst.resourcemanagement.service.BesoinMaterielService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chef/besoins")
@PreAuthorize("hasRole('CHEF_DEPARTEMENT')")
public class ChefBesoinController {

    private final BesoinMaterielService besoinMaterielService;

    public ChefBesoinController(BesoinMaterielService besoinMaterielService) {
        this.besoinMaterielService = besoinMaterielService;
    }

    @GetMapping("/demande/{demandeId}")
    public ResponseEntity<List<BesoinResponse>> listerTousLesBesoins(
            @PathVariable Long demandeId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok(
                besoinMaterielService.listerBesoinsPourChef(userDetails.getId(), demandeId)
        );
    }

    @GetMapping("/demande/{demandeId}/collectifs")
    public ResponseEntity<List<BesoinResponse>> listerBesoinsCollectifs(
            @PathVariable Long demandeId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok(
                besoinMaterielService.listerBesoinsCollectifs(userDetails.getId(), demandeId)
        );
    }

    @PostMapping("/collectifs")
    public ResponseEntity<BesoinResponse> ajouterBesoinCollectif(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody BesoinRequest request
    ) {
        BesoinResponse response =
                besoinMaterielService.ajouterBesoinCollectif(userDetails.getId(), request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BesoinResponse> modifierBesoinParChef(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody BesoinRequest request
    ) {
        return ResponseEntity.ok(
                besoinMaterielService.modifierBesoinParChef(userDetails.getId(), id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> supprimerBesoinParChef(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        besoinMaterielService.supprimerBesoinParChef(userDetails.getId(), id);
        return ResponseEntity.ok(Map.of("message", "Besoin supprimé avec succès"));
    }
}