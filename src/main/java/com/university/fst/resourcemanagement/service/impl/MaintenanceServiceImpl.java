package com.university.fst.resourcemanagement.service.impl;

import com.university.fst.resourcemanagement.dto.*;
import com.university.fst.resourcemanagement.entity.*;
import com.university.fst.resourcemanagement.enums.*;
import com.university.fst.resourcemanagement.repository.*;
import com.university.fst.resourcemanagement.service.MaintenanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    private final PanneRepository panneRepository;
    private final RessourceMaterielleRepository ressourceMaterielleRepository;
    private final AffectationRessourceRepository affectationRessourceRepository;
    private final EnseignantRepository enseignantRepository;
    private final TechnicienRepository technicienRepository;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public MaintenanceServiceImpl(
            PanneRepository panneRepository,
            RessourceMaterielleRepository ressourceMaterielleRepository,
            AffectationRessourceRepository affectationRessourceRepository,
            EnseignantRepository enseignantRepository,
            TechnicienRepository technicienRepository,
            NotificationRepository notificationRepository,
            UserRepository userRepository
    ) {
        this.panneRepository = panneRepository;
        this.ressourceMaterielleRepository = ressourceMaterielleRepository;
        this.affectationRessourceRepository = affectationRessourceRepository;
        this.enseignantRepository = enseignantRepository;
        this.technicienRepository = technicienRepository;
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RessourcePanneSelectResponse> listerRessourcesPannePourEnseignant(Long enseignantUserId) {
        Enseignant enseignant = enseignantRepository.findByUserId(enseignantUserId)
                .orElseThrow(() -> new RuntimeException("Enseignant introuvable"));

        return affectationRessourceRepository
                .findByEnseignantIdOrDepartementIdOrderByDateAffectationDesc(
                        enseignant.getId(),
                        enseignant.getDepartement().getId()
                )
                .stream()
                .map(this::toRessourcePanneSelectResponse)
                .toList();
    }

    @Override
    @Transactional
    public PanneResponse signalerPanne(Long enseignantUserId, SignalerPanneRequest request) {
        Enseignant enseignant = enseignantRepository.findByUserId(enseignantUserId)
                .orElseThrow(() -> new RuntimeException("Enseignant introuvable"));

        RessourceMaterielle ressource = ressourceMaterielleRepository.findById(request.getRessourceId())
                .orElseThrow(() -> new RuntimeException("Ressource introuvable"));

        AffectationRessource affectation = affectationRessourceRepository.findByRessourceId(ressource.getId())
                .orElseThrow(() -> new RuntimeException("Cette ressource n'est pas affectée"));

        boolean autorise =
                (affectation.getEnseignant() != null && affectation.getEnseignant().getId().equals(enseignant.getId()))
                        || affectation.getDepartement().getId().equals(enseignant.getDepartement().getId());

        if (!autorise) {
            throw new RuntimeException("Vous ne pouvez signaler une panne que sur une ressource qui vous est affectée ou affectée à votre département");
        }

        Panne panne = new Panne();
        panne.setRessource(ressource);
        panne.setEnseignant(enseignant);
        panne.setDescriptionSignalement(request.getDescriptionSignalement());
        panne.setStatut(StatutPanne.SIGNALEE);

        return toResponse(panneRepository.save(panne));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PanneResponse> listerMesPannes(Long enseignantUserId) {
        return panneRepository.findByEnseignantUserIdOrderByDateSignalementDesc(enseignantUserId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PanneResponse> listerToutesLesPannes() {
        return panneRepository.findAllByOrderByDateSignalementDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public PanneResponse commencerIntervention(Long technicienUserId, Long panneId, InterventionPanneRequest request) {
        Technicien technicien = technicienRepository.findByUserId(technicienUserId)
                .orElseThrow(() -> new RuntimeException("Technicien introuvable"));

        Panne panne = getPanne(panneId);
        panne.setTechnicien(technicien);
        panne.setDateDebutIntervention(LocalDateTime.now());
        panne.setCommentaireIntervention(request.getCommentaireIntervention());
        panne.setStatut(StatutPanne.EN_COURS);

        return toResponse(panneRepository.save(panne));
    }

    @Override
    @Transactional
    public PanneResponse redigerConstat(Long technicienUserId, Long panneId, ConstatPanneRequest request) {
        Technicien technicien = technicienRepository.findByUserId(technicienUserId)
                .orElseThrow(() -> new RuntimeException("Technicien introuvable"));

        Panne panne = getPanne(panneId);

        if (panne.getTechnicien() == null || !panne.getTechnicien().getId().equals(technicien.getId())) {
            throw new RuntimeException("Vous devez d'abord prendre en charge cette panne");
        }

        String typeMateriel = panne.getRessource().getTypeMateriel().name();
        if ("IMPRIMANTE".equals(typeMateriel) && request.getOrdrePanne() != OrdrePanne.MATERIEL) {
            throw new RuntimeException("Les pannes des imprimantes doivent être uniquement d'ordre matériel");
        }

        if (!Boolean.TRUE.equals(request.getSevere())) {
            throw new RuntimeException("Le constat détaillé est réservé aux pannes sévères");
        }

        panne.setExplicationPanne(request.getExplicationPanne());
        panne.setDateApparition(request.getDateApparition());
        panne.setFrequence(request.getFrequence());
        panne.setOrdrePanne(request.getOrdrePanne());
        panne.setSevere(true);
        panne.setDateConstat(LocalDateTime.now());
        panne.setStatut(StatutPanne.CONSTAT_ENVOYE);

        Panne saved = panneRepository.save(panne);
        notifierResponsables("Nouveau constat de panne sévère sur la ressource " + saved.getRessource().getNumeroInventaire());

        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PanneResponse> listerPannesPourResponsable() {
        return panneRepository.findAllByOrderByDateSignalementDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public PanneResponse decider(Long panneId, DecisionPanneRequest request) {
        Panne panne = getPanne(panneId);

        if (!StatutPanne.CONSTAT_ENVOYE.equals(panne.getStatut())) {
            throw new RuntimeException("La décision du responsable n'est possible qu'après envoi du constat");
        }

        if (!garantieValide(panne.getRessource())) {
            throw new RuntimeException("La garantie de cette ressource est expirée");
        }

        panne.setDecisionResponsable(request.getDecision());
        panne.setMotifDecisionResponsable(request.getMotif());
        panne.setDateDecisionResponsable(LocalDateTime.now());

        if (request.getDecision() == DecisionMaintenance.REPARATION_FOURNISSEUR) {
            panne.setStatut(StatutPanne.DECISION_REPARATION);
        } else {
            panne.setStatut(StatutPanne.DECISION_REMPLACEMENT);
        }

        return toResponse(panneRepository.save(panne));
    }

    private Panne getPanne(Long panneId) {
        return panneRepository.findById(panneId)
                .orElseThrow(() -> new RuntimeException("Panne introuvable"));
    }

    private boolean garantieValide(RessourceMaterielle r) {
        int moisGarantie = r.getOffreFournisseurLigne().getDureeGarantieMois();
        LocalDate finGarantie = r.getDateLivraison().plusMonths(moisGarantie);
        return !LocalDate.now().isAfter(finGarantie);
    }

    private void notifierResponsables(String message) {
        List<User> responsables = userRepository.findAll().stream()
                .filter(u -> u.getRole() == Role.RESPONSABLE_RESOURCE)
                .toList();

        for (User user : responsables) {
            Notification n = new Notification();
            n.setUtilisateur(user);
            n.setMessage(message);
            n.setLu(false);
            n.setDateCreation(LocalDateTime.now());
            n.setTypeNotification(TypeNotification.INFORMATION);
            notificationRepository.save(n);
        }
    }

    private RessourcePanneSelectResponse toRessourcePanneSelectResponse(AffectationRessource a) {
        RessourceMaterielle r = a.getRessource();
        return new RessourcePanneSelectResponse(
                r.getId(),
                r.getNumeroInventaire(),
                r.getCodeBarres(),
                r.getTypeMateriel().name(),
                r.getMarque(),
                a.getDepartement().getNom(),
                a.getTypeBeneficiaire().name()
        );
    }

    private PanneResponse toResponse(Panne p) {
        RessourceMaterielle r = p.getRessource();
        AffectationRessource a = affectationRessourceRepository.findByRessourceId(r.getId()).orElse(null);

        return new PanneResponse(
                p.getId(),
                r.getId(),
                r.getNumeroInventaire(),
                r.getCodeBarres(),
                r.getTypeMateriel().name(),
                r.getMarque(),
                p.getDescriptionSignalement(),
                p.getEnseignant().getUser().getNom(),
                p.getEnseignant().getUser().getPrenom(),
                a != null ? a.getDepartement().getNom() : null,
                p.getStatut(),
                p.getDateSignalement(),
                p.getDateDebutIntervention(),
                p.getCommentaireIntervention(),
                p.isSevere(),
                p.getExplicationPanne(),
                p.getDateApparition(),
                p.getFrequence(),
                p.getOrdrePanne(),
                p.getDateConstat(),
                p.getTechnicien() != null ? p.getTechnicien().getUser().getNom() : null,
                p.getTechnicien() != null ? p.getTechnicien().getUser().getPrenom() : null,
                p.getDecisionResponsable(),
                p.getMotifDecisionResponsable(),
                p.getDateDecisionResponsable(),
                garantieValide(r)
        );
    }
}