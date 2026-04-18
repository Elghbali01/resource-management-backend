package com.university.fst.resourcemanagement.dto;

import com.university.fst.resourcemanagement.enums.TypeBeneficiaireAffectation;

import java.time.LocalDateTime;

public class AffectationRessourceResponse {

    private Long id;
    private Long ressourceId;
    private String numeroInventaire;
    private String codeBarres;
    private String departementNom;
    private Long enseignantId;
    private String enseignantNom;
    private String enseignantPrenom;
    private TypeBeneficiaireAffectation typeBeneficiaire;
    private LocalDateTime dateAffectation;

    public AffectationRessourceResponse(
            Long id,
            Long ressourceId,
            String numeroInventaire,
            String codeBarres,
            String departementNom,
            Long enseignantId,
            String enseignantNom,
            String enseignantPrenom,
            TypeBeneficiaireAffectation typeBeneficiaire,
            LocalDateTime dateAffectation
    ) {
        this.id = id;
        this.ressourceId = ressourceId;
        this.numeroInventaire = numeroInventaire;
        this.codeBarres = codeBarres;
        this.departementNom = departementNom;
        this.enseignantId = enseignantId;
        this.enseignantNom = enseignantNom;
        this.enseignantPrenom = enseignantPrenom;
        this.typeBeneficiaire = typeBeneficiaire;
        this.dateAffectation = dateAffectation;
    }

    public Long getId() { return id; }
    public Long getRessourceId() { return ressourceId; }
    public String getNumeroInventaire() { return numeroInventaire; }
    public String getCodeBarres() { return codeBarres; }
    public String getDepartementNom() { return departementNom; }
    public Long getEnseignantId() { return enseignantId; }
    public String getEnseignantNom() { return enseignantNom; }
    public String getEnseignantPrenom() { return enseignantPrenom; }
    public TypeBeneficiaireAffectation getTypeBeneficiaire() { return typeBeneficiaire; }
    public LocalDateTime getDateAffectation() { return dateAffectation; }
}