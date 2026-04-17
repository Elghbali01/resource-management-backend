package com.university.fst.resourcemanagement.repository;

import com.university.fst.resourcemanagement.entity.AffectationPrevue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AffectationPrevueRepository extends JpaRepository<AffectationPrevue, Long> {

    List<AffectationPrevue> findByDemandeCollecteIdOrderByIdAsc(Long demandeId);

    void deleteByDemandeCollecteId(Long demandeId);

    long countByDemandeCollecteId(Long demandeId);
}