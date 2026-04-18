package com.university.fst.resourcemanagement.repository;

import com.university.fst.resourcemanagement.entity.RessourceMaterielle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RessourceMaterielleRepository extends JpaRepository<RessourceMaterielle, Long> {

    boolean existsByOffreFournisseurId(Long offreId);

    long countByOffreFournisseurId(Long offreId);

    long count();

    List<RessourceMaterielle> findAllByOrderByDateCreationDesc();
}