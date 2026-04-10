package com.university.fst.resourcemanagement.dto;

public class FournisseurRegisterResponse {

    private Long fournisseurId;
    private Long userId;
    private String nomSociete;
    private String email;
    private String message;

    public FournisseurRegisterResponse(Long fournisseurId, Long userId,
                                       String nomSociete, String email, String message) {
        this.fournisseurId = fournisseurId;
        this.userId = userId;
        this.nomSociete = nomSociete;
        this.email = email;
        this.message = message;
    }

    public Long getFournisseurId() { return fournisseurId; }
    public Long getUserId() { return userId; }
    public String getNomSociete() { return nomSociete; }
    public String getEmail() { return email; }
    public String getMessage() { return message; }
}