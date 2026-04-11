package com.university.fst.resourcemanagement.repository;

import com.university.fst.resourcemanagement.entity.ResponsableResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponsableResourceRepository extends JpaRepository<ResponsableResource, Long> {
    void deleteByUserId(Long userId);
}