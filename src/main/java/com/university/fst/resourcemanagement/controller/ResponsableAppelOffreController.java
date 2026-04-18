package com.university.fst.resourcemanagement.controller;

import com.university.fst.resourcemanagement.dto.AppelOffreResponse;
import com.university.fst.resourcemanagement.dto.CreateAppelOffreRequest;
import com.university.fst.resourcemanagement.security.UserDetailsImpl;
import com.university.fst.resourcemanagement.service.AppelOffreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/responsable/appels-offre")
@PreAuthorize("hasRole('RESPONSABLE_RESOURCE')")
public class ResponsableAppelOffreController {

    private final AppelOffreService appelOffreService;

    public ResponsableAppelOffreController(AppelOffreService appelOffreService) {
        this.appelOffreService = appelOffreService;
    }

    @PostMapping
    public ResponseEntity<AppelOffreResponse> creer(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody CreateAppelOffreRequest request
    ) {
        AppelOffreResponse response =
                appelOffreService.creerAppelOffre(userDetails.getId(), request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AppelOffreResponse>> lister() {
        return ResponseEntity.ok(appelOffreService.listerAppelsOffreResponsable());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppelOffreResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(appelOffreService.getAppelOffreResponsable(id));
    }
}