package com.university.fst.resourcemanagement.dto;

import com.university.fst.resourcemanagement.enums.TypeMateriel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RessourceRequest {

    @NotNull(message = "Le type de matériel est obligatoire")
    private TypeMateriel typeMateriel;

    @NotBlank(message = "La marque est obligatoire")
    private String marque;

    private String caracteristiques;

    public RessourceRequest() {}

    public TypeMateriel getTypeMateriel() { return typeMateriel; }
    public void setTypeMateriel(TypeMateriel typeMateriel) { this.typeMateriel = typeMateriel; }

    public String getMarque() { return marque; }
    public void setMarque(String marque) { this.marque = marque; }

    public String getCaracteristiques() { return caracteristiques; }
    public void setCaracteristiques(String caracteristiques) { this.caracteristiques = caracteristiques; }
}