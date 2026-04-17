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
@RequestMapping("/api/enseignant/besoins")
@PreAuthorize("hasRole('ENSEIGNANT')")
public class EnseignantBesoinController {

    private final BesoinMaterielService besoinMaterielService;

    public EnseignantBesoinController(BesoinMaterielService besoinMaterielService) {
        this.besoinMaterielService = besoinMaterielService;
    }

    @PostMapping
    public ResponseEntity<BesoinResponse> soumettre(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody BesoinRequest request
    ) {
        BesoinResponse response =
                besoinMaterielService.soumettreBesoin(userDetails.getId(), request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BesoinResponse> modifier(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody BesoinRequest request
    ) {
        return ResponseEntity.ok(
                besoinMaterielService.modifierBesoin(userDetails.getId(), id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> supprimer(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        besoinMaterielService.supprimerBesoin(userDetails.getId(), id);
        return ResponseEntity.ok(Map.of("message", "Besoin supprimé avec succès"));
    }

    @GetMapping("/demande/{demandeId}")
    public ResponseEntity<List<BesoinResponse>> listerMesBesoins(
            @PathVariable Long demandeId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok(
                besoinMaterielService.listerMesBesoins(userDetails.getId(), demandeId)
        );
    }
}