package com.university.fst.resourcemanagement.repository;

import com.university.fst.resourcemanagement.entity.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {
    Optional<Fournisseur> findByUser_Email(String email);
    boolean existsByUser_Email(String email);
}