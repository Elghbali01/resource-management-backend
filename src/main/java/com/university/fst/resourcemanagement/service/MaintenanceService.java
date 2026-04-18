package com.university.fst.resourcemanagement.service;

import com.university.fst.resourcemanagement.dto.*;

import java.util.List;

public interface MaintenanceService {

    // Enseignant
    List<RessourcePanneSelectResponse> listerRessourcesPannePourEnseignant(Long enseignantUserId);
    PanneResponse signalerPanne(Long enseignantUserId, SignalerPanneRequest request);
    List<PanneResponse> listerMesPannes(Long enseignantUserId);

    // Technicien
    List<PanneResponse> listerToutesLesPannes();
    PanneResponse commencerIntervention(Long technicienUserId, Long panneId, InterventionPanneRequest request);
    PanneResponse redigerConstat(Long technicienUserId, Long panneId, ConstatPanneRequest request);

    // Responsable
    List<PanneResponse> listerPannesPourResponsable();
    PanneResponse decider(Long panneId, DecisionPanneRequest request);
}