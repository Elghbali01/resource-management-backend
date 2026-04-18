package com.university.fst.resourcemanagement.controller;

import com.university.fst.resourcemanagement.dto.CreateOffreFournisseurRequest;
import com.university.fst.resourcemanagement.dto.OffreFournisseurResponse;
import com.university.fst.resourcemanagement.security.UserDetailsImpl;
import com.university.fst.resourcemanagement.service.OffreFournisseurService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fournisseurs/offres")
@PreAuthorize("hasRole('FOURNISSEUR')")
public class FournisseurOffreController {

    private final OffreFournisseurService offreFournisseurService;

    public FournisseurOffreController(OffreFournisseurService offreFournisseurService) {
        this.offreFournisseurService = offreFournisseurService;
    }

    @PostMapping
    public ResponseEntity<OffreFournisseurResponse> soumettre(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody CreateOffreFournisseurRequest request
    ) {
        OffreFournisseurResponse response =
                offreFournisseurService.soumettreOffre(userDetails.getId(), request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/mes-offres")
    public ResponseEntity<List<OffreFournisseurResponse>> listerMesOffres(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok(
                offreFournisseurService.listerMesOffres(userDetails.getId())
        );
    }
}