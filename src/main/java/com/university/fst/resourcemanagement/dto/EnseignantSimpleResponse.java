package com.university.fst.resourcemanagement.dto;

public class EnseignantSimpleResponse {

    private Long id;
    private String nom;
    private String prenom;

    public EnseignantSimpleResponse(Long id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Long getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
}