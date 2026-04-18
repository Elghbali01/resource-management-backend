package com.university.fst.resourcemanagement.dto;

import jakarta.validation.constraints.NotNull;

public class AffectationRessourceRequest {

    @NotNull(message = "L'identifiant de la ressource est obligatoire")
    private Long ressourceId;

    @NotNull(message = "L'identifiant du département est obligatoire")
    private Long departementId;

    private Long enseignantId;

    public AffectationRessourceRequest() {}

    public Long getRessourceId() { return ressourceId; }
    public void setRessourceId(Long ressourceId) { this.ressourceId = ressourceId; }

    public Long getDepartementId() { return departementId; }
    public void setDepartementId(Long departementId) { this.departementId = departementId; }

    public Long getEnseignantId() { return enseignantId; }
    public void setEnseignantId(Long enseignantId) { this.enseignantId = enseignantId; }
}