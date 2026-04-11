package com.university.fst.resourcemanagement.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Email existant — fournisseur
    // ──────────────────────────────────────────────────────────────────────────
    @Async
    public void sendWelcomeEmailToFournisseur(String toEmail,
                                              String nomSociete,
                                              String plainPassword) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("gestionressourcefst@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Bienvenue sur la plateforme – Inscription confirmée");
            helper.setText(buildWelcomeHtml(nomSociete, toEmail, plainPassword), true);
            mailSender.send(message);
            System.out.println("✅ Email fournisseur envoyé à : " + toEmail);
        } catch (MessagingException e) {
            System.err.println("❌ MessagingException pour " + toEmail + " : " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("❌ Erreur inattendue email à " + toEmail + " : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Nouvel email — utilisateur interne (enseignant, chef, technicien, etc.)
    // ──────────────────────────────────────────────────────────────────────────
    @Async
    public void sendWelcomeEmailToUser(String toEmail,
                                       String nom,
                                       String prenom,
                                       String role,
                                       String plainPassword) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("gestionressourcefst@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Bienvenue sur la plateforme FST – Votre compte a été créé");
            helper.setText(buildUserWelcomeHtml(nom, prenom, role, toEmail, plainPassword), true);
            mailSender.send(message);
            System.out.println("✅ Email utilisateur envoyé à : " + toEmail);
        } catch (MessagingException e) {
            System.err.println("❌ MessagingException pour " + toEmail + " : " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("❌ Erreur inattendue email à " + toEmail + " : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // HTML fournisseur (existant, inchangé)
    // ──────────────────────────────────────────────────────────────────────────
    private String buildWelcomeHtml(String nomSociete, String email, String password) {
        return "<!DOCTYPE html>"
                + "<html lang='fr'><head><meta charset='UTF-8'></head>"
                + "<body style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;'>"
                + "<div style='max-width: 600px; margin: auto; background: #ffffff;"
                + "border-radius: 8px; padding: 30px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);'>"
                + "<h2 style='color: #2c3e50;'>&#127881; F&eacute;licitations, <strong>" + nomSociete + "</strong> !</h2>"
                + "<p style='color: #555;'>Votre inscription sur la plateforme de gestion des ressources de la FST a &eacute;t&eacute; "
                + "<strong>valid&eacute;e avec succ&egrave;s</strong>.</p>"
                + "<p style='color: #555;'>Voici vos identifiants de connexion :</p>"
                + "<table style='border-collapse: collapse; width: 100%; margin: 16px 0;'>"
                + "<tr><td style='padding: 10px; background: #ecf0f1; font-weight: bold;'>Email</td>"
                + "<td style='padding: 10px; background: #ecf0f1;'>" + email + "</td></tr>"
                + "<tr><td style='padding: 10px; font-weight: bold;'>Mot de passe</td>"
                + "<td style='padding: 10px;'>" + password + "</td></tr></table>"
                + "<p style='color: #e74c3c; font-size: 13px;'>&#9888;&#65039; Pour des raisons de s&eacute;curit&eacute;, "
                + "nous vous recommandons de changer votre mot de passe d&egrave;s votre premi&egrave;re connexion.</p>"
                + "<hr style='border: none; border-top: 1px solid #eee; margin: 24px 0;'>"
                + "<p style='color: #999; font-size: 12px;'>Cet email a &eacute;t&eacute; envoy&eacute; automatiquement. Merci de ne pas y r&eacute;pondre.</p>"
                + "</div></body></html>";
    }

    // ──────────────────────────────────────────────────────────────────────────
    // HTML utilisateur interne
    // ──────────────────────────────────────────────────────────────────────────
    private String buildUserWelcomeHtml(String nom, String prenom, String role,
                                        String email, String password) {
        String roleLabel = translateRole(role);
        return "<!DOCTYPE html>"
                + "<html lang='fr'><head><meta charset='UTF-8'></head>"
                + "<body style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;'>"
                + "<div style='max-width: 600px; margin: auto; background: #ffffff;"
                + "border-radius: 8px; padding: 30px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);'>"
                + "<h2 style='color: #2c3e50;'>&#128075; Bienvenue, <strong>" + prenom + " " + nom + "</strong> !</h2>"
                + "<p style='color: #555;'>Un compte a &eacute;t&eacute; cr&eacute;&eacute; pour vous sur la plateforme "
                + "de gestion des ressources de la <strong>FST</strong>.</p>"
                + "<p style='color: #555;'>Votre r&ocirc;le : <strong style='color:#2980b9;'>" + roleLabel + "</strong></p>"
                + "<p style='color: #555;'>Voici vos identifiants de connexion :</p>"
                + "<table style='border-collapse: collapse; width: 100%; margin: 16px 0;'>"
                + "<tr><td style='padding: 10px; background: #ecf0f1; font-weight: bold;'>Email</td>"
                + "<td style='padding: 10px; background: #ecf0f1;'>" + email + "</td></tr>"
                + "<tr><td style='padding: 10px; font-weight: bold;'>Mot de passe temporaire</td>"
                + "<td style='padding: 10px;'>" + password + "</td></tr></table>"
                + "<p style='color: #e74c3c; font-size: 13px;'>&#9888;&#65039; Ce mot de passe a &eacute;t&eacute; g&eacute;n&eacute;r&eacute; "
                + "automatiquement. Veuillez le changer d&egrave;s votre premi&egrave;re connexion.</p>"
                + "<hr style='border: none; border-top: 1px solid #eee; margin: 24px 0;'>"
                + "<p style='color: #999; font-size: 12px;'>Cet email a &eacute;t&eacute; envoy&eacute; automatiquement. Merci de ne pas y r&eacute;pondre.</p>"
                + "</div></body></html>";
    }

    private String translateRole(String role) {
        return switch (role) {
            case "ENSEIGNANT"          -> "Enseignant";
            case "CHEF_DEPARTEMENT"    -> "Chef de D&eacute;partement";
            case "RESPONSABLE_RESOURCE"-> "Responsable des Ressources";
            case "TECHNICIEN"          -> "Technicien de Maintenance";
            default                    -> role;
        };
    }
}