package com.university.fst.resourcemanagement.repository;

import com.university.fst.resourcemanagement.entity.AffectationRessource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AffectationRessourceRepository extends JpaRepository<AffectationRessource, Long> {

    Optional<AffectationRessource> findByRessourceId(Long ressourceId);

    List<AffectationRessource> findAllByOrderByDateAffectationDesc();
    List<AffectationRessource> findByEnseignantIdOrDepartementIdOrderByDateAffectationDesc(
            Long enseignantId,
            Long departementId
    );
}