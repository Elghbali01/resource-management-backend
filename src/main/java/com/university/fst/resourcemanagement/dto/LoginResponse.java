package com.university.fst.resourcemanagement.dto;

public class LoginResponse {

    private String token;
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String role;

    // true seulement si c'est un CHEF_DEPARTEMENT (double rôle possible)
    // Le front affiche la popup si hasDoubleRole == true ET token == null
    private boolean hasDoubleRole;

    // ── Constructeur login normal (token généré) ───────────────────────────────
    public LoginResponse(String token, Long id, String nom, String prenom,
                         String email, String role) {
        this.token        = token;
        this.id           = id;
        this.nom          = nom;
        this.prenom       = prenom;
        this.email        = email;
        this.role         = role;
        this.hasDoubleRole = false;
    }

    // ── Constructeur étape 1 chef (pas de token, juste le flag) ───────────────
    public LoginResponse(Long id, String nom, String prenom,
                         String email, boolean hasDoubleRole) {
        this.token        = null;
        this.id           = id;
        this.nom          = nom;
        this.prenom       = prenom;
        this.email        = email;
        this.role         = null;
        this.hasDoubleRole = hasDoubleRole;
    }

    public String getToken()         { return token; }
    public Long getId()              { return id; }
    public String getNom()           { return nom; }
    public String getPrenom()        { return prenom; }
    public String getEmail()         { return email; }
    public String getRole()          { return role; }
    public boolean isHasDoubleRole() { return hasDoubleRole; }
}