package com.university.fst.resourcemanagement.repository;

import com.university.fst.resourcemanagement.entity.BesoinMateriel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BesoinMaterielRepository extends JpaRepository<BesoinMateriel, Long> {

    // Tous les besoins d'un enseignant pour une demande donnée
    List<BesoinMateriel> findByDemandeCollecteIdAndEnseignantUserIdOrderByDateSoumissionDesc(
            Long demandeId, Long userId
    );

    // Tous les besoins d'une demande (consultation chef)
    List<BesoinMateriel> findByDemandeCollecteIdOrderByDateSoumissionDesc(Long demandeId);

    // Sécurité : besoin appartenant à l'enseignant connecté
    Optional<BesoinMateriel> findByIdAndEnseignantUserId(Long besoinId, Long userId);
}