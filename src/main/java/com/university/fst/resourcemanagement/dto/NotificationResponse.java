package com.university.fst.resourcemanagement.dto;

import com.university.fst.resourcemanagement.enums.TypeNotification;
import java.time.LocalDateTime;

public class NotificationResponse {

    private Long id;
    private String message;
    private LocalDateTime dateCreation;
    private boolean lu;
    private TypeNotification typeNotification;
    private Long demandeId; // peut être null

    public NotificationResponse(Long id, String message, LocalDateTime dateCreation,
                                boolean lu, TypeNotification typeNotification,
                                Long demandeId) {
        this.id = id;
        this.message = message;
        this.dateCreation = dateCreation;
        this.lu = lu;
        this.typeNotification = typeNotification;
        this.demandeId = demandeId;
    }

    public Long getId()                          { return id; }
    public String getMessage()                   { return message; }
    public LocalDateTime getDateCreation()       { return dateCreation; }
    public boolean isLu()                        { return lu; }
    public TypeNotification getTypeNotification(){ return typeNotification; }
    public Long getDemandeId()                   { return demandeId; }
}