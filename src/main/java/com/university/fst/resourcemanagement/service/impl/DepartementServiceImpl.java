package com.university.fst.resourcemanagement.service.impl;

import com.university.fst.resourcemanagement.dto.AjouterBudgetRequest;
import com.university.fst.resourcemanagement.dto.DepartementRequest;
import com.university.fst.resourcemanagement.dto.DepartementResponse;
import com.university.fst.resourcemanagement.entity.Departement;
import com.university.fst.resourcemanagement.repository.DepartementRepository;
import com.university.fst.resourcemanagement.service.DepartementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartementServiceImpl implements DepartementService {

    private final DepartementRepository departementRepository;

    public DepartementServiceImpl(DepartementRepository departementRepository) {
        this.departementRepository = departementRepository;
    }

    // ──────────────────────────────────────────────────────────────────────────
    // CRÉER
    // ──────────────────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public DepartementResponse creerDepartement(DepartementRequest request) {

        if (departementRepository.existsByNom(request.getNom())) {
            throw new RuntimeException(
                    "Un département avec le nom '" + request.getNom() + "' existe déjà");
        }

        Departement dept = new Departement();
        dept.setNom(request.getNom());
        Departement saved = departementRepository.save(dept);

        return toResponse(saved);
    }

    // ──────────────────────────────────────────────────────────────────────────
    // LISTER
    // ──────────────────────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<DepartementResponse> listerDepartements() {
        return departementRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ──────────────────────────────────────────────────────────────────────────
    // GET PAR ID
    // ──────────────────────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public DepartementResponse getDepartement(Long id) {
        return toResponse(findById(id));
    }

    // ──────────────────────────────────────────────────────────────────────────
    // MODIFIER
    // ──────────────────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public DepartementResponse modifierDepartement(Long id, DepartementRequest request) {

        Departement dept = findById(id);

        // Vérifier unicité du nouveau nom (en excluant le département courant)
        if (departementRepository.existsByNomAndIdNot(request.getNom(), id)) {
            throw new RuntimeException(
                    "Un autre département avec le nom '" + request.getNom() + "' existe déjà");
        }

        dept.setNom(request.getNom());
        Departement saved = departementRepository.save(dept);
        return toResponse(saved);
    }

    // ──────────────────────────────────────────────────────────────────────────
    // SUPPRIMER
    // ──────────────────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public void supprimerDepartement(Long id) {
        Departement dept = findById(id);

        // On interdit la suppression si des enseignants y sont rattachés
        if (dept.getEnseignants() != null && !dept.getEnseignants().isEmpty()) {
            throw new RuntimeException(
                    "Impossible de supprimer le département '" + dept.getNom()
                            + "' : il contient encore " + dept.getEnseignants().size()
                            + " enseignant(s)");
        }

        departementRepository.delete(dept);
    }

    // ──────────────────────────────────────────────────────────────────────────
    // AJOUTER BUDGET (cumul)
    // ──────────────────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public DepartementResponse ajouterBudget(Long id, AjouterBudgetRequest request) {
        Departement dept = findById(id);
        BigDecimal ancienBudget = dept.getBudget() != null ? dept.getBudget() : BigDecimal.ZERO;
        dept.setBudget(ancienBudget.add(request.getMontant()));
        return toResponse(departementRepository.save(dept));
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Helpers privés
    // ──────────────────────────────────────────────────────────────────────────

    private Departement findById(Long id) {
        return departementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Département introuvable avec l'id : " + id));
    }

    private DepartementResponse toResponse(Departement dept) {
        String chefNom = null, chefPrenom = null, chefEmail = null;
        if (dept.getChef() != null && dept.getChef().getUser() != null) {
            chefNom    = dept.getChef().getUser().getNom();
            chefPrenom = dept.getChef().getUser().getPrenom();
            chefEmail  = dept.getChef().getUser().getEmail();
        }
        int nb = dept.getEnseignants() == null ? 0 : dept.getEnseignants().size();
        return new DepartementResponse(
                dept.getId(), dept.getNom(), dept.getBudget(),
                chefNom, chefPrenom, chefEmail, nb);
    }
}