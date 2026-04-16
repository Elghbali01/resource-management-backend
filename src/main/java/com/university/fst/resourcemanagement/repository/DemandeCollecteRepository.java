package com.university.fst.resourcemanagement.repository;

import com.university.fst.resourcemanagement.entity.DemandeCollecte;
import com.university.fst.resourcemanagement.enums.StatutDemande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeCollecteRepository extends JpaRepository<DemandeCollecte, Long> {

    // Toutes les demandes d'un département
    List<DemandeCollecte> findByDepartementId(Long departementId);

    // Demandes ouvertes d'un département (pour les enseignants)
    List<DemandeCollecte> findByDepartementIdAndStatut(Long departementId, StatutDemande statut);
}