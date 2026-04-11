package com.university.fst.resourcemanagement.repository;

import com.university.fst.resourcemanagement.entity.Technicien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnicienRepository extends JpaRepository<Technicien, Long> {
    void deleteByUserId(Long userId);
}