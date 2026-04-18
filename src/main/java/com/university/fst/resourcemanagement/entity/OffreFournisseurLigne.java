package com.university.fst.resourcemanagement.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "offres_fournisseur_lignes")
public class OffreFournisseurLigne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offre_fournisseur_id", nullable = false)
    private OffreFournisseur offreFournisseur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appel_offre_ligne_id", nullable = false)
    private AppelOffreLigne appelOffreLigne;

    @Column(nullable = false)
    private String marque;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal prixUnitaire;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal prixTotalLigne;

    @Column(nullable = false)
    private Integer dureeGarantieMois;

    @Column(nullable = false)
    private LocalDate dateLivraisonPrevue;

    public OffreFournisseurLigne() {}

    public Long getId() { return id; }

    public OffreFournisseur getOffreFournisseur() { return offreFournisseur; }
    public void setOffreFournisseur(OffreFournisseur offreFournisseur) { this.offreFournisseur = offreFournisseur; }

    public AppelOffreLigne getAppelOffreLigne() { return appelOffreLigne; }
    public void setAppelOffreLigne(AppelOffreLigne appelOffreLigne) { this.appelOffreLigne = appelOffreLigne; }

    public String getMarque() { return marque; }
    public void setMarque(String marque) { this.marque = marque; }

    public BigDecimal getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(BigDecimal prixUnitaire) { this.prixUnitaire = prixUnitaire; }

    public BigDecimal getPrixTotalLigne() { return prixTotalLigne; }
    public void setPrixTotalLigne(BigDecimal prixTotalLigne) { this.prixTotalLigne = prixTotalLigne; }

    public Integer getDureeGarantieMois() { return dureeGarantieMois; }
    public void setDureeGarantieMois(Integer dureeGarantieMois) { this.dureeGarantieMois = dureeGarantieMois; }

    public LocalDate getDateLivraisonPrevue() { return dateLivraisonPrevue; }
    public void setDateLivraisonPrevue(LocalDate dateLivraisonPrevue) { this.dateLivraisonPrevue = dateLivraisonPrevue; }
}