package com.university.fst.resourcemanagement.dto;

import com.university.fst.resourcemanagement.enums.TypeAffectationPrevue;

import java.time.LocalDateTime;

public class AffectationPrevueResponse {

    private Long id;
    private Long demandeId;
    private TypeAffectationPrevue typeAffectation;
    private Integer quantite;
    private String descriptionMateriel;
    private String departementNom;
    private Long enseignantId;
    private String enseignantNom;
    private String enseignantPrenom;
    private LocalDateTime dateCreation;

    public AffectationPrevueResponse(
            Long id,
            Long demandeId,
            TypeAffectationPrevue typeAffectation,
            Integer quantite,
            String descriptionMateriel,
            String departementNom,
            Long enseignantId,
            String enseignantNom,
            String enseignantPrenom,
            LocalDateTime dateCreation
    ) {
        this.id = id;
        this.demandeId = demandeId;
        this.typeAffectation = typeAffectation;
        this.quantite = quantite;
        this.descriptionMateriel = descriptionMateriel;
        this.departementNom = departementNom;
        this.enseignantId = enseignantId;
        this.enseignantNom = enseignantNom;
        this.enseignantPrenom = enseignantPrenom;
        this.dateCreation = dateCreation;
    }

    public Long getId() { return id; }
    public Long getDemandeId() { return demandeId; }
    public TypeAffectationPrevue getTypeAffectation() { return typeAffectation; }
    public Integer getQuantite() { return quantite; }
    public String getDescriptionMateriel() { return descriptionMateriel; }
    public String getDepartementNom() { return departementNom; }
    public Long getEnseignantId() { return enseignantId; }
    public String getEnseignantNom() { return enseignantNom; }
    public String getEnseignantPrenom() { return enseignantPrenom; }
    public LocalDateTime getDateCreation() { return dateCreation; }
}