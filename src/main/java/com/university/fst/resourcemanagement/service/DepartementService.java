package com.university.fst.resourcemanagement.service;

import com.university.fst.resourcemanagement.dto.AjouterBudgetRequest;
import com.university.fst.resourcemanagement.dto.DepartementRequest;
import com.university.fst.resourcemanagement.dto.DepartementResponse;

import java.util.List;

public interface DepartementService {

    /** Crée un nouveau département. */
    DepartementResponse creerDepartement(DepartementRequest request);

    /** Retourne tous les départements avec leur chef et nombre d'enseignants. */
    List<DepartementResponse> listerDepartements();

    /** Retourne un département par son id. */
    DepartementResponse getDepartement(Long id);

    /** Met à jour le nom d'un département. */
    DepartementResponse modifierDepartement(Long id, DepartementRequest request);

    /** Supprime un département (uniquement s'il n'a plus d'enseignants). */
    void supprimerDepartement(Long id);
    /** Ajoute un montant au budget existant du département (cumul). */
    DepartementResponse ajouterBudget(Long id, AjouterBudgetRequest request);
}