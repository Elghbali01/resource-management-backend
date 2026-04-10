package com.university.fst.resourcemanagement.service.impl;

import com.university.fst.resourcemanagement.dto.FournisseurRegisterRequest;
import com.university.fst.resourcemanagement.dto.FournisseurRegisterResponse;
import com.university.fst.resourcemanagement.entity.Fournisseur;
import com.university.fst.resourcemanagement.entity.User;
import com.university.fst.resourcemanagement.enums.Role;
import com.university.fst.resourcemanagement.enums.Status;
import com.university.fst.resourcemanagement.repository.FournisseurRepository;
import com.university.fst.resourcemanagement.repository.UserRepository;
import com.university.fst.resourcemanagement.email.EmailService;
import com.university.fst.resourcemanagement.service.FournisseurService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FournisseurServiceImpl implements FournisseurService {

    private final UserRepository userRepository;
    private final FournisseurRepository fournisseurRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public FournisseurServiceImpl(UserRepository userRepository,
                                  FournisseurRepository fournisseurRepository,
                                  PasswordEncoder passwordEncoder,
                                  EmailService emailService) {
        this.userRepository = userRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public FournisseurRegisterResponse inscrire(FournisseurRegisterRequest request) {

        // 1. Vérification que les deux mots de passe correspondent
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Les mots de passe ne correspondent pas");
        }

        // 2. Vérification unicité email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Un compte avec cet email existe déjà");
        }

        // 3. Création du User (rôle FOURNISSEUR, statut ACTIVE par défaut)
        User user = new User();
        user.setNom(request.getNom());        // nom réel de la personne
        user.setPrenom(request.getPrenom());  // prénom réel de la personne
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.FOURNISSEUR);
        user.setStatus(Status.ACTIVE);

        User savedUser = userRepository.save(user);

        // 4. Création de l'entité Fournisseur liée au User
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setNomSociete(request.getNomSociete());
        fournisseur.setUser(savedUser);
        fournisseur.setBlacklisted(false);

        Fournisseur savedFournisseur = fournisseurRepository.save(fournisseur);

        // 5. Envoi de l'email de bienvenue avec le mot de passe en clair
        emailService.sendWelcomeEmailToFournisseur(
                request.getEmail(),
                request.getNomSociete(),
                request.getPassword()          // mot de passe en clair avant encodage
        );

        return new FournisseurRegisterResponse(
                savedFournisseur.getId(),
                savedUser.getId(),
                savedFournisseur.getNomSociete(),
                savedUser.getEmail(),
                "Inscription réussie. Un email de confirmation a été envoyé à " + request.getEmail()
        );
    }
}