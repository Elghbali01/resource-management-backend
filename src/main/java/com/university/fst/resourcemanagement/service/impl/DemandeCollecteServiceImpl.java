package com.university.fst.resourcemanagement.service.impl;

import com.university.fst.resourcemanagement.dto.DemandeCollecteRequest;
import com.university.fst.resourcemanagement.dto.DemandeCollecteResponse;
import com.university.fst.resourcemanagement.entity.ChefDepartement;
import com.university.fst.resourcemanagement.entity.DemandeCollecte;
import com.university.fst.resourcemanagement.entity.Departement;
import com.university.fst.resourcemanagement.entity.Enseignant;
import com.university.fst.resourcemanagement.entity.Notification;
import com.university.fst.resourcemanagement.entity.User;
import com.university.fst.resourcemanagement.enums.StatutDemande;
import com.university.fst.resourcemanagement.enums.TypeNotification;
import com.university.fst.resourcemanagement.repository.ChefDepartementRepository;
import com.university.fst.resourcemanagement.repository.DemandeCollecteRepository;
import com.university.fst.resourcemanagement.repository.EnseignantRepository;
import com.university.fst.resourcemanagement.repository.NotificationRepository;
import com.university.fst.resourcemanagement.service.DemandeCollecteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DemandeCollecteServiceImpl implements DemandeCollecteService {

    private final DemandeCollecteRepository demandeCollecteRepository;
    private final ChefDepartementRepository chefDepartementRepository;
    private final EnseignantRepository enseignantRepository;
    private final NotificationRepository notificationRepository;

    public DemandeCollecteServiceImpl(
            DemandeCollecteRepository demandeCollecteRepository,
            ChefDepartementRepository chefDepartementRepository,
            EnseignantRepository enseignantRepository,
            NotificationRepository notificationRepository) {
        this.demandeCollecteRepository = demandeCollecteRepository;
        this.chefDepartementRepository = chefDepartementRepository;
        this.enseignantRepository = enseignantRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    @Transactional
    public DemandeCollecteResponse creerDemande(Long chefUserId, DemandeCollecteRequest request) {

        ChefDepartement chef = getChefByUserId(chefUserId);
        Departement departement = chef.getDepartement();
        User chefUser = chef.getUser();

        verifierBudgetDisponible(departement);

        DemandeCollecte demande = new DemandeCollecte();
        demande.setTitre(request.getTitre());
        demande.setDescription(request.getDescription());
        demande.setDateLimite(request.getDateLimite());
        demande.setStatut(StatutDemande.BROUILLON);
        demande.setDepartement(departement);
        demande.setCreePar(chefUser);
        demande.setDateCreation(LocalDateTime.now());

        DemandeCollecte saved = demandeCollecteRepository.save(demande);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public DemandeCollecteResponse ouvrirDemande(Long chefUserId, Long demandeId) {

        ChefDepartement chef = getChefByUserId(chefUserId);
        Departement departement = chef.getDepartement();

        verifierBudgetDisponible(departement);

        DemandeCollecte demande = demandeCollecteRepository
                .findByIdAndDepartementId(demandeId, departement.getId())
                .orElseThrow(() -> new RuntimeException(
                        "Demande introuvable pour le département du chef"));

        if (StatutDemande.FERMEE.equals(demande.getStatut())) {
            throw new RuntimeException("Impossible d'ouvrir une demande déjà fermée");
        }

        if (StatutDemande.OUVERTE.equals(demande.getStatut())) {
            throw new RuntimeException("Cette demande est déjà ouverte");
        }

        boolean existeDejaUneOuverte = demandeCollecteRepository
                .existsByDepartementIdAndStatut(departement.getId(), StatutDemande.OUVERTE);

        if (existeDejaUneOuverte) {
            throw new RuntimeException(
                    "Une autre demande de collecte est déjà ouverte pour ce département");
        }

        demande.setStatut(StatutDemande.OUVERTE);
        DemandeCollecte saved = demandeCollecteRepository.save(demande);

        envoyerNotificationsEnseignants(saved, departement, chefUserId);

        return toResponse(saved);
    }

    @Override
    @Transactional
    public DemandeCollecteResponse fermerDemande(Long chefUserId, Long demandeId) {

        ChefDepartement chef = getChefByUserId(chefUserId);
        Departement departement = chef.getDepartement();

        DemandeCollecte demande = demandeCollecteRepository
                .findByIdAndDepartementId(demandeId, departement.getId())
                .orElseThrow(() -> new RuntimeException(
                        "Demande introuvable pour le département du chef"));

        if (StatutDemande.FERMEE.equals(demande.getStatut())) {
            throw new RuntimeException("Cette demande est déjà fermée");
        }

        if (StatutDemande.BROUILLON.equals(demande.getStatut())) {
            throw new RuntimeException("Impossible de fermer une demande encore en brouillon");
        }

        demande.setStatut(StatutDemande.FERMEE);
        DemandeCollecte saved = demandeCollecteRepository.save(demande);

        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DemandeCollecteResponse> listerDemandesChef(Long chefUserId) {
        ChefDepartement chef = getChefByUserId(chefUserId);

        return demandeCollecteRepository
                .findByDepartementId(chef.getDepartement().getId())
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DemandeCollecteResponse> listerDemandesOuvertesEnseignant(Long enseignantUserId) {

        Enseignant enseignant = enseignantRepository
                .findByUserId(enseignantUserId)
                .orElseThrow(() -> new RuntimeException("Enseignant introuvable"));

        return demandeCollecteRepository
                .findByDepartementIdAndStatut(
                        enseignant.getDepartement().getId(),
                        StatutDemande.OUVERTE
                )
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ChefDepartement getChefByUserId(Long chefUserId) {
        return chefDepartementRepository.findByUserId(chefUserId)
                .orElseThrow(() -> new RuntimeException(
                        "Chef de département introuvable pour l'utilisateur : " + chefUserId));
    }

    private void verifierBudgetDisponible(Departement departement) {
        BigDecimal budget = departement.getBudget() != null
                ? departement.getBudget()
                : BigDecimal.ZERO;

        if (budget.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException(
                    "Impossible de lancer une demande de collecte : le département n'a pas encore de budget");
        }
    }

    private void envoyerNotificationsEnseignants(
            DemandeCollecte demande,
            Departement departement,
            Long chefUserId) {

        List<Enseignant> enseignants = enseignantRepository
                .findByDepartementId(departement.getId());

        String message = String.format(
                "Nouvelle demande de collecte : \"%s\" - Date limite : %s",
                demande.getTitre(),
                demande.getDateLimite()
        );

        for (Enseignant enseignant : enseignants) {
            if (enseignant.getUser() == null || enseignant.getUser().getId().equals(chefUserId)) {
                continue;
            }

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