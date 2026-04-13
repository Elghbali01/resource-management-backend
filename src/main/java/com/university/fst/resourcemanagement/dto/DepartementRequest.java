package com.university.fst.resourcemanagement.dto;

import jakarta.validation.constraints.NotBlank;

public class DepartementRequest {

    @NotBlank(message = "Le nom du département est obligatoire")
    private String nom;

    public DepartementRequest() {}

    public String getNom()           { return nom; }
    public void   setNom(String nom) { this.nom = nom; }
}