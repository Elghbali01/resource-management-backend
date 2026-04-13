package com.university.fst.resourcemanagement.service.impl;

import com.university.fst.resourcemanagement.dto.EnseignantResponse;
import com.university.fst.resourcemanagement.entity.ChefDepartement;
import com.university.fst.resourcemanagement.entity.Enseignant;
import com.university.fst.resourcemanagement.repository.ChefDepartementRepository;
import com.university.fst.resourcemanagement.repository.EnseignantRepository;
import com.university.fst.resourcemanagement.service.ChefDepartementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChefDepartementServiceImpl implements ChefDepartementService {

    private final ChefDepartementRepository chefDepartementRepository;
    private final EnseignantRepository      enseignantRepository;

    public ChefDepartementServiceImpl(
            ChefDepartementRepository chefDepartementRepository,
            EnseignantRepository enseignantRepository) {
        this.chefDepartementRepository = chefDepartementRepository;
        this.enseignantRepository      = enseignantRepository;
    }

    // ──────────────────────────────────────────────────────────────────────────
    // LISTER LES ENSEIGNANTS DU DÉPARTEMENT DU CHEF CONNECTÉ
    // ──────────────────────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<EnseignantResponse> listerEnseignantsDuDepartement(Long chefUserId) {

        // 1. Retrouver l'entrée ChefDepartement via le userId JWT
        ChefDepartement chef = chefDepartementRepository.findByUserId(chefUserId)
                .orElseThrow(() -> new RuntimeException(
                        "Aucun chef de département trouvé pour l'utilisateur id : " + chefUserId));

        Long departementId = chef.getDepartement().getId();

        // 2. Récupérer tous les enseignants du même département
        List<Enseignant> enseignants = enseignantRepository.findByDepartementId(departementId);

        // 3. Mapper vers le DTO (on exclut le chef lui-même de la liste)
        return enseignants.stream()
                .filter(e -> !e.getUser().getId().equals(chefUserId))
                .map(e -> new EnseignantResponse(
                        e.getId(),
                        e.getUser().getId(),
                        e.getUser().getNom(),
                        e.getUser().getPrenom(),
                        e.getUser().getEmail(),
                        e.getUser().getStatus().name(),
                        e.getDepartement().getNom()
                ))
                .collect(Collectors.toList());
    }
}