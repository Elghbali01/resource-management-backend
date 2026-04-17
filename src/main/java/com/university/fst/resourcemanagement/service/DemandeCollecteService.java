package com.university.fst.resourcemanagement.service;

import com.university.fst.resourcemanagement.dto.DemandeCollecteRequest;
import com.university.fst.resourcemanagement.dto.DemandeCollecteResponse;

import java.util.List;

public interface DemandeCollecteService {

    /** Crée une demande en BROUILLON pour le département du chef connecté. */
    DemandeCollecteResponse creerDemande(Long chefUserId, DemandeCollecteRequest request);

    /** Ouvre une demande BROUILLON du département du chef connecté. */
    DemandeCollecteResponse ouvrirDemande(Long chefUserId, Long demandeId);

    /** Ferme une demande OUVERTE du département du chef connecté. */
    DemandeCollecteResponse fermerDemande(Long chefUserId, Long demandeId);

    /** Liste toutes les demandes du département du chef connecté. */
    List<DemandeCollecteResponse> listerDemandesChef(Long chefUserId);

    /** Liste les demandes OUVERTES du département de l'enseignant connecté. */
    List<DemandeCollecteResponse> listerDemandesOuvertesEnseignant(Long enseignantUserId);
}