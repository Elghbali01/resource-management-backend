package com.university.fst.resourcemanagement.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CreateOffreFournisseurRequest {

    @NotNull(message = "L'identifiant de l'appel d'offre est obligatoire")
    private Long appelOffreId;

    @NotEmpty(message = "Au moins une ligne d'offre est obligatoire")
    @Valid
    private List<CreateOffreLigneRequest> lignes;

    public CreateOffreFournisseurRequest() {}

    public Long getAppelOffreId() { return appelOffreId; }
    public void setAppelOffreId(Long appelOffreId) { this.appelOffreId = appelOffreId; }

    public List<CreateOffreLigneRequest> getLignes() { return lignes; }
    public void setLignes(List<CreateOffreLigneRequest> lignes) { this.lignes = lignes; }
}