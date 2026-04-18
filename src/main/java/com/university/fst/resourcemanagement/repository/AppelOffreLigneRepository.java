package com.university.fst.resourcemanagement.repository;

import com.university.fst.resourcemanagement.entity.AppelOffreLigne;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppelOffreLigneRepository extends JpaRepository<AppelOffreLigne, Long> {

    List<AppelOffreLigne> findByAppelOffreIdOrderByIdAsc(Long appelOffreId);

    long countByAppelOffreId(Long appelOffreId);
}