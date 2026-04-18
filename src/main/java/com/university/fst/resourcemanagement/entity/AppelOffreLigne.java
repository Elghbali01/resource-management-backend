package com.university.fst.resourcemanagement.entity;

import com.university.fst.resourcemanagement.enums.TypeAffectationPrevue;
import jakarta.persistence.*;

@Entity
@Table(name = "appel_offre_lignes")
public class AppelOffreLigne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appel_offre_id", nullable = false)
    private AppelOffre appelOffre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demande_collecte_id", nullable = false)
    private DemandeCollecte demandeCollecte;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "affectation_prevue_id", nullable = false)
    private AffectationPrevue affectationPrevue;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descriptionMateriel;

    @Column(nullable = false)
    private Integer quantite;

    @Column(nullable = false)
    private String departementNom;

    private String enseignantNom;
    private String enseignantPrenom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeAffectationPrevue typeAffectation;

    public AppelOffreLigne() {}

    public Long getId() { return id; }

    public AppelOffre getAppelOffre() { return appelOffre; }
    public void setAppelOffre(AppelOffre appelOffre) { this.appelOffre = appelOffre; }

    public DemandeCollecte getDemandeCollecte() { return demandeCollecte; }
    public void setDemandeCollecte(DemandeCollecte demandeCollecte) { this.demandeCollecte = demandeCollecte; }

    public AffectationPrevue getAffectationPrevue() { return affectationPrevue; }
    public void setAffectationPrevue(AffectationPrevue affectationPrevue) { this.affectationPrevue = affectationPrevue; }

    public String getDescriptionMateriel() { return descriptionMateriel; }
    public void setDescriptionMateriel(String descriptionMateriel) { this.descriptionMateriel = descriptionMateriel; }

    public Integer getQuantite() { return quantite; }
    public void setQuantite(Integer quantite) { this.quantite = quantite; }

    public String getDepartementNom() { return departementNom; }
    public void setDepartementNom(String departementNom) { this.departementNom = departementNom; }

    public String getEnseignantNom() { return enseignantNom; }
    public void setEnseignantNom(String enseignantNom) { this.enseignantNom = enseignantNom; }

    public String getEnseignantPrenom() { return enseignantPrenom; }
    public void setEnseignantPrenom(String enseignantPrenom) { this.enseignantPrenom = enseignantPrenom; }

    public TypeAffectationPrevue getTypeAffectation() { return typeAffectation; }
    public void setTypeAffectation(TypeAffectationPrevue typeAffectation) { this.typeAffectation = typeAffectation; }
}