package com.university.fst.resourcemanagement.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateOffreLigneRequest {

    @NotNull(message = "L'identifiant de la ligne d'appel d'offre est obligatoire")
    private Long appelOffreLigneId;

    @NotBlank(message = "La marque est obligatoire")
    private String marque;

    @NotNull(message = "Le prix unitaire est obligatoire")
    @DecimalMin(value = "0.01", message = "Le prix unitaire doit être supérieur à 0")
    private BigDecimal prixUnitaire;

    @NotNull(message = "La durée de garantie est obligatoire")
    @Min(value = 1, message = "La durée de garantie doit être au moins 1 mois")
    private Integer dureeGarantieMois;

    @NotNull(message = "La date de livraison prévue est obligatoire")
    @Future(message = "La date de livraison prévue doit être dans le futur")
    private LocalDate dateLivraisonPrevue;

    public CreateOffreLigneRequest() {}

    public Long getAppelOffreLigneId() { return appelOffreLigneId; }
    public void setAppelOffreLigneId(Long appelOffreLigneId) { this.appelOffreLigneId = appelOffreLigneId; }

    public String getMarque() { return marque; }
    public void setMarque(String marque) { this.marque = marque; }

    public BigDecimal getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(BigDecimal prixUnitaire) { this.prixUnitaire = prixUnitaire; }

    public Integer getDureeGarantieMois() { return dureeGarantieMois; }
    public void setDureeGarantieMois(Integer dureeGarantieMois) { this.dureeGarantieMois = dureeGarantieMois; }

    public LocalDate getDateLivraisonPrevue() { return dateLivraisonPrevue; }
    public void setDateLivraisonPrevue(LocalDate dateLivraisonPrevue) { this.dateLivraisonPrevue = dateLivraisonPrevue; }
}