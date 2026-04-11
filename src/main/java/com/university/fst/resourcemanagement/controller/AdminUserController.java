package com.university.fst.resourcemanagement.controller;

import com.university.fst.resourcemanagement.dto.CreateUserRequest;
import com.university.fst.resourcemanagement.dto.UpdateUserRequest;
import com.university.fst.resourcemanagement.dto.UserListResponse;
import com.university.fst.resourcemanagement.service.AdminUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Endpoints accessibles uniquement à l'ADMIN.
 *
 * GET    /api/admin/users          → liste de tous les utilisateurs internes
 * POST   /api/admin/users          → créer un utilisateur
 * PUT    /api/admin/users/{id}     → modifier nom/prénom/email
 * PATCH  /api/admin/users/{id}/toggle-statut → bloquer / débloquer
 * DELETE /api/admin/users/{id}     → supprimer
 */
@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final AdminUserService adminUserService;

    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    // ── Lister ─────────────────────────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<UserListResponse>> lister() {
        return ResponseEntity.ok(adminUserService.listerUtilisateurs());
    }

    // ── Créer ──────────────────────────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<UserListResponse> creer(
            @Valid @RequestBody CreateUserRequest request) {
        UserListResponse response = adminUserService.creerUtilisateur(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ── Modifier ───────────────────────────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<UserListResponse> modifier(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(adminUserService.modifierUtilisateur(id, request));
    }

    // ── Bloquer / Débloquer ────────────────────────────────────────────────────
    @PatchMapping("/{id}/toggle-statut")
    public ResponseEntity<UserListResponse> toggleStatut(@PathVariable Long id) {
        return ResponseEntity.ok(adminUserService.toggleStatut(id));
    }

    // ── Supprimer ──────────────────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> supprimer(@PathVariable Long id) {
        adminUserService.supprimerUtilisateur(id);
        return ResponseEntity.ok(Map.of("message", "Utilisateur supprimé avec succès"));
    }
}