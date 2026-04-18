package com.university.fst.resourcemanagement.dto;

import com.university.fst.resourcemanagement.enums.DecisionMaintenance;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DecisionPanneRequest {

    @NotNull(message = "La décision est obligatoire")
    private DecisionMaintenance decision;

    @NotBlank(message = "Le motif est obligatoire")
    private String motif;

    public DecisionPanneRequest() {}

    public DecisionMaintenance getDecision() { return decision; }
    public void setDecision(DecisionMaintenance decision) { this.decision = decision; }

    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }
}