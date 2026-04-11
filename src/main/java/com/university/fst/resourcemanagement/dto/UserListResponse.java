package com.university.fst.resourcemanagement.dto;

/**
 * Réponse renvoyée dans le tableau de la page admin :
 * colonnes : id | nom | prénom | email | rôle | statut | actions
 */
public class UserListResponse {

    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String role;
    private String status;
    // Pour les enseignants / chefs de département : nom du département
    private String departementNom;

    public UserListResponse() {}

    public UserListResponse(Long id, String nom, String prenom, String email,
                            String role, String status, String departementNom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.role = role;
        this.status = status;
        this.departementNom = departementNom;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDepartementNom() { return departementNom; }
    public void setDepartementNom(String departementNom) { this.departementNom = departementNom; }
}