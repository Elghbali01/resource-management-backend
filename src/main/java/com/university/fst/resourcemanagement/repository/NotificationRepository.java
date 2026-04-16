package com.university.fst.resourcemanagement.repository;

import com.university.fst.resourcemanagement.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Toutes les notifications d'un utilisateur (triées par date desc)
    List<Notification> findByUtilisateurIdOrderByDateCreationDesc(Long utilisateurId);

    // Notifications non lues d'un utilisateur
    List<Notification> findByUtilisateurIdAndLuFalse(Long utilisateurId);

    // Compte les non lues (pour le badge)
    long countByUtilisateurIdAndLuFalse(Long utilisateurId);
}