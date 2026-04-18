package com.university.fst.resourcemanagement.dto;

public class RessourcePanneSelectResponse {

    private Long ressourceId;
    private String numeroInventaire;
    private String codeBarres;
    private String typeMateriel;
    private String marque;
    private String departementNom;
    private String typeAffectation;

    public RessourcePanneSelectResponse(
            Long ressourceId,
            String numeroInventaire,
            String codeBarres,
            String typeMateriel,
            String marque,
            String departementNom,
            String typeAffectation
    ) {
        this.ressourceId = ressourceId;
        this.numeroInventaire = numeroInventaire;
        this.codeBarres = codeBarres;
        this.typeMateriel = typeMateriel;
        this.marque = marque;
        this.departementNom = departementNom;
        this.typeAffectation = typeAffectation;
    }

    public Long getRessourceId() { return ressourceId; }
    public String getNumeroInventaire() { return numeroInventaire; }
    public String getCodeBarres() { return codeBarres; }
    public String getTypeMateriel() { return typeMateriel; }
    public String getMarque() { return marque; }
    public String getDepartementNom() { return departementNom; }
    public String getTypeAffectation() { return typeAffectation; }
}