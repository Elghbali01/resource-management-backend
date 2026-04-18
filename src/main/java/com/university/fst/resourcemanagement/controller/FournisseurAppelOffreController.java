package com.university.fst.resourcemanagement.controller;

import com.university.fst.resourcemanagement.dto.AppelOffreResponse;
import com.university.fst.resourcemanagement.service.AppelOffreService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fournisseurs/appels-offre")
@PreAuthorize("hasRole('FOURNISSEUR')")
public class FournisseurAppelOffreController {

    private final AppelOffreService appelOffreService;

    public FournisseurAppelOffreController(AppelOffreService appelOffreService) {
        this.appelOffreService = appelOffreService;
    }

    @GetMapping
    public ResponseEntity<List<AppelOffreResponse>> listerActuels() {
        return ResponseEntity.ok(appelOffreService.listerAppelsOffreActuelsPourFournisseur());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppelOffreResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(appelOffreService.getAppelOffrePourFournisseur(id));
    }
}