package com.university.fst.resourcemanagement.service;

import com.university.fst.resourcemanagement.dto.DemandeCollecteRequest;
import com.university.fst.resourcemanagement.dto.DemandeCollecteResponse;
import com.university.fst.resourcemanagement.dto.DemandeConcertationRequest;
import com.university.fst.resourcemanagement.dto.TransmissionDemandeResponse;

import java.util.List;

public interface DemandeCollecteService {

    DemandeCollecteResponse creerDemande(Long chefUserId, DemandeCollecteRequest request);

    DemandeCollecteResponse ouvrirDemande(Long chefUserId, Long demandeId);

    /** Ferme la collecte enseignant et fait passer la demande en CONCERTATION. */
    DemandeCollecteResponse fermerDemande(Long chefUserId, Long demandeId);

    /** Valide la demande après réunion de concertation. */
    DemandeCollecteResponse validerDemande(
            Long chefUserId,
            Long demandeId,
            DemandeConcertationRequest request
    );

    /** Transmet la demande validée au responsable avec génération des affectations prévues. */
    TransmissionDemandeResponse transmettreAuResponsable(Long chefUserId, Long demandeId);

    List<DemandeCollecteResponse> listerDemandesChef(Long chefUserId);

    List<DemandeCollecteResponse> listerDemandesOuvertesEnseignant(Long enseignantUserId);
}