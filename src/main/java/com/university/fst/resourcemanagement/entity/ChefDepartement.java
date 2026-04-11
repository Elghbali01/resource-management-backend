package com.university.fst.resourcemanagement.entity;

import jakarta.persistence.*;

/**
 * Un chef de département est aussi un enseignant.
 * On stocke donc un lien vers Enseignant ET un lien direct vers User.
 * La contrainte unique sur departement_id garantit max 1 chef par département.
 */
@Entity
@Table(name = "chefs_departement")
public class ChefDepartement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Lien vers le profil User (pour login / rôle)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    // Lien vers le profil Enseignant (car le chef est aussi enseignant)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enseignant_id", nullable = false, unique = true)
    private Enseignant enseignant;

    // Contrainte unique => max 1 chef par département
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departement_id", nullable = false, unique = true)
    private Departement departement;

    public ChefDepartement() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Enseignant getEnseignant() { return enseignant; }
    public void setEnseignant(Enseignant enseignant) { this.enseignant = enseignant; }

    public Departement getDepartement() { return departement; }
    public void setDepartement(Departement departement) { this.departement = departement; }
}