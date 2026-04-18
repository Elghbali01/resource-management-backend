package com.university.fst.resourcemanagement.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ReceptionLivraisonRequest {

    @NotNull(message = "L'identifiant de l'offre acceptée est obligatoire")
    private Long offreId;

    @NotNull(message = "La date de livraison est obligatoire")
    private LocalDate dateLivraison;

    private String lieu;
    private String adresse;
    private String siteInternet;
    private String gerant;

    public ReceptionLivraisonRequest() {}

    public Long getOffreId() { return offreId; }
    public void setOffreId(Long offreId) { this.offreId = offreId; }

    public LocalDate getDateLivraison() { return dateLivraison; }
    public void setDateLivraison(LocalDate dateLivraison) { this.dateLivraison = dateLivraison; }

    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getSiteInternet() { return siteInternet; }
    public void setSiteInternet(String siteInternet) { this.siteInternet = siteInternet; }

    public String getGerant() { return gerant; }
    public void setGerant(String gerant) { this.gerant = gerant; }
}