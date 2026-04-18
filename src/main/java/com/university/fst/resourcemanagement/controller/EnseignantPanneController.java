package com.university.fst.resourcemanagement.controller;

import com.university.fst.resourcemanagement.dto.PanneResponse;
import com.university.fst.resourcemanagement.dto.RessourcePanneSelectResponse;
import com.university.fst.resourcemanagement.dto.SignalerPanneRequest;
import com.university.fst.resourcemanagement.security.UserDetailsImpl;
import com.university.fst.resourcemanagement.service.MaintenanceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enseignant/pannes")
@PreAuthorize("hasRole('ENSEIGNANT')")
public class EnseignantPanneController {

    private final MaintenanceService maintenanceService;

    public EnseignantPanneController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @GetMapping("/ressources")
    public ResponseEntity<List<RessourcePanneSelectResponse>> listerRessourcesPourPanne(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok(
                maintenanceService.listerRessourcesPannePourEnseignant(userDetails.getId())
        );
    }

    @PostMapping
    public ResponseEntity<PanneResponse> signaler(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody SignalerPanneRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                maintenanceService.signalerPanne(userDetails.getId(), request)
        );
    }

    @GetMapping
    public ResponseEntity<List<PanneResponse>> mesPannes(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok(
                maintenanceService.listerMesPannes(userDetails.getId())
        );
    }
}