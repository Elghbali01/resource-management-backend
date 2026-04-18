package com.university.fst.resourcemanagement.dto;

import com.university.fst.resourcemanagement.enums.FrequencePanne;
import com.university.fst.resourcemanagement.enums.OrdrePanne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ConstatPanneRequest {

    @NotBlank(message = "L'explication de la panne est obligatoire")
    private String explicationPanne;

    @NotNull(message = "La date d'apparition est obligatoire")
    private LocalDate dateApparition;

    @NotNull(message = "La fréquence est obligatoire")
    private FrequencePanne frequence;

    @NotNull(message = "L'ordre de la panne est obligatoire")
    private OrdrePanne ordrePanne;

    @NotNull(message = "Le niveau de sévérité est obligatoire")
    private Boolean severe;

    public ConstatPanneRequest() {}

    public String getExplicationPanne() { return explicationPanne; }
    public void setExplicationPanne(String explicationPanne) { this.explicationPanne = explicationPanne; }

    public LocalDate getDateApparition() { return dateApparition; }
    public void setDateApparition(LocalDate dateApparition) { this.dateApparition = dateApparition; }

    public FrequencePanne getFrequence() { return frequence; }
    public void setFrequence(FrequencePanne frequence) { this.frequence = frequence; }

    public OrdrePanne getOrdrePanne() { return ordrePanne; }
    public void setOrdrePanne(OrdrePanne ordrePanne) { this.ordrePanne = ordrePanne; }

    public Boolean getSevere() { return severe; }
    public void setSevere(Boolean severe) { this.severe = severe; }
}