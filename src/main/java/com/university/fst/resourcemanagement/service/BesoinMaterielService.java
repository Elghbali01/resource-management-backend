package com.university.fst.resourcemanagement.service;

import com.university.fst.resourcemanagement.dto.BesoinRequest;
import com.university.fst.resourcemanagement.dto.BesoinResponse;

import java.util.List;

public interface BesoinMaterielService {

    /** Soumission d'un besoin par un enseignant connecté. */
    BesoinResponse soumettreBesoin(Long enseignantUserId, BesoinRequest request);

    /** Modifier un besoin déjà soumis par l'enseignant connecté. */
    BesoinResponse modifierBesoin(Long enseignantUserId, Long besoinId, BesoinRequest request);

    /** Supprimer un besoin déjà soumis par l'enseignant connecté. */
    void supprimerBesoin(Long enseignantUserId, Long besoinId);

    /** Lister les besoins de l'enseignant connecté pour une demande donnée. */
    List<BesoinResponse> listerMesBesoins(Long enseignantUserId, Long demandeId);

    /** Lister tous les besoins d'une demande pour le chef du département concerné. */
    List<BesoinResponse> listerBesoinsPourChef(Long chefUserId, Long demandeId);
}