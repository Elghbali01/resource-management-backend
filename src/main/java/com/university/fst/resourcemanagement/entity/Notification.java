package com.university.fst.resourcemanagement.entity;

import com.university.fst.resourcemanagement.enums.TypeNotification;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(nullable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();

    @Column(nullable = false)
    private boolean lu = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeNotification typeNotification;

    // L'enseignant destinataire (via son User)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private User utilisateur;

    // Référence vers la demande concernée
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demande_id")
    private DemandeCollecte demande;

    public Notification() {}

    public Long getId()                          { return id; }
    public void setId(Long id)                   { this.id = id; }

    public String getMessage()                   { return message; }
    public void setMessage(String message)       { this.message = message; }

    public LocalDateTime getDateCreation()       { return dateCreation; }
    public void setDateCreation(LocalDateTime d) { this.dateCreation = d; }

    public boolean isLu()                        { return lu; }
    public void setLu(boolean lu)                { this.lu = lu; }

    public TypeNotification getTypeNotification()          { return typeNotification; }
    public void setTypeNotification(TypeNotification type) { this.typeNotification = type; }

    public User getUtilisateur()                 { return utilisateur; }
    public void setUtilisateur(User u)           { this.utilisateur = u; }

    public DemandeCollecte getDemande()          { return demande; }
    public void setDemande(DemandeCollecte d)    { this.demande = d; }
}