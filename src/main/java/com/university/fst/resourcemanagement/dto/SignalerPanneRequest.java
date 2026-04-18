package com.university.fst.resourcemanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SignalerPanneRequest {

    @NotNull(message = "La ressource est obligatoire")
    private Long ressourceId;

    @NotBlank(message = "La description du signalement est obligatoire")
    private String descriptionSignalement;

    public SignalerPanneRequest() {}

    public Long getRessourceId() { return ressourceId; }
    public void setRessourceId(Long ressourceId) { this.ressourceId = ressourceId; }

    public String getDescriptionSignalement() { return descriptionSignalement; }
    public void setDescriptionSignalement(String descriptionSignalement) { this.descriptionSignalement = descriptionSignalement; }
}