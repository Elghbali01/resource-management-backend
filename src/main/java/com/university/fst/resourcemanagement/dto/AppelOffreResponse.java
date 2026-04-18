package com.university.fst.resourcemanagement.dto;

import com.university.fst.resourcemanagement.enums.StatutAppelOffre;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AppelOffreResponse {

    private Long id;
    private String titre;
    private String description;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private StatutAppelOffre statut;
    private LocalDateTime dateCreation;
    private String creeParNom;
    private String creeParPrenom;
    private long nombreLignes;
    private long nombreDemandes;
    private List<AppelOffreLigneResponse> lignes;

    public AppelOffreResponse(
            Long id,
            String titre,
            String description,
            LocalDate dateDebut,
            LocalDate dateFin,
            StatutAppelOffre statut,
            LocalDateTime dateCreation,
            String creeParNom,
            String creeParPrenom,
            long nombreLignes,
            long nombreDemandes,
            List<AppelOffreLigneResponse> lignes
    ) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.creeParNom = creeParNom;
        this.creeParPrenom = creeParPrenom;
        this.nombreLignes = nombreLignes;
        this.nombreDemandes = nombreDemandes;
        this.lignes = lignes;
    }

    public Long getId() { return id; }
    public String getTitre() { return titre; }
    public String getDescription() { return description; }
    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public StatutAppelOffre getStatut() { return statut; }
    public LocalDateTime getDateCreation() { return dateCreation; }
    public String getCreeParNom() { return creeParNom; }
    public String getCreeParPrenom() { return creeParPrenom; }
    public long getNombreLignes() { return nombreLignes; }
    public long getNombreDemandes() { return nombreDemandes; }
    public List<AppelOffreLigneResponse> getLignes() { return lignes; }
}