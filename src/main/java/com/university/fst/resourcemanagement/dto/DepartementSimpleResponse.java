package com.university.fst.resourcemanagement.dto;

public class DepartementSimpleResponse {

    private Long id;
    private String nom;

    public DepartementSimpleResponse(Long id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Long getId() { return id; }
    public String getNom() { return nom; }
}