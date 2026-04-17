package com.university.fst.resourcemanagement.entity;

import com.university.fst.resourcemanagement.enums.TypeAffectationPrevue;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "affectations_prevues")
public class AffectationPrevue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demande_collecte_id", nullable = false)
    private DemandeCollecte demandeCollecte;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "besoin_materiel_id")
    private BesoinMateriel besoinMateriel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departement_id", nullable = false)
    private Departement departement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enseignant_id")
    private Enseignant enseignant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeAffectationPrevue typeAffectation;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descriptionMateriel;

    @Column(nullable = false)
    private Integer quantite;

    @Column(nullable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();

    public AffectationPrevue() {}

    public Long getId() { return id; }

    public DemandeCollecte getDemandeCollecte() { return demandeCollecte; }
    public void setDemandeCollecte(DemandeCollecte demandeCollecte) { this.demandeCollecte = demandeCollecte; }

    public BesoinMateriel getBesoinMateriel() { return besoinMateriel; }
    public void setBesoinMateriel(BesoinMateriel besoinMateriel) { this.besoinMateriel = besoinMateriel; }

    public Departement getDepartement() { return departement; }
    public void setDepartement(Departement departement) { this.departement = departement; }

    public Enseignant getEnseignant() { return enseignant; }
    public void setEnseignant(Enseignant enseignant) { this.enseignant = enseignant; }

    public TypeAffectationPrevue getTypeAffectation() { return typeAffectation; }
    public void setTypeAffectation(TypeAffectationPrevue typeAffectation) { this.typeAffectation = typeAffectation; }

    public String getDescriptionMateriel() { return descriptionMateriel; }
    public void setDescriptionMateriel(String descriptionMateriel) { this.descriptionMateriel = descriptionMateriel; }

    public Integer getQuantite() { return quantite; }
    public void setQuantite(Integer quantite) { this.quantite = quantite; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
}