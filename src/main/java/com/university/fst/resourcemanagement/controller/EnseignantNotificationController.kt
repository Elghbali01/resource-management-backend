package com.university.fst.resourcemanagement.controller

import com.university.fst.resourcemanagement.dto.DemandeCollecteResponse
import com.university.fst.resourcemanagement.dto.NotificationResponse
import com.university.fst.resourcemanagement.security.UserDetailsImpl
import com.university.fst.resourcemanagement.service.DemandeCollecteService
import com.university.fst.resourcemanagement.service.NotificationService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/enseignant")
@PreAuthorize("hasRole('ENSEIGNANT')")
open class EnseignantNotificationController(   // 👈 IMPORTANT
    private val notificationService: NotificationService,
    private val demandeCollecteService: DemandeCollecteService
) {

    @GetMapping("/notifications")
    open fun getMesNotifications(   // 👈 recommandé aussi
        @AuthenticationPrincipal userDetails: UserDetailsImpl
    ): ResponseEntity<List<NotificationResponse>> {
        return ResponseEntity.ok(
            notificationService.getMesNotifications(userDetails.id)
        )
    }

    @GetMapping("/notifications/non-lues")
    open fun compterNonLues(
        @AuthenticationPrincipal userDetails: UserDetailsImpl
    ): ResponseEntity<Map<String, Long>> {
        return ResponseEntity.ok(
            notificationService.compterNonLues(userDetails.id)
        )
    }

    @PatchMapping("/notifications/{id}/lire")
    open fun marquerCommeLue(
        @PathVariable id: Long,
        @AuthenticationPrincipal userDetails: UserDetailsImpl
    ): ResponseEntity<NotificationResponse> {
        return ResponseEntity.ok(
            notificationService.marquerCommeLue(id, userDetails.id)
        )
    }

    @GetMapping("/demandes-ouvertes")
    open fun getDemandesOuvertes(
        @AuthenticationPrincipal userDetails: UserDetailsImpl
    ): ResponseEntity<List<DemandeCollecteResponse>> {
        return ResponseEntity.ok(
            demandeCollecteService.listerDemandesOuvertesEnseignant(
                userDetails.id
            )
        )
    }
}