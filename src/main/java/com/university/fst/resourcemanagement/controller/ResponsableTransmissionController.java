package com.university.fst.resourcemanagement.controller;

import com.university.fst.resourcemanagement.dto.AffectationPrevueResponse;
import com.university.fst.resourcemanagement.dto.DemandeCollecteResponse;
import com.university.fst.resourcemanagement.service.ResponsableTransmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/responsable/demandes-transmises")
@PreAuthorize("hasRole('RESPONSABLE_RESOURCE')")
public class ResponsableTransmissionController {

    private final ResponsableTransmissionService responsableTransmissionService;

    public ResponsableTransmissionController(
            ResponsableTransmissionService responsableTransmissionService
    ) {
        this.responsableTransmissionService = responsableTransmissionService;
    }

    @GetMapping
    public ResponseEntity<List<DemandeCollecteResponse>> listerDemandesTransmises() {
        return ResponseEntity.ok(responsableTransmissionService.listerDemandesTransmises());
    }

    @GetMapping("/{demandeId}/affectations-prevues")
    public ResponseEntity<List<AffectationPrevueResponse>> listerAffectationsPrevues(
            @PathVariable Long demandeId
    ) {
        return ResponseEntity.ok(
                responsableTransmissionService.listerAffectationsPrevues(demandeId)
        );
    }
}