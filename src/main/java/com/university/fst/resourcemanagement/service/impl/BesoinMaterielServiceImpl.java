package com.university.fst.resourcemanagement.service.impl;

import com.university.fst.resourcemanagement.dto.BesoinRequest;
import com.university.fst.resourcemanagement.dto.BesoinResponse;
import com.university.fst.resourcemanagement.entity.BesoinMateriel;
import com.university.fst.resourcemanagement.entity.ChefDepartement;
import com.university.fst.resourcemanagement.entity.DemandeCollecte;
import com.university.fst.resourcemanagement.entity.Enseignant;
import com.university.fst.resourcemanagement.enums.NatureBesoin;
import com.university.fst.resourcemanagement.enums.StatutDemande;
import com.university.fst.resourcemanagement.repository.BesoinMaterielRepository;
import com.university.fst.resourcemanagement.repository.ChefDepartementRepository;
import com.university.fst.resourcemanagement.repository.DemandeCollecteRepository;
import com.university.fst.resourcemanagement.repository.EnseignantRepository;
import com.university.fst.resourcemanagement.service.BesoinMaterielService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BesoinMaterielServiceImpl implements BesoinMaterielService {

    private final BesoinMaterielRepository besoinMaterielRepository;
    private final EnseignantRepository enseignantRepository;
    private final DemandeCollecteRepository demandeCollecteRepository;
    private final ChefDepartementRepository chefDepartementRepository;

    public BesoinMaterielServiceImpl(
            BesoinMaterielRepository besoinMaterielRepository,
            EnseignantRepository enseignantRepository,
            DemandeCollecteRepository demandeCollecteRepository,
            ChefDepartementRepository chefDepartementRepository
    ) {
        this.besoinMaterielRepository = besoinMaterielRepository;
        this.enseignantRepository = enseignantRepository;
        this.demandeCollecteRepository = demandeCollecteRepository;
        this.chefDepartementRepository = chefDepartementRepository;
    }

    @Override
    @Transactional
    public BesoinResponse soumettreBesoin(Long enseignantUserId, BesoinRequest request) {

        Enseignant enseignant = getEnseignantByUserId(enseignantUserId);
        DemandeCollecte demande = getDemandeById(request.getDemandeId());

        verifierQueLaDemandeEstSoumettable(demande);
        verifierQueLEnseignantAppartientAuBonDepartement(enseignant, demande);

        BesoinMateriel besoin = new BesoinMateriel();
        besoin.setDemandeCollecte(demande);
        besoin.setEnseignant(enseignant);
        besoin.setNatureBesoin(NatureBesoin.INDIVIDUEL);
        besoin.setTypeMateriel(request.getTypeMateriel());
        besoin.setQuantite(request.getQuantite());
        besoin.setMarqueSouhaitee(request.getMarqueSouhaitee());
        besoin.setCaracteristiques(request.getCaracteristiques());
        besoin.setJustification(request.getJustification());
        besoin.setDateSoumission(LocalDateTime.now());
        besoin.setDerniereModification(LocalDateTime.now());

        return toResponse(besoinMaterielRepository.save(besoin));
    }

    @Override
    @Transactional
    public BesoinResponse modifierBesoin(Long enseignantUserId, Long besoinId, BesoinRequest request) {

        BesoinMateriel besoin = besoinMaterielRepository.findByIdAndEnseignantUserId(besoinId, enseignantUserId)
                .orElseThrow(() -> new RuntimeException("Besoin introuvable pour cet enseignant"));

        DemandeCollecte demande = besoin.getDemandeCollecte();
        verifierQueLaDemandeEstSoumettable(demande);

        if (!demande.getId().equals(request.getDemandeId())) {
            throw new RuntimeException("Impossible de changer la demande liée à ce besoin");
        }

        besoin.setTypeMateriel(request.getTypeMateriel());
        besoin.setQuantite(request.getQuantite());
        besoin.setMarqueSouhaitee(request.getMarqueSouhaitee());
        besoin.setCaracteristiques(request.getCaracteristiques());
        besoin.setJustification(request.getJustification());
        besoin.setDerniereModification(LocalDateTime.now());

        return toResponse(besoinMaterielRepository.save(besoin));
    }

    @Override
    @Transactional
    public void supprimerBesoin(Long enseignantUserId, Long besoinId) {

        BesoinMateriel besoin = besoinMaterielRepository.findByIdAndEnseignantUserId(besoinId, enseignantUserId)
                .orElseThrow(() -> new RuntimeException("Besoin introuvable pour cet enseignant"));

        verifierQueLaDemandeEstSoumettable(besoin.getDemandeCollecte());

        besoinMaterielRepository.delete(besoin);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BesoinResponse> listerMesBesoins(Long enseignantUserId, Long demandeId) {

        Enseignant enseignant = getEnseignantByUserId(enseignantUserId);
        DemandeCollecte demande = getDemandeById(demandeId);

        verifierQueLEnseignantAppartientAuBonDepartement(enseignant, demande);

        return besoinMaterielRepository
                .findByDemandeCollecteIdAndEnseignantUserIdOrderByDateSoumissionDesc(demandeId, enseignantUserId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BesoinResponse> listerBesoinsPourChef(Long chefUserId, Long demandeId) {

        ChefDepartement chef = chefDepartementRepository.findByUserId(chefUserId)
                .orElseThrow(() -> new RuntimeException("Chef de département introuvable"));

        DemandeCollecte demande = demandeCollecteRepository
                .findByIdAndDepartementId(demandeId, chef.getDepartement().getId())
                .orElseThrow(() -> new RuntimeException("Demande introuvable pour le département du chef"));

        return besoinMaterielRepository
                .findByDemandeCollecteIdOrderByDateSoumissionDesc(demande.getId())
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private Enseignant getEnseignantByUserId(Long userId) {
        return enseignantRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Enseignant introuvable"));
    }

    private DemandeCollecte getDemandeById(Long demandeId) {
        return demandeCollecteRepository.findById(demandeId)
                .orElseThrow(() -> new RuntimeException("Demande de collecte introuvable : " + demandeId));
    }

    private void verifierQueLEnseignantAppartientAuBonDepartement(
            Enseignant enseignant,
            DemandeCollecte demande
    ) {
        if (!enseignant.getDepartement().getId().equals(demande.getDepartement().getId())) {
            throw new RuntimeException("Cette demande n'appartient pas au département de l'enseignant");
        }
    }

    private void verifierQueLaDemandeEstSoumettable(DemandeCollecte demande) {
        if (!StatutDemande.OUVERTE.equals(demande.getStatut())) {
            throw new RuntimeException("Les besoins ne peuvent être soumis que pour une demande ouverte");
        }

        LocalDate aujourdHui = LocalDate.now();
        if (demande.getDateLimite() != null && aujourdHui.isAfter(demande.getDateLimite())) {
            throw new RuntimeException("La date limite de soumission est dépassée");
        }
    }

    private BesoinResponse toResponse(BesoinMateriel besoin) {
        return new BesoinResponse(
                besoin.getId(),
                besoin.getDemandeCollecte().getId(),
                besoin.getDemandeCollecte().getTitre(),
                besoin.getDemandeCollecte().getDateLimite(),
                besoin.getTypeMateriel(),
                besoin.getNatureBesoin(),
                besoin.getQuantite(),
                besoin.getMarqueSouhaitee(),
                besoin.getCaracteristiques(),
                besoin.getJustification(),
                besoin.getDateSoumission(),
                besoin.getDerniereModification(),
                besoin.getEnseignant() != null ? besoin.getEnseignant().getId() : null,
                besoin.getEnseignant() != null ? besoin.getEnseignant().getUser().getId() : null,
                besoin.getEnseignant() != null ? besoin.getEnseignant().getUser().getNom() : null,
                besoin.getEnseignant() != null ? besoin.getEnseignant().getUser().getPrenom() : null,
                besoin.getDemandeCollecte().getDepartement().getNom()
        );
    }
}