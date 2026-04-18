package com.university.fst.resourcemanagement.service;

import com.university.fst.resourcemanagement.dto.CreateOffreFournisseurRequest;
import com.university.fst.resourcemanagement.dto.DecisionOffreRequest;
import com.university.fst.resourcemanagement.dto.OffreFournisseurResponse;

import java.util.List;

public interface OffreFournisseurService {

    // Fournisseur
    OffreFournisseurResponse soumettreOffre(Long fournisseurUserId, CreateOffreFournisseurRequest request);
    List<OffreFournisseurResponse> listerMesOffres(Long fournisseurUserId);

    // Responsable
    List<OffreFournisseurResponse> listerOffresAppelOffre(Long appelOffreId);
    OffreFournisseurResponse eliminerOffre(Long offreId, DecisionOffreRequest request);
    OffreFournisseurResponse accepterOffre(Long offreId, DecisionOffreRequest request);
}