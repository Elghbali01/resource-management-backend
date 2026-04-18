package com.university.fst.resourcemanagement.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OffreFournisseurLigneResponse {

    private Long id;
    private Long appelOffreLigneId;
    private String descriptionMateriel;
    private Integer quantite;
    private String departementNom;
    private String enseignantNom;
    private String enseignantPrenom;
    private String marque;
    private BigDecimal prixUnitaire;
    private BigDecimal prixTotalLigne;
    private Integer dureeGarantieMois;
    private LocalDate dateLivraisonPrevue;

    public OffreFournisseurLigneResponse(
            Long id,
            Long appelOffreLigneId,
            String descriptionMateriel,
            Integer quantite,
            String departementNom,
            String enseignantNom,
            String enseignantPrenom,
            String marque,
            BigDecimal prixUnitaire,
            BigDecimal prixTotalLigne,
            Integer dureeGarantieMois,
            LocalDate dateLivraisonPrevue
    ) {
        this.id = id;
        this.appelOffreLigneId = appelOffreLigneId;
        this.descriptionMateriel = descriptionMateriel;
        this.quantite = quantite;
        this.departementNom = departementNom;
        this.enseignantNom = enseignantNom;
        this.enseignantPrenom = enseignantPrenom;
        this.marque = marque;
        this.prixUnitaire = prixUnitaire;
        this.prixTotalLigne = prixTotalLigne;
        this.dureeGarantieMois = dureeGarantieMois;
        this.dateLivraisonPrevue = dateLivraisonPrevue;
    }

    public Long getId() { return id; }
    public Long getAppelOffreLigneId() { return appelOffreLigneId; }
    public String getDescriptionMateriel() { return descriptionMateriel; }
    public Integer getQuantite() { return quantite; }
    public String getDepartementNom() { return departementNom; }
    public String getEnseignantNom() { return enseignantNom; }
    public String getEnseignantPrenom() { return enseignantPrenom; }
    public String getMarque() { return marque; }
    public BigDecimal getPrixUnitaire() { return prixUnitaire; }
    public BigDecimal getPrixTotalLigne() { return prixTotalLigne; }
    public Integer getDureeGarantieMois() { return dureeGarantieMois; }
    public LocalDate getDateLivraisonPrevue() { return dateLivraisonPrevue; }
}