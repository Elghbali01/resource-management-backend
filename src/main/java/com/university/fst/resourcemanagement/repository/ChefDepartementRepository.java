package com.university.fst.resourcemanagement.repository;

import com.university.fst.resourcemanagement.entity.ChefDepartement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChefDepartementRepository extends JpaRepository<ChefDepartement, Long> {
    boolean existsByDepartementId(Long departementId);
    void deleteByUserId(Long userId);
}