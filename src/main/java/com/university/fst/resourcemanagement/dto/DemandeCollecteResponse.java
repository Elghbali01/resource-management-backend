package com.university.fst.resourcemanagement.dto;

import com.university.fst.resourcemanagement.enums.StatutDemande;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DemandeCollecteResponse {

    private Long id;
    private String titre;
    private String description;
    private LocalDateTime dateCreation;
    private LocalDate dateLimite;
    private StatutDemande statut;
    private Long departementId;
    private String departementNom;
    private String creeParNom;
    private String creeParPrenom;

    private LocalDateTime dateDebutConcertation;
    private LocalDateTime dateFinConcertation;
    private String compteRenduConcertation;
    private LocalDateTime dateValidationChef;
    private LocalDateTime dateTransmissionResponsable;
    private long nombreAffectationsPrevues;

    public DemandeCollecteResponse(
            Long id,
            String titre,
            String description,
            LocalDateTime dateCreation,
            LocalDate dateLimite,
            StatutDemande statut,
            Long departementId,
            String departementNom,
            String creeParNom,
            String creeParPrenom,
            LocalDateTime dateDebutConcertation,
            LocalDateTime dateFinConcertation,
            String compteRenduConcertation,
            LocalDateTime dateValidationChef,
            LocalDateTime dateTransmissionResponsable,
            long nombreAffectationsPrevues
    ) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.dateCreation = dateCreation;
        this.dateLimite = dateLimite;
        this.statut = statut;
        this.departementId = departementId;
        this.departementNom = departementNom;
        this.creeParNom = creeParNom;
        this.creeParPrenom = creeParPrenom;
        this.dateDebutConcertation = dateDebutConcertation;
        this.dateFinConcertation = dateFinConcertation;
        this.compteRenduConcertation = compteRenduConcertation;
        this.dateValidationChef = dateValidationChef;
        this.dateTransmissionResponsable = dateTransmissionResponsable;
        this.nombreAffectationsPrevues = nombreAffectationsPrevues;
    }

    public Long getId() { return id; }
    public String getTitre() { return titre; }
    public String getDescription() { return description; }
    public LocalDateTime getDateCreation() { return dateCreation; }
    public LocalDate getDateLimite() { return dateLimite; }
    public StatutDemande getStatut() { return statut; }
    public Long getDepartementId() { return departementId; }
    public String getDepartementNom() { return departementNom; }
    public String getCreeParNom() { return creeParNom; }
    public String getCreeParPrenom() { return creeParPrenom; }

    public LocalDateTime getDateDebutConcertation() { return dateDebutConcertation; }
    public LocalDateTime getDateFinConcertation() { return dateFinConcertation; }
    public String getCompteRenduConcertation() { return compteRenduConcertation; }
    public LocalDateTime getDateValidationChef() { return dateValidationChef; }
    public LocalDateTime getDateTransmissionResponsable() { return dateTransmissionResponsable; }
    public long getNombreAffectationsPrevues() { return nombreAffectationsPrevues; }
}