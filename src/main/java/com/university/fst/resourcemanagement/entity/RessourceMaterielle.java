package com.university.fst.resourcemanagement.entity;

import com.university.fst.resourcemanagement.enums.StatutRessource;
import com.university.fst.resourcemanagement.enums.TypeMateriel;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ressources_materielles")
public class RessourceMaterielle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numeroInventaire;

    @Column(nullable = false, unique = true)
    private String codeBarres;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeMateriel typeMateriel;

    @Column(nullable = false)
    private String marque;

    @Column(columnDefinition = "TEXT")
    private String caracteristiques;

    @Column(nullable = false)
    private LocalDate dateLivraison;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutRessource statut = StatutRessource.DISPONIBLE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fournisseur_id", nullable = false)
    private Fournisseur fournisseur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offre_fournisseur_id", nullable = false)
    private OffreFournisseur offreFournisseur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offre_fournisseur_ligne_id", nullable = false)
    private OffreFournisseurLigne offreFournisseurLigne;

    @Column(nullable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();

    public RessourceMaterielle() {}

    public Long getId() { return id; }

    public String getNumeroInventaire() { return numeroInventaire; }
    public void setNumeroInventaire(String numeroInventaire) { this.numeroInventaire = numeroInventaire; }

    public String getCodeBarres() { return codeBarres; }
    public void setCodeBarres(String codeBarres) { this.codeBarres = codeBarres; }

    public TypeMateriel getTypeMateriel() { return typeMateriel; }
    public void setTypeMateriel(TypeMateriel typeMateriel) { this.typeMateriel = typeMateriel; }

    public String getMarque() { return marque; }
    public void setMarque(String marque) { this.marque = marque; }

    public String getCaracteristiques() { return caracteristiques; }
    public void setCaracteristiques(String caracteristiques) { this.caracteristiques = caracteristiques; }

    public LocalDate getDateLivraison() { return dateLivraison; }
    public void setDateLivraison(LocalDate dateLivraison) { this.dateLivraison = dateLivraison; }

    public StatutRessource getStatut() { return statut; }
    public void setStatut(StatutRessource statut) { this.statut = statut; }

    public Fournisseur getFournisseur() { return fournisseur; }
    public void setFournisseur(Fournisseur fournisseur) { this.fournisseur = fournisseur; }

    public OffreFournisseur getOffreFournisseur() { return offreFournisseur; }
    public void setOffreFournisseur(OffreFournisseur offreFournisseur) { this.offreFournisseur = offreFournisseur; }

    public OffreFournisseurLigne getOffreFournisseurLigne() { return offreFournisseurLigne; }
    public void setOffreFournisseurLigne(OffreFournisseurLigne offreFournisseurLigne) { this.offreFournisseurLigne = offreFournisseurLigne; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
}