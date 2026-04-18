package com.university.fst.resourcemanagement.service;

import com.university.fst.resourcemanagement.dto.*;

import java.util.List;

public interface ResponsableRessourceService {

    ReceptionLivraisonResponse receptionnerLivraison(ReceptionLivraisonRequest request);

    List<RessourceResponse> listerRessources();

    RessourceResponse getRessource(Long ressourceId);

    RessourceResponse modifierRessource(Long ressourceId, RessourceRequest request);

    void supprimerRessource(Long ressourceId);

    AffectationRessourceResponse affecterRessource(AffectationRessourceRequest request);

    List<AffectationRessourceResponse> listerAffectations();

    AffectationRessourceResponse modifierAffectation(Long affectationId, AffectationRessourceRequest request);

    void supprimerAffectation(Long affectationId);
}