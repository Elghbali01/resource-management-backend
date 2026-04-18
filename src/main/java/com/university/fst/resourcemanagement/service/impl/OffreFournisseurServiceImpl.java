package com.university.fst.resourcemanagement.service.impl;

import com.university.fst.resourcemanagement.dto.CreateOffreFournisseurRequest;
import com.university.fst.resourcemanagement.dto.CreateOffreLigneRequest;
import com.university.fst.resourcemanagement.dto.DecisionOffreRequest;
import com.university.fst.resourcemanagement.dto.OffreFournisseurLigneResponse;
import com.university.fst.resourcemanagement.dto.OffreFournisseurResponse;
import com.university.fst.resourcemanagement.entity.*;
import com.university.fst.resourcemanagement.enums.StatutAppelOffre;
import com.university.fst.resourcemanagement.enums.StatutOffreFournisseur;
import com.university.fst.resourcemanagement.repository.*;
import com.university.fst.resourcemanagement.service.OffreFournisseurService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OffreFournisseurServiceImpl implements OffreFournisseurService {

    private final OffreFournisseurRepository offreFournisseurRepository;
    private final OffreFournisseurLigneRepository offreFournisseurLigneRepository;
    private final AppelOffreRepository appelOffreRepository;
    private final AppelOffreLigneRepository appelOffreLigneRepository;
    private final FournisseurRepository fournisseurRepository;
    private final DepartementRepository departementRepository;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public OffreFournisseurServiceImpl(
            OffreFournisseurRepository offreFournisseurRepository,
            OffreFournisseurLigneRepository offreFournisseurLigneRepository,
            AppelOffreRepository appelOffreRepository,
            AppelOffreLigneRepository appelOffreLigneRepository,
            FournisseurRepository fournisseurRepository,
            DepartementRepository departementRepository,
            NotificationRepository notificationRepository,
            UserRepository userRepository
    ) {
        this.offreFournisseurRepository = offreFournisseurRepository;
        this.offreFournisseurLigneRepository = offreFournisseurLigneRepository;
        this.appelOffreRepository = appelOffreRepository;
        this.appelOffreLigneRepository = appelOffreLigneRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.departementRepository = departementRepository;
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public OffreFournisseurResponse soumettreOffre(Long fournisseurUserId, CreateOffreFournisseurRequest request) {

        Fournisseur fournisseur = fournisseurRepository.findByUser_Email(
                        userRepository.findById(fournisseurUserId)
                                .orElseThrow(() -> new RuntimeException("Utilisateur fournisseur introuvable"))
                                .getEmail()
                )
                .orElseThrow(() -> new RuntimeException("Fournisseur introuvable"));

        if (fournisseur.isBlacklisted()) {
            throw new RuntimeException("Ce fournisseur est blacklisté et ne peut pas soumettre d'offre");
        }

        AppelOffre appelOffre = appelOffreRepository.findById(request.getAppelOffreId())
                .orElseThrow(() -> new RuntimeException("Appel d'offre introuvable"));

        LocalDate today = LocalDate.now();
        if (!StatutAppelOffre.OUVERT.equals(appelOffre.getStatut())
                || today.isBefore(appelOffre.getDateDebut())
                || today.isAfter(appelOffre.getDateFin())) {
            throw new RuntimeException("Cet appel d'offre n'est plus ouvert aux soumissions");
        }

        if (offreFournisseurRepository.existsByAppelOffreIdAndFournisseurId(appelOffre.getId(), fournisseur.getId())) {
            throw new RuntimeException("Vous avez déjà soumis une offre pour cet appel d'offre");
        }

        List<AppelOffreLigne> lignesAppel = appelOffreLigneRepository.findByAppelOffreIdOrderByIdAsc(appelOffre.getId());
        Set<Long> idsLignesAttendus = lignesAppel.stream().map(AppelOffreLigne::getId).collect(Collectors.toSet());
        Set<Long> idsLignesRecus = request.getLignes().stream()
                .map(CreateOffreLigneRequest::getAppelOffreLigneId)
                .collect(Collectors.toSet());

        if (!idsLignesAttendus.equals(idsLignesRecus)) {
            throw new RuntimeException("L'offre doit couvrir toutes les lignes de l'appel d'offre, sans oubli ni doublon");
        }

        OffreFournisseur offre = new OffreFournisseur();
        offre.setAppelOffre(appelOffre);
        offre.setFournisseur(fournisseur);
        offre.setStatut(StatutOffreFournisseur.SOUMISE);
        offre.setMontantTotal(BigDecimal.ZERO);

        OffreFournisseur savedOffre = offreFournisseurRepository.save(offre);

        BigDecimal total = BigDecimal.ZERO;
        Map<Long, AppelOffreLigne> lignesMap = lignesAppel.stream()
                .collect(Collectors.toMap(AppelOffreLigne::getId, l -> l));

        for (CreateOffreLigneRequest ligneRequest : request.getLignes()) {
            AppelOffreLigne appelLigne = lignesMap.get(ligneRequest.getAppelOffreLigneId());

            BigDecimal totalLigne = ligneRequest.getPrixUnitaire()
                    .multiply(BigDecimal.valueOf(appelLigne.getQuantite()));

            OffreFournisseurLigne ligne = new OffreFournisseurLigne();
            ligne.setOffreFournisseur(savedOffre);
            ligne.setAppelOffreLigne(appelLigne);
            ligne.setMarque(ligneRequest.getMarque());
            ligne.setPrixUnitaire(ligneRequest.getPrixUnitaire());
            ligne.setPrixTotalLigne(totalLigne);
            ligne.setDureeGarantieMois(ligneRequest.getDureeGarantieMois());
            ligne.setDateLivraisonPrevue(ligneRequest.getDateLivraisonPrevue());

            offreFournisseurLigneRepository.save(ligne);
            total = total.add(totalLigne);
        }

        savedOffre.setMontantTotal(total);
        offreFournisseurRepository.save(savedOffre);

        return buildResponse(savedOffre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OffreFournisseurResponse> listerMesOffres(Long fournisseurUserId) {
        Fournisseur fournisseur = fournisseurRepository.findByUser_Email(
                        userRepository.findById(fournisseurUserId)
                                .orElseThrow(() -> new RuntimeException("Utilisateur fournisseur introuvable"))
                                .getEmail()
                )
                .orElseThrow(() -> new RuntimeException("Fournisseur introuvable"));

        return offreFournisseurRepository.findByFournisseurIdOrderByDateSoumissionDesc(fournisseur.getId())
                .stream()
                .map(this::buildResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OffreFournisseurResponse> listerOffresAppelOffre(Long appelOffreId) {
        return offreFournisseurRepository.findByAppelOffreIdOrderByDateSoumissionAsc(appelOffreId)
                .stream()
                .map(this::buildResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OffreFournisseurResponse eliminerOffre(Long offreId, DecisionOffreRequest request) {
        OffreFournisseur offre = offreFournisseurRepository.findById(offreId)
                .orElseThrow(() -> new RuntimeException("Offre introuvable"));

        if (StatutOffreFournisseur.ACCEPTEE.equals(offre.getStatut())) {
            throw new RuntimeException("Impossible d'éliminer une offre déjà acceptée");
        }

        offre.setStatut(StatutOffreFournisseur.ELIMINEE);
        offre.setMotifDecision(request.getMotif());
        offreFournisseurRepository.save(offre);

        notifierFournisseur(
                offre.getFournisseur().getUser(),
                "Votre offre a été éliminée. Motif : " + request.getMotif()
        );

        return buildResponse(offre);
    }

    @Override
    @Transactional
    public OffreFournisseurResponse accepterOffre(Long offreId, DecisionOffreRequest request) {
        OffreFournisseur offre = offreFournisseurRepository.findById(offreId)
                .orElseThrow(() -> new RuntimeException("Offre introuvable"));

        if (!StatutOffreFournisseur.SOUMISE.equals(offre.getStatut())) {
            throw new RuntimeException("Seule une offre soumise peut être acceptée");
        }

        List<OffreFournisseurLigne> lignes = offreFournisseurLigneRepository.findByOffreFournisseurIdOrderByIdAsc(offre.getId());
        Map<Long, BigDecimal> coutParDepartement = new HashMap<>();

        for (OffreFournisseurLigne ligne : lignes) {
            String departementNom = ligne.getAppelOffreLigne().getDepartementNom();

            Long departementId = departementRepository.findAll().stream()
                    .filter(d -> d.getNom().equals(departementNom))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Département introuvable : " + departementNom))
                    .getId();

            coutParDepartement.put(
                    departementId,
                    coutParDepartement.getOrDefault(departementId, BigDecimal.ZERO).add(ligne.getPrixTotalLigne())
            );
        }

        for (Map.Entry<Long, BigDecimal> entry : coutParDepartement.entrySet()) {
            Departement dept = departementRepository.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Département introuvable"));

            BigDecimal budget = dept.getBudget() != null ? dept.getBudget() : BigDecimal.ZERO;

            if (budget.compareTo(entry.getValue()) < 0) {
                throw new RuntimeException(
                        "Budget insuffisant pour le département '" + dept.getNom()
                                + "' : budget actuel = " + budget
                                + ", montant requis = " + entry.getValue()
                );
            }
        }

        // Débit des budgets
        for (Map.Entry<Long, BigDecimal> entry : coutParDepartement.entrySet()) {
            Departement dept = departementRepository.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Département introuvable"));
            dept.setBudget(dept.getBudget().subtract(entry.getValue()));
            departementRepository.save(dept);
        }

        // Accepter l'offre choisie
        offre.setStatut(StatutOffreFournisseur.ACCEPTEE);
        offre.setMotifDecision(request.getMotif());
        offreFournisseurRepository.save(offre);

        notifierFournisseur(
                offre.getFournisseur().getUser(),
                "Votre offre a été acceptée. " + request.getMotif()
        );

        // Rejeter les autres offres du même appel d'offre
        List<OffreFournisseur> autres = offreFournisseurRepository
                .findByAppelOffreIdOrderByDateSoumissionAsc(offre.getAppelOffre().getId());

        for (OffreFournisseur autre : autres) {
            if (autre.getId().equals(offre.getId())) continue;
            if (StatutOffreFournisseur.ACCEPTEE.equals(autre.getStatut())) continue;

            autre.setStatut(StatutOffreFournisseur.REJETEE);
            autre.setMotifDecision("Offre non retenue. Une autre offre a été acceptée.");
            offreFournisseurRepository.save(autre);

            notifierFournisseur(
                    autre.getFournisseur().getUser(),
                    "Votre offre a été rejetée. Une autre offre a été retenue."
            );
        }

        // Fermer l'appel d'offre
        AppelOffre appelOffre = offre.getAppelOffre();
        appelOffre.setStatut(StatutAppelOffre.FERME);
        appelOffreRepository.save(appelOffre);

        return buildResponse(offre);
    }

    private void notifierFournisseur(User user, String message) {
        Notification n = new Notification();
        n.setUtilisateur(user);
        n.setMessage(message);
        n.setLu(false);
        n.setDateCreation(java.time.LocalDateTime.now());
        n.setTypeNotification(com.university.fst.resourcemanagement.enums.TypeNotification.INFORMATION);
        notificationRepository.save(n);
    }

    private OffreFournisseurResponse buildResponse(OffreFournisseur offre) {
        List<OffreFournisseurLigneResponse> lignes = offreFournisseurLigneRepository
                .findByOffreFournisseurIdOrderByIdAsc(offre.getId())
                .stream()
                .map(this::toLigneResponse)
                .collect(Collectors.toList());

        return new OffreFournisseurResponse(
                offre.getId(),
                offre.getAppelOffre().getId(),
                offre.getAppelOffre().getTitre(),
                offre.getFournisseur().getId(),
                offre.getFournisseur().getNomSociete(),
                offre.getFournisseur().getUser().getEmail(),
                offre.getStatut(),
                offre.getMontantTotal(),
                offre.getDateSoumission(),
                offre.getMotifDecision(),
                lignes
        );
    }

    private OffreFournisseurLigneResponse toLigneResponse(OffreFournisseurLigne l) {
        return new OffreFournisseurLigneResponse(
                l.getId(),
                l.getAppelOffreLigne().getId(),
                l.getAppelOffreLigne().getDescriptionMateriel(),
                l.getAppelOffreLigne().getQuantite(),
                l.getAppelOffreLigne().getDepartementNom(),
                l.getAppelOffreLigne().getEnseignantNom(),
                l.getAppelOffreLigne().getEnseignantPrenom(),
                l.getMarque(),
                l.getPrixUnitaire(),
                l.getPrixTotalLigne(),
                l.getDureeGarantieMois(),
                l.getDateLivraisonPrevue()
        );
    }
}