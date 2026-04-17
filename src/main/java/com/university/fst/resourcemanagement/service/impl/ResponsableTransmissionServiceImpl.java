package com.university.fst.resourcemanagement.service.impl;

import com.university.fst.resourcemanagement.dto.AffectationPrevueResponse;
import com.university.fst.resourcemanagement.dto.DemandeCollecteResponse;
import com.university.fst.resourcemanagement.entity.AffectationPrevue;
import com.university.fst.resourcemanagement.entity.DemandeCollecte;
import com.university.fst.resourcemanagement.enums.StatutDemande;
import com.university.fst.resourcemanagement.repository.AffectationPrevueRepository;
import com.university.fst.resourcemanagement.repository.DemandeCollecteRepository;
import com.university.fst.resourcemanagement.service.ResponsableTransmissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResponsableTransmissionServiceImpl implements ResponsableTransmissionService {

    private final DemandeCollecteRepository demandeCollecteRepository;
    private final AffectationPrevueRepository affectationPrevueRepository;

    public ResponsableTransmissionServiceImpl(
            DemandeCollecteRepository demandeCollecteRepository,
            AffectationPrevueRepository affectationPrevueRepository
    ) {
        this.demandeCollecteRepository = demandeCollecteRepository;
        this.affectationPrevueRepository = affectationPrevueRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DemandeCollecteResponse> listerDemandesTransmises() {
        return demandeCollecteRepository
                .findByStatutOrderByDateTransmissionResponsableDesc(StatutDemande.TRANSMISE)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AffectationPrevueResponse> listerAffectationsPrevues(Long demandeId) {
        return affectationPrevueRepository.findByDemandeCollecteIdOrderByIdAsc(demandeId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private DemandeCollecteResponse toResponse(DemandeCollecte d) {
        long nb = affectationPrevueRepository.countByDemandeCollecteId(d.getId());

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
                nb
        );
    }

    private AffectationPrevueResponse toResponse(AffectationPrevue a) {
        return new AffectationPrevueResponse(
                a.getId(),
                a.getDemandeCollecte().getId(),
                a.getTypeAffectation(),
                a.getQuantite(),
                a.getDescriptionMateriel(),
                a.getDepartement().getNom(),
                a.getEnseignant() != null ? a.getEnseignant().getId() : null,
                a.getEnseignant() != null ? a.getEnseignant().getUser().getNom() : null,
                a.getEnseignant() != null ? a.getEnseignant().getUser().getPrenom() : null,
                a.getDateCreation()
        );
    }
}