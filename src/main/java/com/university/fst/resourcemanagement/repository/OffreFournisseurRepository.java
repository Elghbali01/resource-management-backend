package com.university.fst.resourcemanagement.repository;

import com.university.fst.resourcemanagement.entity.OffreFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OffreFournisseurRepository extends JpaRepository<OffreFournisseur, Long> {

    List<OffreFournisseur> findByAppelOffreIdOrderByDateSoumissionAsc(Long appelOffreId);

    List<OffreFournisseur> findByFournisseurIdOrderByDateSoumissionDesc(Long fournisseurId);

    Optional<OffreFournisseur> findByIdAndFournisseurId(Long id, Long fournisseurId);

    boolean existsByAppelOffreIdAndFournisseurId(Long appelOffreId, Long fournisseurId);
}