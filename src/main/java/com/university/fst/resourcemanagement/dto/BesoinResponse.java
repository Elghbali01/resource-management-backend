package com.university.fst.resourcemanagement.dto;

import com.university.fst.resourcemanagement.enums.NatureBesoin;
import com.university.fst.resourcemanagement.enums.TypeMateriel;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BesoinResponse {

    private Long id;
    private Long demandeId;
    private String demandeTitre;
    private LocalDate demandeDateLimite;
    private TypeMateriel typeMateriel;
    private NatureBesoin natureBesoin;
    private Integer quantite;
    private String marqueSouhaitee;
    private String caracteristiques;
    private String justification;
    private LocalDateTime dateSoumission;
    private LocalDateTime derniereModification;
    private Long enseignantId;
    private Long enseignantUserId;
    private String enseignantNom;
    private String enseignantPrenom;
    private String departementNom;

    public BesoinResponse(
            Long id,
            Long demandeId,
            String demandeTitre,
            LocalDate demandeDateLimite,
            TypeMateriel typeMateriel,
            NatureBesoin natureBesoin,
            Integer quantite,
            String marqueSouhaitee,
            String caracteristiques,
            String justification,
            LocalDateTime dateSoumission,
            LocalDateTime derniereModification,
            Long enseignantId,
            Long enseignantUserId,
            String enseignantNom,
            String enseignantPrenom,
            String departementNom
    ) {
        this.id = id;
        this.demandeId = demandeId;
        this.demandeTitre = demandeTitre;
        this.demandeDateLimite = demandeDateLimite;
        this.typeMateriel = typeMateriel;
        this.natureBesoin = natureBesoin;
        this.quantite = quantite;
        this.marqueSouhaitee = marqueSouhaitee;
        this.caracteristiques = caracteristiques;
        this.justification = justification;
        this.dateSoumission = dateSoumission;
        this.derniereModification = derniereModification;
        this.enseignantId = enseignantId;
        this.enseignantUserId = enseignantUserId;
        this.enseignantNom = enseignantNom;
        this.enseignantPrenom = enseignantPrenom;
        this.departementNom = departementNom;
    }

    public Long getId() {
        return id;
    }

    public Long getDemandeId() {
        return demandeId;
    }

    public String getDemandeTitre() {
        return demandeTitre;
    }

    public LocalDate getDemandeDateLimite() {
        return demandeDateLimite;
    }

    public TypeMateriel getTypeMateriel() {
        return typeMateriel;
    }

    public NatureBesoin getNatureBesoin() {
        return natureBesoin;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public String getMarqueSouhaitee() {
        return marqueSouhaitee;
    }

    public String getCaracteristiques() {
        return caracteristiques;
    }

    public String getJustification() {
        return justification;
    }

    public LocalDateTime getDateSoumission() {
        return dateSoumission;
    }

    public LocalDateTime getDerniereModification() {
        return derniereModification;
    }

    public Long getEnseignantId() {
        return enseignantId;
    }

    public Long getEnseignantUserId() {
        return enseignantUserId;
    }

    public String getEnseignantNom() {
        return enseignantNom;
    }

    public String getEnseignantPrenom() {
        return enseignantPrenom;
    }

    public String getDepartementNom() {
        return departementNom;
    }
}