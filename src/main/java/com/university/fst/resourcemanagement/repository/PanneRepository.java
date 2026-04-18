package com.university.fst.resourcemanagement.repository;

import com.university.fst.resourcemanagement.entity.Panne;
import com.university.fst.resourcemanagement.enums.StatutPanne;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PanneRepository extends JpaRepository<Panne, Long> {

    List<Panne> findByEnseignantUserIdOrderByDateSignalementDesc(Long userId);

    List<Panne> findAllByOrderByDateSignalementDesc();

    List<Panne> findByStatutOrderByDateSignalementDesc(StatutPanne statut);
}