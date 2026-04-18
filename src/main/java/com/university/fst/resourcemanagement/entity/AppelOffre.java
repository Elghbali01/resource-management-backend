package com.university.fst.resourcemanagement.entity;

import com.university.fst.resourcemanagement.enums.StatutAppelOffre;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "appels_offre")
public class AppelOffre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDate dateDebut;

    @Column(nullable = false)
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutAppelOffre statut = StatutAppelOffre.OUVERT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cree_par_id", nullable = false)
    private User creePar;

    @Column(nullable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();

    public AppelOffre() {}

    public Long getId() { return id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }

    public StatutAppelOffre getStatut() { return statut; }
    public void setStatut(StatutAppelOffre statut) { this.statut = statut; }

    public User getCreePar() { return creePar; }
    public void setCreePar(User creePar) { this.creePar = creePar; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
}