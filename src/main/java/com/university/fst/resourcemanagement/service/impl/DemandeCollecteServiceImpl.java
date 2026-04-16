package com.university.fst.resourcemanagement.service.impl;

import com.university.fst.resourcemanagement.dto.DemandeCollecteRequest;
import com.university.fst.resourcemanagement.dto.DemandeCollecteResponse;
import com.university.fst.resourcemanagement.entity.*;
import com.university.fst.resourcemanagement.enums.StatutDemande;
import com.university.fst.resourcemanagement.enums.TypeNotification;
import com.university.fst.resourcemanagement.repository.*;
import com.university.fst.resourcemanagement.service.DemandeCollecteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DemandeCollecteServiceImpl implements DemandeCollecteService {

    private final DemandeCollecteRepository demandeCollecteRepository;
    private final ChefDepartementRepository chefDepartementRepository;
    private final EnseignantRepository      enseignantRepository;
    private final NotificationRepository    notificationRepository;
    private final UserRepository            userRepository;

    public DemandeCollecteServiceImpl(
            DemandeCollecteRepository demandeCollecteRepository,
            ChefDepartementRepository chefDepartementRepository,
            EnseignantRepository enseignantRepository,
            NotificationRepository notificationRepository,
            UserRepository userRepository) {
        this.demandeCollecteRepository = demandeCollecteRepository;
        this.chefDepartementRepository = chefDepartementRepository;
        this.enseignantRepository      = enseignantRepository;
        this.notificationRepository    = notificationRepository;
        this.userRepository            = userRepository;
    }

    // ──────────────────────────────────────────────────────────────────────────
    // CRÉER UNE DEMANDE
    // ──────────────────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public DemandeCollecteResponse creerDemande(Long chefUserId,
                                                DemandeCollecteRequest request) {

        // 1. Récupérer le chef et son département
        ChefDepartement chef = chefDepartementRepository
                .findByUserId(chefUserId)
                .orElseThrow(() -> new RuntimeException(
                        "Chef de département introuvable pour l'utilisateur : " + chefUserId));

        Departement departement = chef.getDepartement();
        User chefUser = chef.getUser();

        // 2. Créer la demande
        DemandeCollecte demande = new DemandeCollecte();
        demande.setTitre(request.getTitre());
        demande.setDescription(request.getDescription());
        demande.setDateLimite(request.getDateLimite());
        demande.setStatut(request.getStatut());
        demande.setDepartement(departement);
        demande.setCreePar(chefUser);
        demande.setDateCreation(LocalDateTime.now());

        DemandeCollecte saved = demandeCollecteRepository.save(demande);

        // 3. Si statut OUVERTE → envoyer notifications à tous les enseignants
        if (StatutDemande.OUVERTE.equals(saved.getStatut())) {
            envoyerNotificationsEnseignants(saved, departement);
        }

        return toResponse(saved);
    }

    // ──────────────────────────────────────────────────────────────────────────
    // LISTER LES DEMANDES DU CHEF
    // ──────────────────────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<DemandeCollecteResponse> listerDemandesChef(Long chefUserId) {
        ChefDepartement chef = chefDepartementRepository
                .findByUserId(chefUserId)
                .orElseThrow(() -> new RuntimeException("Chef introuvable"));

        return demandeCollecteRepository
                .findByDepartementId(chef.getDepartement().getId())
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ──────────────────────────────────────────────────────────────────────────
    // LISTER LES DEMANDES OUVERTES POUR UN ENSEIGNANT
    // ──────────────────────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<DemandeCollecteResponse> listerDemandesOuvertesEnseignant(
            Long enseignantUserId) {

        Enseignant enseignant = enseignantRepository
                .findByUserId(enseignantUserId)
                .orElseThrow(() -> new RuntimeException("Enseignant introuvable"));

        return demandeCollecteRepository
                .findByDepartementIdAndStatut(
                        enseignant.getDepartement().getId(),
                        StatutDemande.OUVERTE)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Créer une notification pour chaque enseignant du département
    // ──────────────────────────────────────────────────────────────────────────
    private void envoyerNotificationsEnseignants(DemandeCollecte demande,
                                                 Departement departement) {

        List<Enseignant> enseignants = enseignantRepository
                .findByDepartementId(departement.getId());

        String message = String.format(
                "📋 Nouvelle demande de collecte : « %s » — Date limite : %s",
                demande.getTitre(),
                demande.getDateLimite().toString()
        );

        for (Enseignant enseignant : enseignants) {
            Notification notif = new Notification();
            notif.setMessage(message);
            notif.setTypeNotification(TypeNotification.NOUVELLE_DEMANDE_COLLECTE);
            notif.setUtilisateur(enseignant.getUser());
            notif.setDemande(demande);
            notif.setLu(false);
            notif.setDateCreation(LocalDateTime.now());
            notificationRepository.save(notif);
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Mapper entité → DTO
    // ──────────────────────────────────────────────────────────────────────────
    private DemandeCollecteResponse toResponse(DemandeCollecte d) {
        return new DemandeCollecteResponse(
                d.getId(),
                d.getTitre(),
                d.getDescription(),
                d.getDateCreation(),
                d.getDateLimite(),
                d.getStatut(),
                d.getDepartement().getId(),
                d.getDepartement().getNom(),
                d.getCreePar().getNom(),
                d.getCreePar().getPrenom()
        );
    }
}