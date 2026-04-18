package com.university.fst.resourcemanagement.entity;

import com.university.fst.resourcemanagement.enums.StatutOffreFournisseur;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "offres_fournisseur")
public class OffreFournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appel_offre_id", nullable = false)
    private AppelOffre appelOffre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fournisseur_id", nullable = false)
    private Fournisseur fournisseur;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutOffreFournisseur statut = StatutOffreFournisseur.SOUMISE;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal montantTotal = BigDecimal.ZERO;

    @Column(nullable = false)
    private LocalDateTime dateSoumission = LocalDateTime.now();

    @Column(columnDefinition = "TEXT")
    private String motifDecision;

    @OneToMany(mappedBy = "offreFournisseur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OffreFournisseurLigne> lignes;

    public OffreFournisseur() {}

    public Long getId() { return id; }

    public AppelOffre getAppelOffre() { return appelOffre; }
    public void setAppelOffre(AppelOffre appelOffre) { this.appelOffre = appelOffre; }

    public Fournisseur getFournisseur() { return fournisseur; }
    public void setFournisseur(Fournisseur fournisseur) { this.fournisseur = fournisseur; }

    public StatutOffreFournisseur getStatut() { return statut; }
    public void setStatut(StatutOffreFournisseur statut) { this.statut = statut; }

    public BigDecimal getMontantTotal() { return montantTotal; }
    public void setMontantTotal(BigDecimal montantTotal) { this.montantTotal = montantTotal; }

    public LocalDateTime getDateSoumission() { return dateSoumission; }
    public void setDateSoumission(LocalDateTime dateSoumission) { this.dateSoumission = dateSoumission; }

    public String getMotifDecision() { return motifDecision; }
    public void setMotifDecision(String motifDecision) { this.motifDecision = motifDecision; }

    public List<OffreFournisseurLigne> getLignes() { return lignes; }
    public void setLignes(List<OffreFournisseurLigne> lignes) { this.lignes = lignes; }
}