package com.university.fst.resourcemanagement.service.impl;

import com.university.fst.resourcemanagement.dto.DemandeCollecteRequest;
import com.university.fst.resourcemanagement.dto.DemandeCollecteResponse;
import com.university.fst.resourcemanagement.dto.DemandeConcertationRequest;
import com.university.fst.resourcemanagement.dto.TransmissionDemandeResponse;
import com.university.fst.resourcemanagement.entity.*;
import com.university.fst.resourcemanagement.enums.NatureBesoin;
import com.university.fst.resourcemanagement.enums.StatutDemande;
import com.university.fst.resourcemanagement.enums.TypeAffectationPrevue;
import com.university.fst.resourcemanagement.enums.TypeNotification;
import com.university.fst.resourcemanagement.repository.*;
import com.university.fst.resourcemanagement.service.DemandeCollecteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DemandeCollecteServiceImpl implements DemandeCollecteService {

    private final DemandeCollecteRepository demandeCollecteRepository;
    private final ChefDepartementRepository chefDepartementRepository;
    private final EnseignantRepository enseignantRepository;
    private final NotificationRepository notificationRepository;
    private final BesoinMaterielRepository besoinMaterielRepository;
    private final AffectationPrevueRepository affectationPrevueRepository;

    public DemandeCollecteServiceImpl(
            DemandeCollecteRepository demandeCollecteRepository,
            ChefDepartementRepository chefDepartementRepository,
            EnseignantRepository enseignantRepository,
            NotificationRepository notificationRepository,
            BesoinMaterielRepository besoinMaterielRepository,
            AffectationPrevueRepository affectationPrevueRepository
    ) {
        this.demandeCollecteRepository = demandeCollecteRepository;
        this.chefDepartementRepository = chefDepartementRepository;
        this.enseignantRepository = enseignantRepository;
        this.notificationRepository = notificationRepository;
        this.besoinMaterielRepository = besoinMaterielRepository;
        this.affectationPrevueRepository = affectationPrevueRepository;
    }

    @Override
    @Transactional
    public DemandeCollecteResponse creerDemande(Long chefUserId, DemandeCollecteRequest request) {
        ChefDepartement chef = getChefByUserId(chefUserId);
        Departement departement = chef.getDepartement();

        verifierBudgetDisponible(departement);

        DemandeCollecte demande = new DemandeCollecte();
        demande.setTitre(request.getTitre());
        demande.setDescription(request.getDescription());
        demande.setDateLimite(request.getDateLimite());
        demande.setStatut(StatutDemande.BROUILLON);
        demande.setDepartement(departement);
        demande.setCreePar(chef.getUser());
        demande.setDateCreation(LocalDateTime.now());

        return toResponse(demandeCollecteRepository.save(demande));
    }

    @Override
    @Transactional
    public DemandeCollecteResponse ouvrirDemande(Long chefUserId, Long demandeId) {
        ChefDepartement chef = getChefByUserId(chefUserId);
        Departement departement = chef.getDepartement();

        verifierBudgetDisponible(departement);

        DemandeCollecte demande = demandeCollecteRepository
                .findByIdAndDepartementId(demandeId, departement.getId())
                .orElseThrow(() -> new RuntimeException("Demande introuvable pour le département du chef"));

        if (StatutDemande.OUVERTE.equals(demande.getStatut())) {
            throw new RuntimeException("Cette demande est déjà ouverte");
        }
        if (StatutDemande.CONCERTATION.equals(demande.getStatut())
                || StatutDemande.VALIDEE.equals(demande.getStatut())
                || StatutDemande.TRANSMISE.equals(demande.getStatut())) {
            throw new RuntimeException("Impossible d'ouvrir cette demande dans son état actuel");
        }

        boolean existeDejaUneOuverte = demandeCollecteRepository
                .existsByDepartementIdAndStatut(departement.getId(), StatutDemande.OUVERTE);

        if (existeDejaUneOuverte) {
            throw new RuntimeException("Une autre demande de collecte est déjà ouverte pour ce département");
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
                .orElseThrow(() -> new RuntimeException("Demande introuvable pour le département du chef"));

        if (!StatutDemande.OUVERTE.equals(demande.getStatut())) {
            throw new RuntimeException("Seule une demande ouverte peut passer en concertation");
        }

        demande.setStatut(StatutDemande.CONCERTATION);
        demande.setDateDebutConcertation(LocalDateTime.now());

        return toResponse(demandeCollecteRepository.save(demande));
    }

    @Override
    @Transactional
    public DemandeCollecteResponse validerDemande(
            Long chefUserId,
            Long demandeId,
            DemandeConcertationRequest request
    ) {
        ChefDepartement chef = getChefByUserId(chefUserId);
        Departement departement = chef.getDepartement();

        DemandeCollecte demande = demandeCollecteRepository
                .findByIdAndDepartementId(demandeId, departement.getId())
                .orElseThrow(() -> new RuntimeException("Demande introuvable pour le département du chef"));

        if (!StatutDemande.CONCERTATION.equals(demande.getStatut())) {
            throw new RuntimeException("La demande doit être en concertation avant validation");
        }

        List<BesoinMateriel> besoins = besoinMaterielRepository
                .findByDemandeCollecteIdOrderByDateSoumissionDesc(demandeId);

        if (besoins.isEmpty()) {
            throw new RuntimeException("Impossible de valider une demande sans aucun besoin");
        }

        demande.setCompteRenduConcertation(request.getCompteRenduConcertation());
        demande.setDateFinConcertation(LocalDateTime.now());
        demande.setDateValidationChef(LocalDateTime.now());
        demande.setStatut(StatutDemande.VALIDEE);

        return toResponse(demandeCollecteRepository.save(demande));
    }

    @Override
    @Transactional
    public TransmissionDemandeResponse transmettreAuResponsable(Long chefUserId, Long demandeId) {
        ChefDepartement chef = getChefByUserId(chefUserId);
        Departement departement = chef.getDepartement();

        DemandeCollecte demande = demandeCollecteRepository
                .findByIdAndDepartementId(demandeId, departement.getId())
                .orElseThrow(() -> new RuntimeException("Demande introuvable pour le département du chef"));

        if (!StatutDemande.VALIDEE.equals(demande.getStatut())) {
            throw new RuntimeException("La demande doit être validée avant transmission au responsable");
        }

        List<BesoinMateriel> besoins = besoinMaterielRepository
                .findByDemandeCollecteIdOrderByDateSoumissionDesc(demandeId);

        if (besoins.isEmpty()) {
            throw new RuntimeException("Impossible de transmettre une demande sans besoins");
        }

        affectationPrevueRepository.deleteByDemandeCollecteId(demandeId);

        for (BesoinMateriel besoin : besoins) {
            AffectationPrevue ap = new AffectationPrevue();
            ap.setDemandeCollecte(demande);
            ap.setBesoinMateriel(besoin);
            ap.setDepartement(departement);
            ap.setQuantite(besoin.getQuantite());
            ap.setDescriptionMateriel(construireDescriptionMateriel(besoin));

            if (NatureBesoin.INDIVIDUEL.equals(besoin.getNatureBesoin()) && besoin.getEnseignant() != null) {
                ap.setTypeAffectation(TypeAffectationPrevue.ENSEIGNANT);
                ap.setEnseignant(besoin.getEnseignant());
            } else {
                ap.setTypeAffectation(TypeAffectationPrevue.DEPARTEMENT);
                ap.setEnseignant(null);
            }

            affectationPrevueRepository.save(ap);
        }

        demande.setStatut(StatutDemande.TRANSMISE);
        demande.setDateTransmissionResponsable(LocalDateTime.now());
        demandeCollecteRepository.save(demande);

        long total = affectationPrevueRepository.countByDemandeCollecteId(demandeId);

        return new TransmissionDemandeResponse(
                demande.getId(),
                demande.getStatut(),
                demande.getDateTransmissionResponsable(),
                total,
                "Demande transmise au responsable avec succès"
        );
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
        BigDecimal budget = departement.getBudget() != null ? departement.getBudget() : BigDecimal.ZERO;
        if (budget.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException(
                    "Impossible de lancer une demande de collecte : le département n'a pas encore de budget");
        }
    }

    private void envoyerNotificationsEnseignants(
            DemandeCollecte demande,
            Departement departement,
            Long chefUserId
    ) {
        List<Enseignant> enseignants = enseignantRepository.findByDepartementId(departement.getId());

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

    private String construireDescriptionMateriel(BesoinMateriel besoin) {
        String marque = besoin.getMarqueSouhaitee() != null ? besoin.getMarqueSouhaitee() : "Sans marque";
        String details = besoin.getCaracteristiques() != null ? besoin.getCaracteristiques() : "";
        return besoin.getTypeMateriel().name() + " | Marque: " + marque + " | " + details;
    }

    private DemandeCollecteResponse toResponse(DemandeCollecte d) {
        long nbAffectations = affectationPrevueRepository.countByDemandeCollecteId(d.getId());

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
                d.getCreePar().getPrenom(),
                d.getDateDebutConcertation(),
                d.getDateFinConcertation(),
                d.getCompteRenduConcertation(),
                d.getDateValidationChef(),
                d.getDateTransmissionResponsable(),
                nbAffectations
        );
    }
}