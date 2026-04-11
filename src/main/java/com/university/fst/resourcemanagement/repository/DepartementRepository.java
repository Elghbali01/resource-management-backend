package com.university.fst.resourcemanagement.repository;

import com.university.fst.resourcemanagement.entity.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, Long> {
    boolean existsByNom(String nom);
}