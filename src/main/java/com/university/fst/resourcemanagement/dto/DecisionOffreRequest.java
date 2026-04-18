package com.university.fst.resourcemanagement.dto;

import jakarta.validation.constraints.NotBlank;

public class DecisionOffreRequest {

    @NotBlank(message = "Le motif est obligatoire")
    private String motif;

    public DecisionOffreRequest() {}

    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }
}