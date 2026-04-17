package com.university.fst.resourcemanagement.dto;

import com.university.fst.resourcemanagement.enums.StatutDemande;

import java.time.LocalDateTime;

public class TransmissionDemandeResponse {

    private Long demandeId;
    private StatutDemande statut;
    private LocalDateTime dateTransmission;
    private long nombreAffectationsPrevues;
    private String message;

    public TransmissionDemandeResponse(
            Long demandeId,
            StatutDemande statut,
            LocalDateTime dateTransmission,
            long nombreAffectationsPrevues,
            String message
    ) {
        this.demandeId = demandeId;
        this.statut = statut;
        this.dateTransmission = dateTransmission;
        this.nombreAffectationsPrevues = nombreAffectationsPrevues;
        this.message = message;
    }

    public Long getDemandeId() { return demandeId; }
    public StatutDemande getStatut() { return statut; }
    public LocalDateTime getDateTransmission() { return dateTransmission; }
    public long getNombreAffectationsPrevues() { return nombreAffectationsPrevues; }
    public String getMessage() { return message; }
}