package com.university.fst.resourcemanagement.repository;

import com.university.fst.resourcemanagement.entity.OffreFournisseurLigne;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OffreFournisseurLigneRepository extends JpaRepository<OffreFournisseurLigne, Long> {

    List<OffreFournisseurLigne> findByOffreFournisseurIdOrderByIdAsc(Long offreFournisseurId);
}