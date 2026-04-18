package com.university.fst.resourcemanagement.controller;

import com.university.fst.resourcemanagement.dto.DecisionPanneRequest;
import com.university.fst.resourcemanagement.dto.PanneResponse;
import com.university.fst.resourcemanagement.service.MaintenanceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/responsable/maintenance")
@PreAuthorize("hasRole('RESPONSABLE_RESOURCE')")
public class ResponsableMaintenanceController {

    private final MaintenanceService maintenanceService;

    public ResponsableMaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @GetMapping("/pannes")
    public ResponseEntity<List<PanneResponse>> lister() {
        return ResponseEntity.ok(maintenanceService.listerPannesPourResponsable());
    }

    @PatchMapping("/pannes/{panneId}/decision")
    public ResponseEntity<PanneResponse> decider(
            @PathVariable Long panneId,
            @Valid @RequestBody DecisionPanneRequest request
    ) {
        return ResponseEntity.ok(maintenanceService.decider(panneId, request));
    }
}