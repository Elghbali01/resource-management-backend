package com.university.fst.resourcemanagement.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "departements")
public class Departement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nom;

    // Un département a au plus un chef (géré via ChefDepartement)
    @OneToOne(mappedBy = "departement", fetch = FetchType.LAZY)
    private ChefDepartement chef;

    @OneToMany(mappedBy = "departement", fetch = FetchType.LAZY)
    private List<Enseignant> enseignants;

    public Departement() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public ChefDepartement getChef() { return chef; }
    public void setChef(ChefDepartement chef) { this.chef = chef; }

    public List<Enseignant> getEnseignants() { return enseignants; }
    public void setEnseignants(List<Enseignant> enseignants) { this.enseignants = enseignants; }
}