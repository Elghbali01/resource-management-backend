package com.university.fst.resourcemanagement.service;

import com.university.fst.resourcemanagement.dto.NotificationResponse;

import java.util.List;
import java.util.Map;

public interface NotificationService {

    /** Récupère toutes les notifications de l'utilisateur connecté. */
    List<NotificationResponse> getMesNotifications(Long userId);

    /** Marque une notification comme lue. */
    NotificationResponse marquerCommeLue(Long notificationId, Long userId);

    /** Retourne le nombre de notifications non lues. */
    Map<String, Long> compterNonLues(Long userId);
}