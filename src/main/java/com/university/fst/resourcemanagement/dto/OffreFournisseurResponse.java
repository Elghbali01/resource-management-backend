package com.university.fst.resourcemanagement.dto;

import com.university.fst.resourcemanagement.enums.StatutOffreFournisseur;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OffreFournisseurResponse {

    private Long id;
    private Long appelOffreId;
    private String appelOffreTitre;
    private Long fournisseurId;
    private String nomSociete;
    private String email;
    private StatutOffreFournisseur statut;
    private BigDecimal montantTotal;
    private LocalDateTime dateSoumission;
    private String motifDecision;
    private List<OffreFournisseurLigneResponse> lignes;

    public OffreFournisseurResponse(
            Long id,
            Long appelOffreId,
            String appelOffreTitre,
            Long fournisseurId,
            String nomSociete,
            String email,
            StatutOffreFournisseur statut,
            BigDecimal montantTotal,
            LocalDateTime dateSoumission,
            String motifDecision,
            List<OffreFournisseurLigneResponse> lignes
    ) {
        this.id = id;
        this.appelOffreId = appelOffreId;
        this.appelOffreTitre = appelOffreTitre;
        this.fournisseurId = fournisseurId;
        this.nomSociete = nomSociete;
        this.email = email;
        this.statut = statut;
        this.montantTotal = montantTotal;
        this.dateSoumission = dateSoumission;
        this.motifDecision = motifDecision;
        this.lignes = lignes;
    }

    public Long getId() { return id; }
    public Long getAppelOffreId() { return appelOffreId; }
    public String getAppelOffreTitre() { return appelOffreTitre; }
    public Long getFournisseurId() { return fournisseurId; }
    public String getNomSociete() { return nomSociete; }
    public String getEmail() { return email; }
    public StatutOffreFournisseur getStatut() { return statut; }
    public BigDecimal getMontantTotal() { return montantTotal; }
    public LocalDateTime getDateSoumission() { return dateSoumission; }
    public String getMotifDecision() { return motifDecision; }
    public List<OffreFournisseurLigneResponse> getLignes() { return lignes; }
}