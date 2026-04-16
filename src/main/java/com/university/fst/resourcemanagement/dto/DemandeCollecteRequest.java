package com.university.fst.resourcemanagement.dto;

import com.university.fst.resourcemanagement.enums.StatutDemande;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class DemandeCollecteRequest {

    @NotBlank(message = "Le titre est obligatoire")
    private String titre;

    private String description;

    @NotNull(message = "La date limite est obligatoire")
    @Future(message = "La date limite doit être dans le futur")
    private LocalDate dateLimite;

    @NotNull(message = "Le statut est obligatoire")
    private StatutDemande statut;

    public DemandeCollecteRequest() {}

    public String getTitre()               { return titre; }
    public void setTitre(String titre)     { this.titre = titre; }

    public String getDescription()         { return description; }
    public void setDescription(String d)   { this.description = d; }

    public LocalDate getDateLimite()       { return dateLimite; }
    public void setDateLimite(LocalDate d) { this.dateLimite = d; }

    public StatutDemande getStatut()       { return statut; }
    public void setStatut(StatutDemande s) { this.statut = s; }
}