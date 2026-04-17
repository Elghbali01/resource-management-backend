package com.university.fst.resourcemanagement.dto;

import com.university.fst.resourcemanagement.enums.TypeMateriel;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BesoinRequest {

    @NotNull(message = "L'identifiant de la demande est obligatoire")
    private Long demandeId;

    @NotNull(message = "Le type de matériel est obligatoire")
    private TypeMateriel typeMateriel;

    @NotNull(message = "La quantité est obligatoire")
    @Min(value = 1, message = "La quantité doit être supérieure ou égale à 1")
    private Integer quantite;

    @Size(max = 100, message = "La marque souhaitée ne doit pas dépasser 100 caractères")
    private String marqueSouhaitee;

    @Size(max = 3000, message = "La justification ne doit pas dépasser 3000 caractères")
    private String justification;

    // ── Champs ORDINATEUR ─────────────────────────────────────────────
    @Size(max = 100, message = "Le CPU ne doit pas dépasser 100 caractères")
    private String cpu;

    @Size(max = 100, message = "La RAM ne doit pas dépasser 100 caractères")
    private String ram;

    @Size(max = 100, message = "Le disque dur ne doit pas dépasser 100 caractères")
    private String disqueDur;

    @Size(max = 100, message = "L'écran ne doit pas dépasser 100 caractères")
    private String ecran;

    // ── Champs IMPRIMANTE ─────────────────────────────────────────────
    @Size(max = 100, message = "La vitesse d'impression ne doit pas dépasser 100 caractères")
    private String vitesseImpression;

    @Size(max = 100, message = "La résolution ne doit pas dépasser 100 caractères")
    private String resolution;

    public BesoinRequest() {}

    public Long getDemandeId() {
        return demandeId;
    }

    public void setDemandeId(Long demandeId) {
        this.demandeId = demandeId;
    }

    public TypeMateriel getTypeMateriel() {
        return typeMateriel;
    }

    public void setTypeMateriel(TypeMateriel typeMateriel) {
        this.typeMateriel = typeMateriel;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public String getMarqueSouhaitee() {
        return marqueSouhaitee;
    }

    public void setMarqueSouhaitee(String marqueSouhaitee) {
        this.marqueSouhaitee = marqueSouhaitee;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getDisqueDur() {
        return disqueDur;
    }

    public void setDisqueDur(String disqueDur) {
        this.disqueDur = disqueDur;
    }

    public String getEcran() {
        return ecran;
    }

    public void setEcran(String ecran) {
        this.ecran = ecran;
    }

    public String getVitesseImpression() {
        return vitesseImpression;
    }

    public void setVitesseImpression(String vitesseImpression) {
        this.vitesseImpression = vitesseImpression;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
}