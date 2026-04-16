package com.university.fst.resourcemanagement.service;

import com.university.fst.resourcemanagement.dto.DemandeCollecteRequest;
import com.university.fst.resourcemanagement.dto.DemandeCollecteResponse;

import java.util.List;

public interface DemandeCollecteService {

    /** Crée une demande pour le département du chef connecté. */
    DemandeCollecteResponse creerDemande(Long chefUserId, DemandeCollecteRequest request);

    /** Liste toutes les demandes du département du chef connecté. */
    List<DemandeCollecteResponse> listerDemandesChef(Long chefUserId);

    /** Liste les demandes OUVERTES du département de l'enseignant connecté. */
    List<DemandeCollecteResponse> listerDemandesOuvertesEnseignant(Long enseignantUserId);
}