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

    public DemandeCollecteResponse(Long id, String titre, String description,
                                   LocalDateTime dateCreation, LocalDate dateLimite,
                                   StatutDemande statut, Long departementId,
                                   String departementNom, String creeParNom,
                                   String creeParPrenom) {
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
    }

    public Long getId()                    { return id; }
    public String getTitre()               { return titre; }
    public String getDescription()         { return description; }
    public LocalDateTime getDateCreation() { return dateCreation; }
    public LocalDate getDateLimite()       { return dateLimite; }
    public StatutDemande getStatut()       { return statut; }
    public Long getDepartementId()         { return departementId; }
    public String getDepartementNom()      { return departementNom; }
    public String getCreeParNom()          { return creeParNom; }
    public String getCreeParPrenom()       { return creeParPrenom; }
}