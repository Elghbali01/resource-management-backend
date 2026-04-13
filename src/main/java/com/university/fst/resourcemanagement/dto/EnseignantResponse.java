package com.university.fst.resourcemanagement.dto;

public class EnseignantResponse {

    private Long   id;
    private Long   userId;
    private String nom;
    private String prenom;
    private String email;
    private String status;
    private String departementNom;

    public EnseignantResponse() {}

    public EnseignantResponse(Long id, Long userId, String nom, String prenom,
                              String email, String status, String departementNom) {
        this.id             = id;
        this.userId         = userId;
        this.nom            = nom;
        this.prenom         = prenom;
        this.email          = email;
        this.status         = status;
        this.departementNom = departementNom;
    }

    public Long   getId()                      { return id; }
    public void   setId(Long id)               { this.id = id; }

    public Long   getUserId()                  { return userId; }
    public void   setUserId(Long userId)       { this.userId = userId; }

    public String getNom()                     { return nom; }
    public void   setNom(String nom)           { this.nom = nom; }

    public String getPrenom()                  { return prenom; }
    public void   setPrenom(String prenom)     { this.prenom = prenom; }

    public String getEmail()                   { return email; }
    public void   setEmail(String email)       { this.email = email; }

    public String getStatus()                  { return status; }
    public void   setStatus(String status)     { this.status = status; }

    public String getDepartementNom()              { return departementNom; }
    public void   setDepartementNom(String v)      { this.departementNom = v; }
}