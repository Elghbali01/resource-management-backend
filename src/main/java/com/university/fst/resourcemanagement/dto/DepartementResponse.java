package com.university.fst.resourcemanagement.dto;

import java.math.BigDecimal;

public class DepartementResponse {

    private Long       id;
    private String     nom;
    private BigDecimal budget;
    private String     chefNom;
    private String     chefPrenom;
    private String     chefEmail;
    private int        nombreEnseignants;

    public DepartementResponse() {}

    public DepartementResponse(Long id, String nom, BigDecimal budget,
                               String chefNom, String chefPrenom, String chefEmail,
                               int nombreEnseignants) {
        this.id                = id;
        this.nom               = nom;
        this.budget            = budget;
        this.chefNom           = chefNom;
        this.chefPrenom        = chefPrenom;
        this.chefEmail         = chefEmail;
        this.nombreEnseignants = nombreEnseignants;
    }

    public Long       getId()                        { return id; }
    public void       setId(Long id)                 { this.id = id; }

    public String     getNom()                       { return nom; }
    public void       setNom(String nom)             { this.nom = nom; }

    public BigDecimal getBudget()                    { return budget; }
    public void       setBudget(BigDecimal budget)   { this.budget = budget; }

    public String getChefNom()               { return chefNom; }
    public void   setChefNom(String v)       { this.chefNom = v; }

    public String getChefPrenom()            { return chefPrenom; }
    public void   setChefPrenom(String v)    { this.chefPrenom = v; }

    public String getChefEmail()             { return chefEmail; }
    public void   setChefEmail(String v)     { this.chefEmail = v; }

    public int  getNombreEnseignants()       { return nombreEnseignants; }
    public void setNombreEnseignants(int v)  { this.nombreEnseignants = v; }
}