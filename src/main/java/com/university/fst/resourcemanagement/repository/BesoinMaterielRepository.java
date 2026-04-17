package com.university.fst.resourcemanagement.repository;

import com.university.fst.resourcemanagement.entity.BesoinMateriel;
import com.university.fst.resourcemanagement.enums.NatureBesoin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BesoinMaterielRepository extends JpaRepository<BesoinMateriel, Long> {

    List<BesoinMateriel> findByDemandeCollecteIdAndEnseignantUserIdOrderByDateSoumissionDesc(
            Long demandeId, Long userId
    );

    List<BesoinMateriel> findByDemandeCollecteIdOrderByDateSoumissionDesc(Long demandeId);

    List<BesoinMateriel> findByDemandeCollecteIdAndNatureBesoinOrderByDateSoumissionDesc(
            Long demandeId, NatureBesoin natureBesoin
    );

    Optional<BesoinMateriel> findByIdAndEnseignantUserId(Long besoinId, Long userId);
}