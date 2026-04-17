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

    @Size(max = 3000, message = "Les caractéristiques ne doivent pas dépasser 3000 caractères")
    private String caracteristiques;

    @Size(max = 3000, message = "La justification ne doit pas dépasser 3000 caractères")
    private String justification;

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

    public String getCaracteristiques() {
        return caracteristiques;
    }

    public void setCaracteristiques(String caracteristiques) {
        this.caracteristiques = caracteristiques;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }
}