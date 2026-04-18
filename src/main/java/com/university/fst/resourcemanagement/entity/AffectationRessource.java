package com.university.fst.resourcemanagement.entity;

import com.university.fst.resourcemanagement.enums.TypeBeneficiaireAffectation;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "affectations_ressource")
public class AffectationRessource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ressource_id", nullable = false, unique = true)
    private RessourceMaterielle ressource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departement_id", nullable = false)
    private Departement departement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enseignant_id")
    private Enseignant enseignant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeBeneficiaireAffectation typeBeneficiaire;

    @Column(nullable = false)
    private LocalDateTime dateAffectation = LocalDateTime.now();

    public AffectationRessource() {}

    public Long getId() { return id; }

    public RessourceMaterielle getRessource() { return ressource; }
    public void setRessource(RessourceMaterielle ressource) { this.ressource = ressource; }

    public Departement getDepartement() { return departement; }
    public void setDepartement(Departement departement) { this.departement = departement; }

    public Enseignant getEnseignant() { return enseignant; }
    public void setEnseignant(Enseignant enseignant) { this.enseignant = enseignant; }

    public TypeBeneficiaireAffectation getTypeBeneficiaire() { return typeBeneficiaire; }
    public void setTypeBeneficiaire(TypeBeneficiaireAffectation typeBeneficiaire) { this.typeBeneficiaire = typeBeneficiaire; }

    public LocalDateTime getDateAffectation() { return dateAffectation; }
    public void setDateAffectation(LocalDateTime dateAffectation) { this.dateAffectation = dateAffectation; }
}