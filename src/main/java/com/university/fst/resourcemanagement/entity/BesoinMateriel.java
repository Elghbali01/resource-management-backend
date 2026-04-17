package com.university.fst.resourcemanagement.entity;

import com.university.fst.resourcemanagement.enums.NatureBesoin;
import com.university.fst.resourcemanagement.enums.TypeMateriel;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "besoins_materiel")
public class BesoinMateriel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Demande de collecte à laquelle appartient ce besoin
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demande_collecte_id", nullable = false)
    private DemandeCollecte demandeCollecte;

    // Pour l'instant utilisé pour les besoins enseignants
    // nullable = true pour laisser la porte ouverte aux besoins COLLECTIFS du chef plus tard
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enseignant_id")
    private Enseignant enseignant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NatureBesoin natureBesoin = NatureBesoin.INDIVIDUEL;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeMateriel typeMateriel;

    @Column(nullable = false)
    private Integer quantite;

    private String marqueSouhaitee;

    @Column(columnDefinition = "TEXT")
    private String caracteristiques;

    @Column(columnDefinition = "TEXT")
    private String justification;

    @Column(nullable = false)
    private LocalDateTime dateSoumission = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime derniereModification = LocalDateTime.now();

    public BesoinMateriel() {}

    public Long getId() {
        return id;
    }

    public DemandeCollecte getDemandeCollecte() {
        return demandeCollecte;
    }

    public void setDemandeCollecte(DemandeCollecte demandeCollecte) {
        this.demandeCollecte = demandeCollecte;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    public NatureBesoin getNatureBesoin() {
        return natureBesoin;
    }

    public void setNatureBesoin(NatureBesoin natureBesoin) {
        this.natureBesoin = natureBesoin;
    }

    public TypeMateriel getTypeMateriel() {
        return typeMateriel;
    }

    public void setTypeMateriel(TypeMateriel typeMateriel) {
        this.typeMateriel = typeMateriel;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public String getMarqueSouhaitee() {
        return marqueSouhaitee;
    }

    public void setMarqueSouhaitee(String marqueSouhaitee) {
        this.marqueSouhaitee = marqueSouhaitee;
    }

    public String getCaracteristiques() {
        return caracteristiques;
    }

    public void setCaracteristiques(String caracteristiques) {
        this.caracteristiques = caracteristiques;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public LocalDateTime getDateSoumission() {
        return dateSoumission;
    }

    public void setDateSoumission(LocalDateTime dateSoumission) {
        this.dateSoumission = dateSoumission;
    }

    public LocalDateTime getDerniereModification() {
        return derniereModification;
    }

    public void setDerniereModification(LocalDateTime derniereModification) {
        this.derniereModification = derniereModification;
    }
}