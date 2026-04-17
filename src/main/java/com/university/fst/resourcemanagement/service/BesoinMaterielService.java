package com.university.fst.resourcemanagement.service;

import com.university.fst.resourcemanagement.dto.BesoinRequest;
import com.university.fst.resourcemanagement.dto.BesoinResponse;

import java.util.List;

public interface BesoinMaterielService {

    // Enseignant
    BesoinResponse soumettreBesoin(Long enseignantUserId, BesoinRequest request);
    BesoinResponse modifierBesoin(Long enseignantUserId, Long besoinId, BesoinRequest request);
    void supprimerBesoin(Long enseignantUserId, Long besoinId);
    List<BesoinResponse> listerMesBesoins(Long enseignantUserId, Long demandeId);

    // Chef
    List<BesoinResponse> listerBesoinsPourChef(Long chefUserId, Long demandeId);

    /** Ajouter un besoin collectif du département pendant la concertation. */
    BesoinResponse ajouterBesoinCollectif(Long chefUserId, BesoinRequest request);

    /** Modifier n'importe quel besoin de la demande pendant la concertation. */
    BesoinResponse modifierBesoinParChef(Long chefUserId, Long besoinId, BesoinRequest request);

    /** Supprimer n'importe quel besoin de la demande pendant la concertation. */
    void supprimerBesoinParChef(Long chefUserId, Long besoinId);

    /** Lister uniquement les besoins collectifs du département. */
    List<BesoinResponse> listerBesoinsCollectifs(Long chefUserId, Long demandeId);
}