package com.university.fst.resourcemanagement.dto;

public class FournisseurAdminResponse {

    private Long fournisseurId;
    private Long userId;
    private String nomSociete;
    private String email;
    private boolean blacklisted;
    private String blacklistMotif;
    private String lieu;
    private String adresse;
    private String siteInternet;
    private String gerant;

    public FournisseurAdminResponse(
            Long fournisseurId,
            Long userId,
            String nomSociete,
            String email,
            boolean blacklisted,
            String blacklistMotif,
            String lieu,
            String adresse,
            String siteInternet,
            String gerant
    ) {
        this.fournisseurId = fournisseurId;
        this.userId = userId;
        this.nomSociete = nomSociete;
        this.email = email;
        this.blacklisted = blacklisted;
        this.blacklistMotif = blacklistMotif;
        this.lieu = lieu;
        this.adresse = adresse;
        this.siteInternet = siteInternet;
        this.gerant = gerant;
    }

    public Long getFournisseurId() { return fournisseurId; }
    public Long getUserId() { return userId; }
    public String getNomSociete() { return nomSociete; }
    public String getEmail() { return email; }
    public boolean isBlacklisted() { return blacklisted; }
    public String getBlacklistMotif() { return blacklistMotif; }
    public String getLieu() { return lieu; }
    public String getAdresse() { return adresse; }
    public String getSiteInternet() { return siteInternet; }
    public String getGerant() { return gerant; }
}