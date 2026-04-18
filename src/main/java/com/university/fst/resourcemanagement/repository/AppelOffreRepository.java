package com.university.fst.resourcemanagement.repository;

import com.university.fst.resourcemanagement.entity.AppelOffre;
import com.university.fst.resourcemanagement.enums.StatutAppelOffre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppelOffreRepository extends JpaRepository<AppelOffre, Long> {

    List<AppelOffre> findAllByOrderByDateCreationDesc();

    List<AppelOffre> findByStatutAndDateDebutLessThanEqualAndDateFinGreaterThanEqualOrderByDateCreationDesc(
            StatutAppelOffre statut,
            LocalDate dateDebut,
            LocalDate dateFin
    );
}