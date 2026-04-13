package com.university.fst.resourcemanagement.repository;

import com.university.fst.resourcemanagement.entity.ChefDepartement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChefDepartementRepository extends JpaRepository<ChefDepartement, Long> {
    boolean existsByDepartementId(Long departementId);
    void deleteByUserId(Long userId);
    /** Trouve le chef par son userId → pour récupérer son département. */
    Optional<ChefDepartement> findByUserId(Long userId);
}