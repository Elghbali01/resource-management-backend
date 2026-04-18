package com.university.fst.resourcemanagement.controller;

import com.university.fst.resourcemanagement.dto.*;
import com.university.fst.resourcemanagement.service.ResponsableRessourceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/responsable/ressources")
@PreAuthorize("hasRole('RESPONSABLE_RESOURCE')")
public class ResponsableRessourceController {

    private final ResponsableRessourceService responsableRessourceService;

    public ResponsableRessourceController(ResponsableRessourceService responsableRessourceService) {
        this.responsableRessourceService = responsableRessourceService;
    }

    @PostMapping("/reception")
    public ResponseEntity<ReceptionLivraisonResponse> receptionner(
            @Valid @RequestBody ReceptionLivraisonRequest request
    ) {
        ReceptionLivraisonResponse response =
                responsableRessourceService.receptionnerLivraison(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<RessourceResponse>> listerRessources() {
        return ResponseEntity.ok(responsableRessourceService.listerRessources());
    }

    @GetMapping("/{ressourceId}")
    public ResponseEntity<RessourceResponse> getRessource(@PathVariable Long ressourceId) {
        return ResponseEntity.ok(responsableRessourceService.getRessource(ressourceId));
    }

    @PutMapping("/{ressourceId}")
    public ResponseEntity<RessourceResponse> modifierRessource(
            @PathVariable Long ressourceId,
            @Valid @RequestBody RessourceRequest request
    ) {
        return ResponseEntity.ok(
                responsableRessourceService.modifierRessource(ressourceId, request)
        );
    }

    @DeleteMapping("/{ressourceId}")
    public ResponseEntity<Map<String, String>> supprimerRessource(@PathVariable Long ressourceId) {
        responsableRessourceService.supprimerRessource(ressourceId);
        return ResponseEntity.ok(Map.of("message", "Ressource supprimée avec succès"));
    }

    @PostMapping("/affectations")
    public ResponseEntity<AffectationRessourceResponse> affecter(
            @Valid @RequestBody AffectationRessourceRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                responsableRessourceService.affecterRessource(request)
        );
    }

    @GetMapping("/affectations")
    public ResponseEntity<List<AffectationRessourceResponse>> listerAffectations() {
        return ResponseEntity.ok(responsableRessourceService.listerAffectations());
    }

    @PutMapping("/affectations/{affectationId}")
    public ResponseEntity<AffectationRessourceResponse> modifierAffectation(
            @PathVariable Long affectationId,
            @Valid @RequestBody AffectationRessourceRequest request
    ) {
        return ResponseEntity.ok(
                responsableRessourceService.modifierAffectation(affectationId, request)
        );
    }

    @DeleteMapping("/affectations/{affectationId}")
    public ResponseEntity<Map<String, String>> supprimerAffectation(
            @PathVariable Long affectationId
    ) {
        responsableRessourceService.supprimerAffectation(affectationId);
        return ResponseEntity.ok(Map.of("message", "Affectation supprimée avec succès"));
    }
}