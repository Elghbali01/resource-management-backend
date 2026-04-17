package com.university.fst.resourcemanagement.repository;

import com.university.fst.resourcemanagement.entity.DemandeCollecte;
import com.university.fst.resourcemanagement.enums.StatutDemande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DemandeCollecteRepository extends JpaRepository<DemandeCollecte, Long> {

    // Toutes les demandes d'un département
    List<DemandeCollecte> findByDepartementId(Long departementId);

    // Demandes ouvertes d'un département (pour les enseignants)
    List<DemandeCollecte> findByDepartementIdAndStatut(Long departementId, StatutDemande statut);

    // Vérifie s'il existe déjà une demande ouverte pour ce département
    boolean existsByDepartementIdAndStatut(Long departementId, StatutDemande statut);

    // Sécurité : retrouver une demande appartenant bien au département du chef
    Optional<DemandeCollecte> findByIdAndDepartementId(Long id, Long departementId);
}