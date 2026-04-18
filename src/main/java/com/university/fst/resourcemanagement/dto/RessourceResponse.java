package com.university.fst.resourcemanagement.dto;

import com.university.fst.resourcemanagement.enums.StatutRessource;
import com.university.fst.resourcemanagement.enums.TypeMateriel;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RessourceResponse {

    private Long id;
    private String numeroInventaire;
    private String codeBarres;
    private TypeMateriel typeMateriel;
    private String marque;
    private String caracteristiques;
    private LocalDate dateLivraison;
    private StatutRessource statut;
    private Long fournisseurId;
    private String nomSociete;
    private Long offreId;
    private LocalDateTime dateCreation;

    public RessourceResponse(
            Long id,
            String numeroInventaire,
            String codeBarres,
            TypeMateriel typeMateriel,
            String marque,
            String caracteristiques,
            LocalDate dateLivraison,
            StatutRessource statut,
            Long fournisseurId,
            String nomSociete,
            Long offreId,
            LocalDateTime dateCreation
    ) {
        this.id = id;
        this.numeroInventaire = numeroInventaire;
        this.codeBarres = codeBarres;
        this.typeMateriel = typeMateriel;
        this.marque = marque;
        this.caracteristiques = caracteristiques;
        this.dateLivraison = dateLivraison;
        this.statut = statut;
        this.fournisseurId = fournisseurId;
        this.nomSociete = nomSociete;
        this.offreId = offreId;
        this.dateCreation = dateCreation;
    }

    public Long getId() { return id; }
    public String getNumeroInventaire() { return numeroInventaire; }
    public String getCodeBarres() { return codeBarres; }
    public TypeMateriel getTypeMateriel() { return typeMateriel; }
    public String getMarque() { return marque; }
    public String getCaracteristiques() { return caracteristiques; }
    public LocalDate getDateLivraison() { return dateLivraison; }
    public StatutRessource getStatut() { return statut; }
    public Long getFournisseurId() { return fournisseurId; }
    public String getNomSociete() { return nomSociete; }
    public Long getOffreId() { return offreId; }
    public LocalDateTime getDateCreation() { return dateCreation; }
}