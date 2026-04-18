package com.university.fst.resourcemanagement.service.impl;

import com.university.fst.resourcemanagement.dto.*;
import com.university.fst.resourcemanagement.entity.*;
import com.university.fst.resourcemanagement.enums.StatutOffreFournisseur;
import com.university.fst.resourcemanagement.enums.StatutRessource;
import com.university.fst.resourcemanagement.enums.TypeBeneficiaireAffectation;
import com.university.fst.resourcemanagement.repository.*;
import com.university.fst.resourcemanagement.service.ResponsableRessourceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResponsableRessourceServiceImpl implements ResponsableRessourceService {

    private final OffreFournisseurRepository offreFournisseurRepository;
    private final OffreFournisseurLigneRepository offreFournisseurLigneRepository;
    private final RessourceMaterielleRepository ressourceMaterielleRepository;
    private final AffectationRessourceRepository affectationRessourceRepository;
    private final DepartementRepository departementRepository;
    private final EnseignantRepository enseignantRepository;
    private final FournisseurRepository fournisseurRepository;

    public ResponsableRessourceServiceImpl(
            OffreFournisseurRepository offreFournisseurRepository,
            OffreFournisseurLigneRepository offreFournisseurLigneRepository,
            RessourceMaterielleRepository ressourceMaterielleRepository,
            AffectationRessourceRepository affectationRessourceRepository,
            DepartementRepository departementRepository,
            EnseignantRepository enseignantRepository,
            FournisseurRepository fournisseurRepository
    ) {
        this.offreFournisseurRepository = offreFournisseurRepository;
        this.offreFournisseurLigneRepository = offreFournisseurLigneRepository;
        this.ressourceMaterielleRepository = ressourceMaterielleRepository;
        this.affectationRessourceRepository = affectationRessourceRepository;
        this.departementRepository = departementRepository;
        this.enseignantRepository = enseignantRepository;
        this.fournisseurRepository = fournisseurRepository;
    }

    @Override
    @Transactional
    public ReceptionLivraisonResponse receptionnerLivraison(ReceptionLivraisonRequest request) {
        OffreFournisseur offre = offreFournisseurRepository.findById(request.getOffreId())
                .orElseThrow(() -> new RuntimeException("Offre fournisseur introuvable"));

        if (!StatutOffreFournisseur.ACCEPTEE.equals(offre.getStatut())) {
            throw new RuntimeException("Seule une offre acceptée peut être réceptionnée");
        }

        if (ressourceMaterielleRepository.existsByOffreFournisseurId(offre.getId())) {
            throw new RuntimeException("Cette livraison a déjà été réceptionnée");
        }

        Fournisseur fournisseur = offre.getFournisseur();
        mettreAJourInfosSociete(fournisseur, request);

        List<OffreFournisseurLigne> lignes = offreFournisseurLigneRepository
                .findByOffreFournisseurIdOrderByIdAsc(offre.getId());

        List<RessourceResponse> created = new ArrayList<>();

        for (OffreFournisseurLigne ligne : lignes) {
            for (int i = 0; i < ligne.getAppelOffreLigne().getQuantite(); i++) {
                RessourceMaterielle r = new RessourceMaterielle();
                r.setTypeMateriel(ligne.getAppelOffreLigne().getAffectationPrevue().getBesoinMateriel().getTypeMateriel());
                r.setMarque(ligne.getMarque());
                r.setCaracteristiques(ligne.getAppelOffreLigne().getDescriptionMateriel());
                r.setDateLivraison(request.getDateLivraison());
                r.setStatut(StatutRessource.DISPONIBLE);
                r.setFournisseur(fournisseur);
                r.setOffreFournisseur(offre);
                r.setOffreFournisseurLigne(ligne);

                long next = ressourceMaterielleRepository.count() + 1 + created.size();
                r.setNumeroInventaire(String.format("INV-%06d", next));
                r.setCodeBarres(String.format("BAR-%06d", next));

                RessourceMaterielle saved = ressourceMaterielleRepository.save(r);
                created.add(toRessourceResponse(saved));
            }
        }

        return new ReceptionLivraisonResponse(
                offre.getId(),
                fournisseur.getNomSociete(),
                request.getDateLivraison(),
                created.size(),
                created
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<RessourceResponse> listerRessources() {
        return ressourceMaterielleRepository.findAllByOrderByDateCreationDesc()
                .stream()
                .map(this::toRessourceResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RessourceResponse getRessource(Long ressourceId) {
        RessourceMaterielle r = getRessourceEntity(ressourceId);
        return toRessourceResponse(r);
    }

    @Override
    @Transactional
    public RessourceResponse modifierRessource(Long ressourceId, RessourceRequest request) {
        RessourceMaterielle r = getRessourceEntity(ressourceId);
        r.setTypeMateriel(request.getTypeMateriel());
        r.setMarque(request.getMarque());
        r.setCaracteristiques(request.getCaracteristiques());
        return toRessourceResponse(ressourceMaterielleRepository.save(r));
    }

    @Override
    @Transactional
    public void supprimerRessource(Long ressourceId) {
        RessourceMaterielle r = getRessourceEntity(ressourceId);

        if (affectationRessourceRepository.findByRessourceId(ressourceId).isPresent()) {
            throw new RuntimeException("Impossible de supprimer une ressource déjà affectée");
        }

        ressourceMaterielleRepository.delete(r);
    }

    @Override
    @Transactional
    public AffectationRessourceResponse affecterRessource(AffectationRessourceRequest request) {
        RessourceMaterielle r = getRessourceEntity(request.getRessourceId());

        if (affectationRessourceRepository.findByRessourceId(r.getId()).isPresent()) {
            throw new RuntimeException("Cette ressource est déjà affectée");
        }

        Departement departement = departementRepository.findById(request.getDepartementId())
                .orElseThrow(() -> new RuntimeException("Département introuvable"));

        AffectationRessource a = new AffectationRessource();
        a.setRessource(r);
        a.setDepartement(departement);

        if (request.getEnseignantId() != null) {
            Enseignant enseignant = enseignantRepository.findById(request.getEnseignantId())
                    .orElseThrow(() -> new RuntimeException("Enseignant introuvable"));

            if (!enseignant.getDepartement().getId().equals(departement.getId())) {
                throw new RuntimeException("L'enseignant doit appartenir au département choisi");
            }

            a.setEnseignant(enseignant);
            a.setTypeBeneficiaire(TypeBeneficiaireAffectation.ENSEIGNANT);
        } else {
            a.setEnseignant(null);
            a.setTypeBeneficiaire(TypeBeneficiaireAffectation.DEPARTEMENT);
        }

        r.setStatut(StatutRessource.AFFECTEE);
        ressourceMaterielleRepository.save(r);

        return toAffectationResponse(affectationRessourceRepository.save(a));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AffectationRessourceResponse> listerAffectations() {
        return affectationRessourceRepository.findAllByOrderByDateAffectationDesc()
                .stream()
                .map(this::toAffectationResponse)
                .toList();
    }

    @Override
    @Transactional
    public AffectationRessourceResponse modifierAffectation(Long affectationId, AffectationRessourceRequest request) {
        AffectationRessource a = affectationRessourceRepository.findById(affectationId)
                .orElseThrow(() -> new RuntimeException("Affectation introuvable"));

        if (!a.getRessource().getId().equals(request.getRessourceId())) {
            throw new RuntimeException("Impossible de changer la ressource liée à l'affectation");
        }

        Departement departement = departementRepository.findById(request.getDepartementId())
                .orElseThrow(() -> new RuntimeException("Département introuvable"));

        a.setDepartement(departement);

        if (request.getEnseignantId() != null) {
            Enseignant enseignant = enseignantRepository.findById(request.getEnseignantId())
                    .orElseThrow(() -> new RuntimeException("Enseignant introuvable"));

            if (!enseignant.getDepartement().getId().equals(departement.getId())) {
                throw new RuntimeException("L'enseignant doit appartenir au département choisi");
            }

            a.setEnseignant(enseignant);
            a.setTypeBeneficiaire(TypeBeneficiaireAffectation.ENSEIGNANT);
        } else {
            a.setEnseignant(null);
            a.setTypeBeneficiaire(TypeBeneficiaireAffectation.DEPARTEMENT);
        }

        return toAffectationResponse(affectationRessourceRepository.save(a));
    }

    @Override
    @Transactional
    public void supprimerAffectation(Long affectationId) {
        AffectationRessource a = affectationRessourceRepository.findById(affectationId)
                .orElseThrow(() -> new RuntimeException("Affectation introuvable"));

        RessourceMaterielle r = a.getRessource();
        r.setStatut(StatutRessource.DISPONIBLE);
        ressourceMaterielleRepository.save(r);

        affectationRessourceRepository.delete(a);
    }

    private RessourceMaterielle getRessourceEntity(Long id) {
        return ressourceMaterielleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ressource introuvable"));
    }

    private void mettreAJourInfosSociete(Fournisseur fournisseur, ReceptionLivraisonRequest request) {
        boolean changed = false;

        if (request.getLieu() != null && !request.getLieu().isBlank()) {
            fournisseur.setLieu(request.getLieu());
            changed = true;
        }
        if (request.getAdresse() != null && !request.getAdresse().isBlank()) {
            fournisseur.setAdresse(request.getAdresse());
            changed = true;
        }
        if (request.getSiteInternet() != null && !request.getSiteInternet().isBlank()) {
            fournisseur.setSiteInternet(request.getSiteInternet());
            changed = true;
        }
        if (request.getGerant() != null && !request.getGerant().isBlank()) {
            fournisseur.setGerant(request.getGerant());
            changed = true;
        }

        if (changed) {
            fournisseurRepository.save(fournisseur);
        }
    }

    private RessourceResponse toRessourceResponse(RessourceMaterielle r) {
        return new RessourceResponse(
                r.getId(),
                r.getNumeroInventaire(),
                r.getCodeBarres(),
                r.getTypeMateriel(),
                r.getMarque(),
                r.getCaracteristiques(),
                r.getDateLivraison(),
                r.getStatut(),
                r.getFournisseur().getId(),
                r.getFournisseur().getNomSociete(),
                r.getOffreFournisseur().getId(),
                r.getDateCreation()
        );
    }

    private AffectationRessourceResponse toAffectationResponse(AffectationRessource a) {
        return new AffectationRessourceResponse(
                a.getId(),
                a.getRessource().getId(),
                a.getRessource().getNumeroInventaire(),
                a.getRessource().getCodeBarres(),
                a.getDepartement().getNom(),
                a.getEnseignant() != null ? a.getEnseignant().getId() : null,
                a.getEnseignant() != null ? a.getEnseignant().getUser().getNom() : null,
                a.getEnseignant() != null ? a.getEnseignant().getUser().getPrenom() : null,
                a.getTypeBeneficiaire(),
                a.getDateAffectation()
        );
    }
}