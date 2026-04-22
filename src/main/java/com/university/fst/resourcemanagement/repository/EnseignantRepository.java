package com.university.fst.resourcemanagement.repository;

import com.university.fst.resourcemanagement.entity.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
    Optional<Enseignant> findByUserId(Long userId);
    /** Tous les enseignants d'un département donné. */
    List<Enseignant> findByDepartementId(Long departementId);

    boolean existsByUserId(Long userId);


}