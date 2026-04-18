package com.university.fst.resourcemanagement.controller;

import com.university.fst.resourcemanagement.dto.ConstatPanneRequest;
import com.university.fst.resourcemanagement.dto.InterventionPanneRequest;
import com.university.fst.resourcemanagement.dto.PanneResponse;
import com.university.fst.resourcemanagement.security.UserDetailsImpl;
import com.university.fst.resourcemanagement.service.MaintenanceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/technicien/pannes")
@PreAuthorize("hasRole('TECHNICIEN')")
public class TechnicienPanneController {

    private final MaintenanceService maintenanceService;

    public TechnicienPanneController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @GetMapping
    public ResponseEntity<List<PanneResponse>> lister() {
        return ResponseEntity.ok(maintenanceService.listerToutesLesPannes());
    }

    @PatchMapping("/{panneId}/intervention")
    public ResponseEntity<PanneResponse> intervenir(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long panneId,
            @RequestBody InterventionPanneRequest request
    ) {
        return ResponseEntity.ok(
                maintenanceService.commencerIntervention(userDetails.getId(), panneId, request)
        );
    }

    @PostMapping("/{panneId}/constat")
    public ResponseEntity<PanneResponse> constat(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long panneId,
            @Valid @RequestBody ConstatPanneRequest request
    ) {
        return ResponseEntity.ok(
                maintenanceService.redigerConstat(userDetails.getId(), panneId, request)
        );
    }
}