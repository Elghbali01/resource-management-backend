package com.university.fst.resourcemanagement.repository;

import com.university.fst.resourcemanagement.entity.DemandeCollecte;
import com.university.fst.resourcemanagement.enums.StatutDemande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DemandeCollecteRepository extends JpaRepository<DemandeCollecte, Long> {

    List<DemandeCollecte> findByDepartementId(Long departementId);

    List<DemandeCollecte> findByDepartementIdAndStatut(Long departementId, StatutDemande statut);

    boolean existsByDepartementIdAndStatut(Long departementId, StatutDemande statut);

    Optional<DemandeCollecte> findByIdAndDepartementId(Long id, Long departementId);

    List<DemandeCollecte> findByStatutOrderByDateTransmissionResponsableDesc(StatutDemande statut);
}