package com.university.fst.resourcemanagement.entity;

import com.university.fst.resourcemanagement.enums.StatutDemande;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "demandes_collecte")
public class DemandeCollecte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDate dateLimite;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutDemande statut = StatutDemande.BROUILLON;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departement_id", nullable = false)
    private Departement departement;

    // Le chef qui a créé la demande
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cree_par_id", nullable = false)
    private User creePar;

    public DemandeCollecte() {}

    public Long getId()                        { return id; }
    public void setId(Long id)                 { this.id = id; }

    public String getTitre()                   { return titre; }
    public void setTitre(String titre)         { this.titre = titre; }

    public String getDescription()             { return description; }
    public void setDescription(String d)       { this.description = d; }

    public LocalDateTime getDateCreation()     { return dateCreation; }
    public void setDateCreation(LocalDateTime d){ this.dateCreation = d; }

    public LocalDate getDateLimite()           { return dateLimite; }
    public void setDateLimite(LocalDate d)     { this.dateLimite = d; }

    public StatutDemande getStatut()           { return statut; }
    public void setStatut(StatutDemande s)     { this.statut = s; }

    public Departement getDepartement()        { return departement; }
    public void setDepartement(Departement d)  { this.departement = d; }

    public User getCreePar()                   { return creePar; }
    public void setCreePar(User u)             { this.creePar = u; }
}