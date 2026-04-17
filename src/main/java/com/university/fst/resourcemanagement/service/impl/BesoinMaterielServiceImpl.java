package com.university.fst.resourcemanagement.service.impl;

import com.university.fst.resourcemanagement.dto.BesoinRequest;
import com.university.fst.resourcemanagement.dto.BesoinResponse;
import com.university.fst.resourcemanagement.entity.BesoinMateriel;
import com.university.fst.resourcemanagement.entity.ChefDepartement;
import com.university.fst.resourcemanagement.entity.DemandeCollecte;
import com.university.fst.resourcemanagement.entity.Enseignant;
import com.university.fst.resourcemanagement.enums.NatureBesoin;
import com.university.fst.resourcemanagement.enums.StatutDemande;
import com.university.fst.resourcemanagement.enums.TypeMateriel;
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
        validerChampsMetier(request);

        BesoinMateriel besoin = new BesoinMateriel();
        besoin.setDemandeCollecte(demande);
        besoin.setEnseignant(enseignant);
        besoin.setNatureBesoin(NatureBesoin.INDIVIDUEL);
        besoin.setTypeMateriel(request.getTypeMateriel());
        besoin.setQuantite(request.getQuantite());
        besoin.setMarqueSouhaitee(clean(request.getMarqueSouhaitee()));
        besoin.setCaracteristiques(construireCaracteristiques(request));
        besoin.setJustification(clean(request.getJustification()));
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

        validerChampsMetier(request);

        besoin.setTypeMateriel(request.getTypeMateriel());
        besoin.setQuantite(request.getQuantite());
        besoin.setMarqueSouhaitee(clean(request.getMarqueSouhaitee()));
        besoin.setCaracteristiques(construireCaracteristiques(request));
        besoin.setJustification(clean(request.getJustification()));
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

    private void validerChampsMetier(BesoinRequest request) {
        if (request.getTypeMateriel() == null) {
            throw new RuntimeException("Le type de matériel est obligatoire");
        }

        if (clean(request.getMarqueSouhaitee()) == null) {
            throw new RuntimeException("La marque est obligatoire");
        }

        if (clean(request.getJustification()) == null) {
            throw new RuntimeException("La justification est obligatoire");
        }

        if (TypeMateriel.ORDINATEUR.equals(request.getTypeMateriel())) {
            if (clean(request.getCpu()) == null) {
                throw new RuntimeException("Le CPU est obligatoire pour un ordinateur");
            }
            if (clean(request.getRam()) == null) {
                throw new RuntimeException("La RAM est obligatoire pour un ordinateur");
            }
            if (clean(request.getDisqueDur()) == null) {
                throw new RuntimeException("Le disque dur est obligatoire pour un ordinateur");
            }
            if (clean(request.getEcran()) == null) {
                throw new RuntimeException("L'écran est obligatoire pour un ordinateur");
            }
        }

        if (TypeMateriel.IMPRIMANTE.equals(request.getTypeMateriel())) {
            if (clean(request.getVitesseImpression()) == null) {
                throw new RuntimeException("La vitesse d'impression est obligatoire pour une imprimante");
            }
            if (clean(request.getResolution()) == null) {
                throw new RuntimeException("La résolution est obligatoire pour une imprimante");
            }
        }
    }

    private String construireCaracteristiques(BesoinRequest request) {
        if (TypeMateriel.ORDINATEUR.equals(request.getTypeMateriel())) {
            return String.format(
                    "CPU: %s | RAM: %s | Disque dur: %s | Ecran: %s",
                    clean(request.getCpu()),
                    clean(request.getRam()),
                    clean(request.getDisqueDur()),
                    clean(request.getEcran())
            );
        }

        if (TypeMateriel.IMPRIMANTE.equals(request.getTypeMateriel())) {
            return String.format(
                    "Vitesse d'impression: %s | Résolution: %s",
                    clean(request.getVitesseImpression()),
                    clean(request.getResolution())
            );
        }

        return null;
    }

    private ParsedCaracteristiques parseCaracteristiques(TypeMateriel typeMateriel, String caracteristiques) {
        ParsedCaracteristiques parsed = new ParsedCaracteristiques();

        if (caracteristiques == null || caracteristiques.isBlank()) {
            return parsed;
        }

        String[] parts = caracteristiques.split("\\|");
        for (String rawPart : parts) {
            String part = rawPart.trim();
            int idx = part.indexOf(':');
            if (idx <= 0) {
                continue;
            }

            String key = part.substring(0, idx).trim().toLowerCase();
            String value = part.substring(idx + 1).trim();

            if (TypeMateriel.ORDINATEUR.equals(typeMateriel)) {
                if (key.equals("cpu")) parsed.cpu = value;
                else if (key.equals("ram")) parsed.ram = value;
                else if (key.equals("disque dur")) parsed.disqueDur = value;
                else if (key.equals("ecran") || key.equals("écran")) parsed.ecran = value;
            }

            if (TypeMateriel.IMPRIMANTE.equals(typeMateriel)) {
                if (key.equals("vitesse d'impression")) parsed.vitesseImpression = value;
                else if (key.equals("résolution") || key.equals("resolution")) parsed.resolution = value;
            }
        }

        return parsed;
    }

    private String clean(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private BesoinResponse toResponse(BesoinMateriel besoin) {
        ParsedCaracteristiques parsed = parseCaracteristiques(
                besoin.getTypeMateriel(),
                besoin.getCaracteristiques()
        );

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
                besoin.getDemandeCollecte().getDepartement().getNom(),
                parsed.cpu,
                parsed.ram,
                parsed.disqueDur,
                parsed.ecran,
                parsed.vitesseImpression,
                parsed.resolution
        );
    }

    private static class ParsedCaracteristiques {
        private String cpu;
        private String ram;
        private String disqueDur;
        private String ecran;
        private String vitesseImpression;
        private String resolution;
    }
}