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

    // Fix 3 : @Async => envoi en arrière-plan, plus de retard HTTP
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

            // Fix 2 : buildWelcomeHtml utilise la concaténation String classique
            // (évite les conflits % dans les text blocks Java)
            String htmlContent = buildWelcomeHtml(nomSociete, toEmail, plainPassword);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            System.out.println("✅ Email de bienvenue envoyé à : " + toEmail);

        } catch (MessagingException e) {
            // Erreur SMTP (mauvais host, port bloqué, auth refusée…)
            System.err.println("❌ MessagingException pour " + toEmail + " : " + e.getMessage());
            e.printStackTrace();

        } catch (Exception e) {
            // Fix 1 : capture les RuntimeException, IllegalArgumentException, etc.
            // (ex: erreur dans buildWelcomeHtml ou dans .formatted())
            System.err.println("❌ Erreur inattendue lors de l'envoi email à " + toEmail + " : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Fix 2 : String concaténée => plus de problème avec %% ou %s
    private String buildWelcomeHtml(String nomSociete, String email, String password) {
        return "<!DOCTYPE html>"
                + "<html lang='fr'>"
                + "<head><meta charset='UTF-8'></head>"
                + "<body style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;'>"
                + "<div style='max-width: 600px; margin: auto; background: #ffffff;"
                + "            border-radius: 8px; padding: 30px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);'>"
                + "<h2 style='color: #2c3e50;'>&#127881; F&eacute;licitations, <strong>"
                + nomSociete
                + "</strong> !</h2>"
                + "<p style='color: #555;'>"
                + "Votre inscription sur la plateforme de gestion des ressources de la FST a &eacute;t&eacute; "
                + "<strong>valid&eacute;e avec succ&egrave;s</strong>."
                + "</p>"
                + "<p style='color: #555;'>Voici vos identifiants de connexion :</p>"
                + "<table style='border-collapse: collapse; width: 100%; margin: 16px 0;'>"
                + "<tr>"
                + "<td style='padding: 10px; background: #ecf0f1; font-weight: bold;'>Email</td>"
                + "<td style='padding: 10px; background: #ecf0f1;'>" + email + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td style='padding: 10px; font-weight: bold;'>Mot de passe</td>"
                + "<td style='padding: 10px;'>" + password + "</td>"
                + "</tr>"
                + "</table>"
                + "<p style='color: #e74c3c; font-size: 13px;'>"
                + "&#9888;&#65039; Pour des raisons de s&eacute;curit&eacute;, nous vous recommandons "
                + "de changer votre mot de passe d&egrave;s votre premi&egrave;re connexion."
                + "</p>"
                + "<hr style='border: none; border-top: 1px solid #eee; margin: 24px 0;'>"
                + "<p style='color: #999; font-size: 12px;'>"
                + "Cet email a &eacute;t&eacute; envoy&eacute; automatiquement. Merci de ne pas y r&eacute;pondre."
                + "</p>"
                + "</div>"
                + "</body>"
                + "</html>";
    }
}