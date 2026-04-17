package com.university.fst.resourcemanagement.dto;

import jakarta.validation.constraints.NotBlank;

public class DemandeConcertationRequest {

    @NotBlank(message = "Le compte rendu de concertation est obligatoire")
    private String compteRenduConcertation;

    public DemandeConcertationRequest() {}

    public String getCompteRenduConcertation() {
        return compteRenduConcertation;
    }

    public void setCompteRenduConcertation(String compteRenduConcertation) {
        this.compteRenduConcertation = compteRenduConcertation;
    }
}