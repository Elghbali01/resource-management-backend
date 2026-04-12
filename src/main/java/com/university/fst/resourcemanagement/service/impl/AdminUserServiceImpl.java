package com.university.fst.resourcemanagement.service.impl;

import com.university.fst.resourcemanagement.dto.CreateUserRequest;
import com.university.fst.resourcemanagement.dto.UpdateUserRequest;
import com.university.fst.resourcemanagement.dto.UserListResponse;
import com.university.fst.resourcemanagement.email.EmailService;
import com.university.fst.resourcemanagement.entity.*;
import com.university.fst.resourcemanagement.enums.Role;
import com.university.fst.resourcemanagement.enums.Status;
import com.university.fst.resourcemanagement.repository.*;
import com.university.fst.resourcemanagement.service.AdminUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository               userRepository;
    private final EnseignantRepository         enseignantRepository;
    private final ChefDepartementRepository    chefDepartementRepository;
    private final TechnicienRepository         technicienRepository;
    private final ResponsableResourceRepository responsableResourceRepository;
    private final DepartementRepository        departementRepository;
    private final PasswordEncoder              passwordEncoder;
    private final EmailService                 emailService;
    private final FournisseurRepository        fournisseurRepository ;

    public AdminUserServiceImpl(
            UserRepository userRepository,
            EnseignantRepository enseignantRepository,
            ChefDepartementRepository chefDepartementRepository,
            TechnicienRepository technicienRepository,
            ResponsableResourceRepository responsableResourceRepository,
            DepartementRepository departementRepository,
            PasswordEncoder passwordEncoder,
            EmailService emailService ,
            FournisseurRepository fournisseurRepository) {
        this.userRepository                = userRepository;
        this.enseignantRepository          = enseignantRepository;
        this.chefDepartementRepository     = chefDepartementRepository;
        this.technicienRepository          = technicienRepository;
        this.responsableResourceRepository = responsableResourceRepository;
        this.departementRepository         = departementRepository;
        this.passwordEncoder               = passwordEncoder;
        this.emailService                  = emailService;
        this.fournisseurRepository =fournisseurRepository;
    }

    // ──────────────────────────────────────────────────────────────────────────
    // CRÉER UN UTILISATEUR
    // ──────────────────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public UserListResponse creerUtilisateur(CreateUserRequest request) {

        // 1. Unicité email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Un compte avec cet email existe déjà");
        }

        // 2. Validation rôle autorisé (on n'accepte pas ADMIN ni FOURNISSEUR ici)
        Role role = request.getRole();
        if (role == Role.ADMIN || role == Role.FOURNISSEUR) {
            throw new RuntimeException("Ce rôle ne peut pas être créé via cette interface");
        }

        // 3. Validation département pour les rôles qui en ont besoin
        if ((role == Role.ENSEIGNANT || role == Role.CHEF_DEPARTEMENT)
                && request.getDepartementId() == null) {
            throw new RuntimeException("Le département est obligatoire pour ce rôle");
        }

        // 4. Génération du mot de passe aléatoire (8 premiers chars d'un UUID)
        String plainPassword = UUID.randomUUID().toString().replace("-", "").substring(0, 10);

        // 5. Création de l'entité User
        User user = new User();
        user.setNom(request.getNom());
        user.setPrenom(request.getPrenom());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(plainPassword)); // stocké haché
        user.setRole(role);
        user.setStatus(Status.ACTIVE);
        User savedUser = userRepository.save(user);

        // 6. Création de l'entrée dans la table spécifique au rôle
        String departementNom = null;
        switch (role) {
            case ENSEIGNANT -> {
                Departement dept = getDepartement(request.getDepartementId());
                departementNom = dept.getNom();
                Enseignant enseignant = new Enseignant();
                enseignant.setUser(savedUser);
                enseignant.setDepartement(dept);
                enseignantRepository.save(enseignant);
            }
            case CHEF_DEPARTEMENT -> {
                Departement dept = getDepartement(request.getDepartementId());
                departementNom = dept.getNom();

                // Vérifier qu'il n'y a pas déjà un chef pour ce département
                if (chefDepartementRepository.existsByDepartementId(dept.getId())) {
                    throw new RuntimeException(
                            "Le département '" + dept.getNom() + "' a déjà un chef de département");
                }

                // Le chef est aussi enseignant => on crée les 2 entrées
                Enseignant enseignant = new Enseignant();
                enseignant.setUser(savedUser);
                enseignant.setDepartement(dept);
                Enseignant savedEnseignant = enseignantRepository.save(enseignant);

                ChefDepartement chef = new ChefDepartement();
                chef.setUser(savedUser);
                chef.setEnseignant(savedEnseignant);
                chef.setDepartement(dept);
                chefDepartementRepository.save(chef);
            }
            case TECHNICIEN -> {
                Technicien technicien = new Technicien();
                technicien.setUser(savedUser);
                technicienRepository.save(technicien);
            }
            case RESPONSABLE_RESOURCE -> {
                ResponsableResource resp = new ResponsableResource();
                resp.setUser(savedUser);
                responsableResourceRepository.save(resp);
            }
            default -> throw new RuntimeException("Rôle non géré : " + role);
        }

        // 7. Envoi email avec le mot de passe en clair (avant encodage)
        emailService.sendWelcomeEmailToUser(
                savedUser.getEmail(),
                savedUser.getNom(),
                savedUser.getPrenom(),
                role.name(),
                plainPassword
        );

        return new UserListResponse(
                savedUser.getId(),
                savedUser.getNom(),
                savedUser.getPrenom(),
                savedUser.getEmail(),
                role.name(),
                savedUser.getStatus().name(),
                departementNom
        );
    }

    // ──────────────────────────────────────────────────────────────────────────
    // LISTER LES UTILISATEURS INTERNES
    // ──────────────────────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<UserListResponse> listerUtilisateurs() {
        return userRepository.findAll().stream()
                .filter(u -> u.getRole() != Role.ADMIN)
                .map(u -> new UserListResponse(
                        u.getId(),
                        u.getNom(),
                        u.getPrenom(),
                        u.getEmail(),
                        u.getRole().name(),
                        u.getStatus().name(),
                        resolveDepartementNom(u)
                ))
                .collect(Collectors.toList());
    }

    // ──────────────────────────────────────────────────────────────────────────
    // MODIFIER UN UTILISATEUR
    // ──────────────────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public UserListResponse modifierUtilisateur(Long id, UpdateUserRequest request) {
        User user = getUser(id);

        // Si changement d'email, vérifier unicité
        if (!user.getEmail().equals(request.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Cet email est déjà utilisé par un autre compte");
        }

        user.setNom(request.getNom());
        user.setPrenom(request.getPrenom());
        user.setEmail(request.getEmail());
        User saved = userRepository.save(user);

        return new UserListResponse(
                saved.getId(), saved.getNom(), saved.getPrenom(),
                saved.getEmail(), saved.getRole().name(),
                saved.getStatus().name(), resolveDepartementNom(saved)
        );
    }

    // ──────────────────────────────────────────────────────────────────────────
    // BLOQUER / DÉBLOQUER (toggle ACTIVE <-> INACTIVE)
    // ──────────────────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public UserListResponse toggleStatut(Long id) {
        User user = getUser(id);
        user.setStatus(user.getStatus() == Status.ACTIVE ? Status.INACTIVE : Status.ACTIVE);
        User saved = userRepository.save(user);
        return new UserListResponse(
                saved.getId(), saved.getNom(), saved.getPrenom(),
                saved.getEmail(), saved.getRole().name(),
                saved.getStatus().name(), resolveDepartementNom(saved)
        );
    }

    // ──────────────────────────────────────────────────────────────────────────
    // SUPPRIMER UN UTILISATEUR
    // ──────────────────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public void supprimerUtilisateur(Long id) {
        User user = getUser(id);
        // Suppression de l'entrée dans la table de rôle avant le User
        switch (user.getRole()) {
            case ENSEIGNANT -> enseignantRepository.findByUserId(id)
                    .ifPresent(enseignantRepository::delete);
            case CHEF_DEPARTEMENT -> {
                // Supprimer chef (qui supprime aussi l'enseignant lié)
                chefDepartementRepository.deleteByUserId(id);
                enseignantRepository.findByUserId(id)
                        .ifPresent(enseignantRepository::delete);
            }
            case TECHNICIEN          -> technicienRepository.deleteByUserId(id);
            case RESPONSABLE_RESOURCE-> responsableResourceRepository.deleteByUserId(id);
            case FOURNISSEUR -> fournisseurRepository.deleteByUserId(id);
            default -> { /* rien */ }
        }
        userRepository.delete(user);
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Helpers privés
    // ──────────────────────────────────────────────────────────────────────────

    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec l'id : " + id));
    }

    private Departement getDepartement(Long id) {
        return departementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Département introuvable avec l'id : " + id));
    }

    /** Résout le nom du département pour l'affichage (null si sans département). */
    private String resolveDepartementNom(User user) {
        if (user.getRole() == Role.ENSEIGNANT || user.getRole() == Role.CHEF_DEPARTEMENT) {
            return enseignantRepository.findByUserId(user.getId())
                    .map(e -> e.getDepartement().getNom())
                    .orElse(null);
        }
        return null;
    }
}