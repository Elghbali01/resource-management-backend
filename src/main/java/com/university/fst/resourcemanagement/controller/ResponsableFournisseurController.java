package com.university.fst.resourcemanagement.controller;

import com.university.fst.resourcemanagement.dto.BlacklistFournisseurRequest;
import com.university.fst.resourcemanagement.dto.FournisseurAdminResponse;
import com.university.fst.resourcemanagement.service.GestionListeNoireFournisseurService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/responsable/fournisseurs")
@PreAuthorize("hasRole('RESPONSABLE_RESOURCE')")
public class ResponsableFournisseurController {

    private final GestionListeNoireFournisseurService gestionListeNoireFournisseurService;

    public ResponsableFournisseurController(
            GestionListeNoireFournisseurService gestionListeNoireFournisseurService
    ) {
        this.gestionListeNoireFournisseurService = gestionListeNoireFournisseurService;
    }

    @GetMapping
    public ResponseEntity<List<FournisseurAdminResponse>> listerFournisseurs() {
        return ResponseEntity.ok(
                gestionListeNoireFournisseurService.listerFournisseurs()
        );
    }

    @PatchMapping("/{fournisseurId}/blacklist")
    public ResponseEntity<FournisseurAdminResponse> blacklister(
            @PathVariable Long fournisseurId,
            @Valid @RequestBody BlacklistFournisseurRequest request
    ) {
        return ResponseEntity.ok(
                gestionListeNoireFournisseurService.blacklisterFournisseur(fournisseurId, request)
        );
    }

    @PatchMapping("/{fournisseurId}/retirer-blacklist")
    public ResponseEntity<FournisseurAdminResponse> retirerBlacklist(
            @PathVariable Long fournisseurId
    ) {
        return ResponseEntity.ok(
                gestionListeNoireFournisseurService.retirerListeNoire(fournisseurId)
        );
    }
}