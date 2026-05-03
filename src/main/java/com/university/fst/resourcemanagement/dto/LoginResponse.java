package com.university.fst.resourcemanagement.dto;

public class LoginResponse {

    private String token;
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String role;
    private boolean hasDoubleRole;
    private boolean mustChangePassword;

    // ── Login normal (tous les rôles sauf chef étape 1) ──────────────────────
    public LoginResponse(String token, Long id, String nom, String prenom,
                         String email, String role, boolean mustChangePassword) {
        this.token              = token;
        this.id                 = id;
        this.nom                = nom;
        this.prenom             = prenom;
        this.email              = email;
        this.role               = role;
        this.hasDoubleRole      = false;
        this.mustChangePassword = mustChangePassword;
    }

    // ── Chef étape 1 : popup choix de rôle (pas de token encore) ─────────────
    public LoginResponse(Long id, String nom, String prenom,
                         String email, boolean hasDoubleRole) {
        this.token              = null;
        this.id                 = id;
        this.nom                = nom;
        this.prenom             = prenom;
        this.email              = email;
        this.role               = null;
        this.hasDoubleRole      = hasDoubleRole;
        this.mustChangePassword = false;
    }

    public String getToken()              { return token; }
    public Long getId()                   { return id; }
    public String getNom()                { return nom; }
    public String getPrenom()             { return prenom; }
    public String getEmail()              { return email; }
    public String getRole()               { return role; }
    public boolean isHasDoubleRole()      { return hasDoubleRole; }
    public boolean isMustChangePassword() { return mustChangePassword; }
}