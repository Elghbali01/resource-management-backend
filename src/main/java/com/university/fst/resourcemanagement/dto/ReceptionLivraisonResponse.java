package com.university.fst.resourcemanagement.dto;

import java.time.LocalDate;
import java.util.List;

public class ReceptionLivraisonResponse {

    private Long offreId;
    private String nomSociete;
    private LocalDate dateLivraison;
    private int nombreRessourcesCreees;
    private List<RessourceResponse> ressources;

    public ReceptionLivraisonResponse(
            Long offreId,
            String nomSociete,
            LocalDate dateLivraison,
            int nombreRessourcesCreees,
            List<RessourceResponse> ressources
    ) {
        this.offreId = offreId;
        this.nomSociete = nomSociete;
        this.dateLivraison = dateLivraison;
        this.nombreRessourcesCreees = nombreRessourcesCreees;
        this.ressources = ressources;
    }

    public Long getOffreId() { return offreId; }
    public String getNomSociete() { return nomSociete; }
    public LocalDate getDateLivraison() { return dateLivraison; }
    public int getNombreRessourcesCreees() { return nombreRessourcesCreees; }
    public List<RessourceResponse> getRessources() { return ressources; }
}