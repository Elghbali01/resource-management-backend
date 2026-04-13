package com.university.fst.resourcemanagement.controller;

import com.university.fst.resourcemanagement.dto.AjouterBudgetRequest;
import com.university.fst.resourcemanagement.dto.DepartementRequest;
import com.university.fst.resourcemanagement.dto.DepartementResponse;
import com.university.fst.resourcemanagement.service.DepartementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Endpoints pour la gestion des départements.
 *
 * GET    /api/admin/departements          → liste tous les départements
 * GET    /api/admin/departements/{id}     → détail d'un département
 * POST   /api/admin/departements          → créer un département
 * PUT    /api/admin/departements/{id}     → modifier le nom
 * DELETE /api/admin/departements/{id}     → supprimer
 */
@RestController
@RequestMapping("/api/admin/departements")
@PreAuthorize("hasRole('ADMIN')")
public class DepartementController {

    private final DepartementService departementService;

    public DepartementController(DepartementService departementService) {
        this.departementService = departementService;
    }

    // ── Lister ─────────────────────────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<DepartementResponse>> lister() {
        return ResponseEntity.ok(departementService.listerDepartements());
    }

    // ── Détail ─────────────────────────────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<DepartementResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(departementService.getDepartement(id));
    }

    // ── Créer ──────────────────────────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<DepartementResponse> creer(
            @Valid @RequestBody DepartementRequest request) {
        DepartementResponse response = departementService.creerDepartement(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ── Modifier ───────────────────────────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<DepartementResponse> modifier(
            @PathVariable Long id,
            @Valid @RequestBody DepartementRequest request) {
        return ResponseEntity.ok(departementService.modifierDepartement(id, request));
    }

    // ── Supprimer ──────────────────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> supprimer(@PathVariable Long id) {
        departementService.supprimerDepartement(id);
        return ResponseEntity.ok(Map.of("message", "Département supprimé avec succès"));
    }
    // ── Budget ─────────────────────────────────────────────────────────────────
    @PatchMapping("/{id}/budget")
    public ResponseEntity<DepartementResponse> ajouterBudget(
            @PathVariable Long id,
            @Valid @RequestBody AjouterBudgetRequest request) {
        return ResponseEntity.ok(departementService.ajouterBudget(id, request));
    }
    // Lister ── Budget ─────────────────────────────────────────────────────────────────
    @GetMapping("/{id}/budget")
    public ResponseEntity<Map<String, Object>> getBudget(@PathVariable Long id) {
        BigDecimal budget = departementService.getBudgetDepartement(id);
        return ResponseEntity.ok(Map.of(
                "departementId", id,
                "budget",        budget
        ));
    }
}