package com.university.fst.resourcemanagement.service;

import com.university.fst.resourcemanagement.dto.CreateUserRequest;
import com.university.fst.resourcemanagement.dto.UpdateUserRequest;
import com.university.fst.resourcemanagement.dto.UserListResponse;

import java.util.List;

public interface AdminUserService {

    /** Crée un utilisateur (+ entrée dans sa table de rôle) et envoie un email. */
    UserListResponse creerUtilisateur(CreateUserRequest request);

    /** Retourne tous les utilisateurs internes (hors ADMIN et FOURNISSEUR). */
    List<UserListResponse> listerUtilisateurs();

    /** Met à jour nom / prénom / email d'un utilisateur. */
    UserListResponse modifierUtilisateur(Long id, UpdateUserRequest request);

    /** Bloque ou débloque un utilisateur (toggle ACTIVE <-> INACTIVE). */
    UserListResponse toggleStatut(Long id);

    /** Supprime un utilisateur et son entrée de rôle associée. */
    void supprimerUtilisateur(Long id);
}