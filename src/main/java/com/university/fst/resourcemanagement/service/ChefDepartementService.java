package com.university.fst.resourcemanagement.service;

import com.university.fst.resourcemanagement.dto.EnseignantResponse;

import java.util.List;

public interface ChefDepartementService {

    /**
     * Retourne la liste des enseignants du département
     * dont le chef connecté est responsable.
     *
     * @param chefUserId  l'id User du chef connecté (extrait du JWT)
     */
    List<EnseignantResponse> listerEnseignantsDuDepartement(Long chefUserId);
}