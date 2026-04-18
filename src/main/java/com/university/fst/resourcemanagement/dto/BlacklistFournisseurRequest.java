package com.university.fst.resourcemanagement.dto;

import jakarta.validation.constraints.NotBlank;

public class BlacklistFournisseurRequest {

    @NotBlank(message = "Le motif est obligatoire")
    private String motif;

    public BlacklistFournisseurRequest() {}

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }
}