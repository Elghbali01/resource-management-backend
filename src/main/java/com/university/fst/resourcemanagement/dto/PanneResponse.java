package com.university.fst.resourcemanagement.dto;

import com.university.fst.resourcemanagement.enums.DecisionMaintenance;
import com.university.fst.resourcemanagement.enums.FrequencePanne;
import com.university.fst.resourcemanagement.enums.OrdrePanne;
import com.university.fst.resourcemanagement.enums.StatutPanne;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PanneResponse {

    private Long id;
    private Long ressourceId;
    private String numeroInventaire;
    private String codeBarres;
    private String typeMateriel;
    private String marque;
    private String descriptionSignalement;
    private String enseignantNom;
    private String enseignantPrenom;
    private String departementNom;
    private StatutPanne statut;
    private LocalDateTime dateSignalement;
    private LocalDateTime dateDebutIntervention;
    private String commentaireIntervention;
    private Boolean severe;
    private String explicationPanne;
    private LocalDate dateApparition;
    private FrequencePanne frequence;
    private OrdrePanne ordrePanne;
    private LocalDateTime dateConstat;
    private String technicienNom;
    private String technicienPrenom;
    private DecisionMaintenance decisionResponsable;
    private String motifDecisionResponsable;
    private LocalDateTime dateDecisionResponsable;
    private Boolean garantieValide;

    public PanneResponse(
            Long id,
            Long ressourceId,
            String numeroInventaire,
            String codeBarres,
            String typeMateriel,
            String marque,
            String descriptionSignalement,
            String enseignantNom,
            String enseignantPrenom,
            String departementNom,
            StatutPanne statut,
            LocalDateTime dateSignalement,
            LocalDateTime dateDebutIntervention,
            String commentaireIntervention,
            Boolean severe,
            String explicationPanne,
            LocalDate dateApparition,
            FrequencePanne frequence,
            OrdrePanne ordrePanne,
            LocalDateTime dateConstat,
            String technicienNom,
            String technicienPrenom,
            DecisionMaintenance decisionResponsable,
            String motifDecisionResponsable,
            LocalDateTime dateDecisionResponsable,
            Boolean garantieValide
    ) {
        this.id = id;
        this.ressourceId = ressourceId;
        this.numeroInventaire = numeroInventaire;
        this.codeBarres = codeBarres;
        this.typeMateriel = typeMateriel;
        this.marque = marque;
        this.descriptionSignalement = descriptionSignalement;
        this.enseignantNom = enseignantNom;
        this.enseignantPrenom = enseignantPrenom;
        this.departementNom = departementNom;
        this.statut = statut;
        this.dateSignalement = dateSignalement;
        this.dateDebutIntervention = dateDebutIntervention;
        this.commentaireIntervention = commentaireIntervention;
        this.severe = severe;
        this.explicationPanne = explicationPanne;
        this.dateApparition = dateApparition;
        this.frequence = frequence;
        this.ordrePanne = ordrePanne;
        this.dateConstat = dateConstat;
        this.technicienNom = technicienNom;
        this.technicienPrenom = technicienPrenom;
        this.decisionResponsable = decisionResponsable;
        this.motifDecisionResponsable = motifDecisionResponsable;
        this.dateDecisionResponsable = dateDecisionResponsable;
        this.garantieValide = garantieValide;
    }

    public Long getId() { return id; }
    public Long getRessourceId() { return ressourceId; }
    public String getNumeroInventaire() { return numeroInventaire; }
    public String getCodeBarres() { return codeBarres; }
    public String getTypeMateriel() { return typeMateriel; }
    public String getMarque() { return marque; }
    public String getDescriptionSignalement() { return descriptionSignalement; }
    public String getEnseignantNom() { return enseignantNom; }
    public String getEnseignantPrenom() { return enseignantPrenom; }
    public String getDepartementNom() { return departementNom; }
    public StatutPanne getStatut() { return statut; }
    public LocalDateTime getDateSignalement() { return dateSignalement; }
    public LocalDateTime getDateDebutIntervention() { return dateDebutIntervention; }
    public String getCommentaireIntervention() { return commentaireIntervention; }
    public Boolean getSevere() { return severe; }
    public String getExplicationPanne() { return explicationPanne; }
    public LocalDate getDateApparition() { return dateApparition; }
    public FrequencePanne getFrequence() { return frequence; }
    public OrdrePanne getOrdrePanne() { return ordrePanne; }
    public LocalDateTime getDateConstat() { return dateConstat; }
    public String getTechnicienNom() { return technicienNom; }
    public String getTechnicienPrenom() { return technicienPrenom; }
    public DecisionMaintenance getDecisionResponsable() { return decisionResponsable; }
    public String getMotifDecisionResponsable() { return motifDecisionResponsable; }
    public LocalDateTime getDateDecisionResponsable() { return dateDecisionResponsable; }
    public Boolean getGarantieValide() { return garantieValide; }
}