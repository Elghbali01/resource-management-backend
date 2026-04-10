package com.university.fst.resourcemanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "fournisseurs")
public class Fournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomSociete;

    // Informations complémentaires renseignées lors de la première livraison
    private String lieu;
    private String adresse;
    private String siteInternet;
    private String gerant;

    // Lien 1-1 vers le compte User (pour login, rôle, statut)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    // Liste noire
    @Column(nullable = false)
    private boolean blacklisted = false;

    private String blacklistMotif;

    public Fournisseur() {}

    // Getters & Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomSociete() { return nomSociete; }
    public void setNomSociete(String nomSociete) { this.nomSociete = nomSociete; }

    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getSiteInternet() { return siteInternet; }
    public void setSiteInternet(String siteInternet) { this.siteInternet = siteInternet; }

    public String getGerant() { return gerant; }
    public void setGerant(String gerant) { this.gerant = gerant; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public boolean isBlacklisted() { return blacklisted; }
    public void setBlacklisted(boolean blacklisted) { this.blacklisted = blacklisted; }

    public String getBlacklistMotif() { return blacklistMotif; }
    public void setBlacklistMotif(String blacklistMotif) { this.blacklistMotif = blacklistMotif; }
}