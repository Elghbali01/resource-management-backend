package com.university.fst.resourcemanagement.entity;

import com.university.fst.resourcemanagement.enums.DecisionMaintenance;
import com.university.fst.resourcemanagement.enums.FrequencePanne;
import com.university.fst.resourcemanagement.enums.OrdrePanne;
import com.university.fst.resourcemanagement.enums.StatutPanne;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "pannes")
public class Panne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ressource_id", nullable = false)
    private RessourceMaterielle ressource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enseignant_id", nullable = false)
    private Enseignant enseignant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technicien_id")
    private Technicien technicien;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutPanne statut = StatutPanne.SIGNALEE;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descriptionSignalement;

    @Column(nullable = false)
    private LocalDateTime dateSignalement = LocalDateTime.now();

    private LocalDateTime dateDebutIntervention;
    private String commentaireIntervention;

    // Constat technicien
    @Column(columnDefinition = "TEXT")
    private String explicationPanne;

    private LocalDate dateApparition;

    @Enumerated(EnumType.STRING)
    private FrequencePanne frequence;

    @Enumerated(EnumType.STRING)
    private OrdrePanne ordrePanne;

    @Column(nullable = false)
    private boolean severe = false;

    private LocalDateTime dateConstat;

    // Décision responsable
    @Enumerated(EnumType.STRING)
    private DecisionMaintenance decisionResponsable;

    @Column(columnDefinition = "TEXT")
    private String motifDecisionResponsable;

    private LocalDateTime dateDecisionResponsable;

    public Panne() {}

    public Long getId() { return id; }

    public RessourceMaterielle getRessource() { return ressource; }
    public void setRessource(RessourceMaterielle ressource) { this.ressource = ressource; }

    public Enseignant getEnseignant() { return enseignant; }
    public void setEnseignant(Enseignant enseignant) { this.enseignant = enseignant; }

    public Technicien getTechnicien() { return technicien; }
    public void setTechnicien(Technicien technicien) { this.technicien = technicien; }

    public StatutPanne getStatut() { return statut; }
    public void setStatut(StatutPanne statut) { this.statut = statut; }

    public String getDescriptionSignalement() { return descriptionSignalement; }
    public void setDescriptionSignalement(String descriptionSignalement) { this.descriptionSignalement = descriptionSignalement; }

    public LocalDateTime getDateSignalement() { return dateSignalement; }
    public void setDateSignalement(LocalDateTime dateSignalement) { this.dateSignalement = dateSignalement; }

    public LocalDateTime getDateDebutIntervention() { return dateDebutIntervention; }
    public void setDateDebutIntervention(LocalDateTime dateDebutIntervention) { this.dateDebutIntervention = dateDebutIntervention; }

    public String getCommentaireIntervention() { return commentaireIntervention; }
    public void setCommentaireIntervention(String commentaireIntervention) { this.commentaireIntervention = commentaireIntervention; }

    public String getExplicationPanne() { return explicationPanne; }
    public void setExplicationPanne(String explicationPanne) { this.explicationPanne = explicationPanne; }

    public LocalDate getDateApparition() { return dateApparition; }
    public void setDateApparition(LocalDate dateApparition) { this.dateApparition = dateApparition; }

    public FrequencePanne getFrequence() { return frequence; }
    public void setFrequence(FrequencePanne frequence) { this.frequence = frequence; }

    public OrdrePanne getOrdrePanne() { return ordrePanne; }
    public void setOrdrePanne(OrdrePanne ordrePanne) { this.ordrePanne = ordrePanne; }

    public boolean isSevere() { return severe; }
    public void setSevere(boolean severe) { this.severe = severe; }

    public LocalDateTime getDateConstat() { return dateConstat; }
    public void setDateConstat(LocalDateTime dateConstat) { this.dateConstat = dateConstat; }

    public DecisionMaintenance getDecisionResponsable() { return decisionResponsable; }
    public void setDecisionResponsable(DecisionMaintenance decisionResponsable) { this.decisionResponsable = decisionResponsable; }

    public String getMotifDecisionResponsable() { return motifDecisionResponsable; }
    public void setMotifDecisionResponsable(String motifDecisionResponsable) { this.motifDecisionResponsable = motifDecisionResponsable; }

    public LocalDateTime getDateDecisionResponsable() { return dateDecisionResponsable; }
    public void setDateDecisionResponsable(LocalDateTime dateDecisionResponsable) { this.dateDecisionResponsable = dateDecisionResponsable; }
}