package com.university.fst.resourcemanagement.service.impl;

import com.university.fst.resourcemanagement.dto.BlacklistFournisseurRequest;
import com.university.fst.resourcemanagement.dto.FournisseurAdminResponse;
import com.university.fst.resourcemanagement.entity.Fournisseur;
import com.university.fst.resourcemanagement.repository.FournisseurRepository;
import com.university.fst.resourcemanagement.service.GestionListeNoireFournisseurService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GestionListeNoireFournisseurServiceImpl implements GestionListeNoireFournisseurService {

    private final FournisseurRepository fournisseurRepository;

    public GestionListeNoireFournisseurServiceImpl(FournisseurRepository fournisseurRepository) {
        this.fournisseurRepository = fournisseurRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FournisseurAdminResponse> listerFournisseurs() {
        return fournisseurRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FournisseurAdminResponse blacklisterFournisseur(Long fournisseurId, BlacklistFournisseurRequest request) {
        Fournisseur fournisseur = fournisseurRepository.findById(fournisseurId)
                .orElseThrow(() -> new RuntimeException("Fournisseur introuvable"));

        fournisseur.setBlacklisted(true);
        fournisseur.setBlacklistMotif(request.getMotif());

        return toResponse(fournisseurRepository.save(fournisseur));
    }

    @Override
    @Transactional
    public FournisseurAdminResponse retirerListeNoire(Long fournisseurId) {
        Fournisseur fournisseur = fournisseurRepository.findById(fournisseurId)
                .orElseThrow(() -> new RuntimeException("Fournisseur introuvable"));

        fournisseur.setBlacklisted(false);
        fournisseur.setBlacklistMotif(null);

        return toResponse(fournisseurRepository.save(fournisseur));
    }

    private FournisseurAdminResponse toResponse(Fournisseur f) {
        return new FournisseurAdminResponse(
                f.getId(),
                f.getUser() != null ? f.getUser().getId() : null,
                f.getNomSociete(),
                f.getUser() != null ? f.getUser().getEmail() : null,
                f.isBlacklisted(),
                f.getBlacklistMotif(),
                f.getLieu(),
                f.getAdresse(),
                f.getSiteInternet(),
                f.getGerant()
        );
    }
}