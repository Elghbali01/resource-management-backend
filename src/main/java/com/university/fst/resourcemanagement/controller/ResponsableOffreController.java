package com.university.fst.resourcemanagement.controller;

import com.university.fst.resourcemanagement.dto.DecisionOffreRequest;
import com.university.fst.resourcemanagement.dto.OffreFournisseurResponse;
import com.university.fst.resourcemanagement.service.OffreFournisseurService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/responsable/offres")
@PreAuthorize("hasRole('RESPONSABLE_RESOURCE')")
public class ResponsableOffreController {

    private final OffreFournisseurService offreFournisseurService;

    public ResponsableOffreController(OffreFournisseurService offreFournisseurService) {
        this.offreFournisseurService = offreFournisseurService;
    }

    @GetMapping("/appel-offre/{appelOffreId}")
    public ResponseEntity<List<OffreFournisseurResponse>> listerOffres(
            @PathVariable Long appelOffreId
    ) {
        return ResponseEntity.ok(
                offreFournisseurService.listerOffresAppelOffre(appelOffreId)
        );
    }

    @PatchMapping("/{offreId}/eliminer")
    public ResponseEntity<OffreFournisseurResponse> eliminer(
            @PathVariable Long offreId,
            @Valid @RequestBody DecisionOffreRequest request
    ) {
        return ResponseEntity.ok(
                offreFournisseurService.eliminerOffre(offreId, request)
        );
    }

    @PatchMapping("/{offreId}/accepter")
    public ResponseEntity<OffreFournisseurResponse> accepter(
            @PathVariable Long offreId,
            @Valid @RequestBody DecisionOffreRequest request
    ) {
        return ResponseEntity.ok(
                offreFournisseurService.accepterOffre(offreId, request)
        );
    }
}