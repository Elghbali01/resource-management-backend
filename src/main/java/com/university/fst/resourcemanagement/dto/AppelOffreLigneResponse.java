package com.university.fst.resourcemanagement.dto;

import com.university.fst.resourcemanagement.enums.TypeAffectationPrevue;

public class AppelOffreLigneResponse {

    private Long id;
    private Long demandeId;
    private TypeAffectationPrevue typeAffectation;
    private Integer quantite;
    private String descriptionMateriel;
    private String departementNom;
    private String enseignantNom;
    private String enseignantPrenom;

    public AppelOffreLigneResponse(
            Long id,
            Long demandeId,
            TypeAffectationPrevue typeAffectation,
            Integer quantite,
            String descriptionMateriel,
            String departementNom,
            String enseignantNom,
            String enseignantPrenom
    ) {
        this.id = id;
        this.demandeId = demandeId;
        this.typeAffectation = typeAffectation;
        this.quantite = quantite;
        this.descriptionMateriel = descriptionMateriel;
        this.departementNom = departementNom;
        this.enseignantNom = enseignantNom;
        this.enseignantPrenom = enseignantPrenom;
    }

    public Long getId() { return id; }
    public Long getDemandeId() { return demandeId; }
    public TypeAffectationPrevue getTypeAffectation() { return typeAffectation; }
    public Integer getQuantite() { return quantite; }
    public String getDescriptionMateriel() { return descriptionMateriel; }
    public String getDepartementNom() { return departementNom; }
    public String getEnseignantNom() { return enseignantNom; }
    public String getEnseignantPrenom() { return enseignantPrenom; }
}