package com.university.fst.resourcemanagement.service.impl;

import com.university.fst.resourcemanagement.dto.NotificationResponse;
import com.university.fst.resourcemanagement.entity.Notification;
import com.university.fst.resourcemanagement.repository.NotificationRepository;
import com.university.fst.resourcemanagement.service.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getMesNotifications(Long userId) {
        return notificationRepository
                .findByUtilisateurIdOrderByDateCreationDesc(userId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public NotificationResponse marquerCommeLue(Long notificationId, Long userId) {
        Notification notif = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException(
                        "Notification introuvable : " + notificationId));

        // Sécurité : vérifier que la notification appartient bien à cet utilisateur
        if (!notif.getUtilisateur().getId().equals(userId)) {
            throw new RuntimeException("Accès refusé à cette notification");
        }

        notif.setLu(true);
        return toResponse(notificationRepository.save(notif));
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> compterNonLues(Long userId) {
        long count = notificationRepository.countByUtilisateurIdAndLuFalse(userId);
        return Map.of("nonLues", count);
    }

    private NotificationResponse toResponse(Notification n) {
        return new NotificationResponse(
                n.getId(),
                n.getMessage(),
                n.getDateCreation(),
                n.isLu(),
                n.getTypeNotification(),
                n.getDemande() != null ? n.getDemande().getId() : null
        );
    }
}