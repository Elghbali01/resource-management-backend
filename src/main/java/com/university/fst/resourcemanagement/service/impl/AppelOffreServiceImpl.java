package com.university.fst.resourcemanagement.service.impl;

import com.university.fst.resourcemanagement.dto.AppelOffreLigneResponse;
import com.university.fst.resourcemanagement.dto.AppelOffreResponse;
import com.university.fst.resourcemanagement.dto.CreateAppelOffreRequest;
import com.university.fst.resourcemanagement.entity.AffectationPrevue;
import com.university.fst.resourcemanagement.entity.AppelOffre;
import com.university.fst.resourcemanagement.entity.AppelOffreLigne;
import com.university.fst.resourcemanagement.entity.DemandeCollecte;
import com.university.fst.resourcemanagement.entity.User;
import com.university.fst.resourcemanagement.enums.StatutAppelOffre;
import com.university.fst.resourcemanagement.enums.StatutDemande;
import com.university.fst.resourcemanagement.repository.AffectationPrevueRepository;
import com.university.fst.resourcemanagement.repository.AppelOffreLigneRepository;
import com.university.fst.resourcemanagement.repository.AppelOffreRepository;
import com.university.fst.resourcemanagement.repository.DemandeCollecteRepository;
import com.university.fst.resourcemanagement.repository.UserRepository;
import com.university.fst.resourcemanagement.service.AppelOffreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AppelOffreServiceImpl implements AppelOffreService {

    private final AppelOffreRepository appelOffreRepository;
    private final AppelOffreLigneRepository appelOffreLigneRepository;
    private final DemandeCollecteRepository demandeCollecteRepository;
    private final AffectationPrevueRepository affectationPrevueRepository;
    private final UserRepository userRepository;

    public AppelOffreServiceImpl(
            AppelOffreRepository appelOffreRepository,
            AppelOffreLigneRepository appelOffreLigneRepository,
            DemandeCollecteRepository demandeCollecteRepository,
            AffectationPrevueRepository affectationPrevueRepository,
            UserRepository userRepository
    ) {
        this.appelOffreRepository = appelOffreRepository;
        this.appelOffreLigneRepository = appelOffreLigneRepository;
        this.demandeCollecteRepository = demandeCollecteRepository;
        this.affectationPrevueRepository = affectationPrevueRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public AppelOffreResponse creerAppelOffre(Long responsableUserId, CreateAppelOffreRequest request) {

        if (request.getDateFin().isBefore(request.getDateDebut())) {
            throw new RuntimeException("La date de fin doit être postérieure ou égale à la date de début");
        }

        User responsable = userRepository.findById(responsableUserId)
                .orElseThrow(() -> new RuntimeException("Responsable introuvable"));

        AppelOffre appelOffre = new AppelOffre();
        appelOffre.setTitre(request.getTitre());
        appelOffre.setDescription(request.getDescription());
        appelOffre.setDateDebut(request.getDateDebut());
        appelOffre.setDateFin(request.getDateFin());
        appelOffre.setStatut(StatutAppelOffre.OUVERT);
        appelOffre.setCreePar(responsable);

        AppelOffre savedAppel = appelOffreRepository.save(appelOffre);

        for (Long demandeId : request.getDemandeIds()) {
            DemandeCollecte demande = demandeCollecteRepository.findById(demandeId)
                    .orElseThrow(() -> new RuntimeException("Demande transmise introuvable : " + demandeId));

            if (!StatutDemande.TRANSMISE.equals(demande.getStatut())) {
                throw new RuntimeException("La demande " + demandeId + " doit être au statut TRANSMISE");
            }

            List<AffectationPrevue> affectations = affectationPrevueRepository
                    .findByDemandeCollecteIdOrderByIdAsc(demandeId);

            if (affectations.isEmpty()) {
                throw new RuntimeException("Aucune affectation prévue trouvée pour la demande " + demandeId);
            }

            for (AffectationPrevue affectation : affectations) {
                AppelOffreLigne ligne = new AppelOffreLigne();
                ligne.setAppelOffre(savedAppel);
                ligne.setDemandeCollecte(demande);
                ligne.setAffectationPrevue(affectation);
                ligne.setDescriptionMateriel(affectation.getDescriptionMateriel());
                ligne.setQuantite(affectation.getQuantite());
                ligne.setDepartementNom(affectation.getDepartement().getNom());
                ligne.setTypeAffectation(affectation.getTypeAffectation());

                if (affectation.getEnseignant() != null && affectation.getEnseignant().getUser() != null) {
                    ligne.setEnseignantNom(affectation.getEnseignant().getUser().getNom());
                    ligne.setEnseignantPrenom(affectation.getEnseignant().getUser().getPrenom());
                }

                appelOffreLigneRepository.save(ligne);
            }
        }

        return buildResponse(savedAppel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppelOffreResponse> listerAppelsOffreResponsable() {
        return appelOffreRepository.findAllByOrderByDateCreationDesc()
                .stream()
                .map(this::buildResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AppelOffreResponse getAppelOffreResponsable(Long appelOffreId) {
        AppelOffre appelOffre = appelOffreRepository.findById(appelOffreId)
                .orElseThrow(() -> new RuntimeException("Appel d'offre introuvable"));
        return buildResponse(appelOffre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppelOffreResponse> listerAppelsOffreActuelsPourFournisseur() {
        LocalDate today = LocalDate.now();

        return appelOffreRepository
                .findByStatutAndDateDebutLessThanEqualAndDateFinGreaterThanEqualOrderByDateCreationDesc(
                        StatutAppelOffre.OUVERT,
                        today,
                        today
                )
                .stream()
                .map(this::buildResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AppelOffreResponse getAppelOffrePourFournisseur(Long appelOffreId) {
        AppelOffre appelOffre = appelOffreRepository.findById(appelOffreId)
                .orElseThrow(() -> new RuntimeException("Appel d'offre introuvable"));

        LocalDate today = LocalDate.now();
        if (!StatutAppelOffre.OUVERT.equals(appelOffre.getStatut())
                || today.isBefore(appelOffre.getDateDebut())
                || today.isAfter(appelOffre.getDateFin())) {
            throw new RuntimeException("Cet appel d'offre n'est pas actuellement consultable");
        }

        return buildResponse(appelOffre);
    }

    private AppelOffreResponse buildResponse(AppelOffre appelOffre) {
        List<AppelOffreLigneResponse> lignes = appelOffreLigneRepository
                .findByAppelOffreIdOrderByIdAsc(appelOffre.getId())
                .stream()
                .map(this::toLigneResponse)
                .collect(Collectors.toList());

        Set<Long> demandeIds = new HashSet<>();
        for (AppelOffreLigneResponse ligne : lignes) {
            demandeIds.add(ligne.getDemandeId());
        }

        return new AppelOffreResponse(
                appelOffre.getId(),
                appelOffre.getTitre(),
                appelOffre.getDescription(),
                appelOffre.getDateDebut(),
                appelOffre.getDateFin(),
                appelOffre.getStatut(),
                appelOffre.getDateCreation(),
                appelOffre.getCreePar().getNom(),
                appelOffre.getCreePar().getPrenom(),
                lignes.size(),
                demandeIds.size(),
                lignes
        );
    }

    private AppelOffreLigneResponse toLigneResponse(AppelOffreLigne ligne) {
        return new AppelOffreLigneResponse(
                ligne.getId(),
                ligne.getDemandeCollecte().getId(),
                ligne.getTypeAffectation(),
                ligne.getQuantite(),
                ligne.getDescriptionMateriel(),
                ligne.getDepartementNom(),
                ligne.getEnseignantNom(),
                ligne.getEnseignantPrenom()
        );
    }
}