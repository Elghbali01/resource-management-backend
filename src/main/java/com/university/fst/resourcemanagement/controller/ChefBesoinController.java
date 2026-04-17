package com.university.fst.resourcemanagement.controller;

import com.university.fst.resourcemanagement.dto.BesoinResponse;
import com.university.fst.resourcemanagement.security.UserDetailsImpl;
import com.university.fst.resourcemanagement.service.BesoinMaterielService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chef/demandes")
@PreAuthorize("hasRole('CHEF_DEPARTEMENT')")
public class ChefBesoinController {

    private final BesoinMaterielService besoinMaterielService;

    public ChefBesoinController(BesoinMaterielService besoinMaterielService) {
        this.besoinMaterielService = besoinMaterielService;
    }

    @GetMapping("/{demandeId}/besoins")
    public ResponseEntity<List<BesoinResponse>> listerBesoinsDemande(
            @PathVariable Long demandeId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok(
                besoinMaterielService.listerBesoinsPourChef(userDetails.getId(), demandeId)
        );
    }
}