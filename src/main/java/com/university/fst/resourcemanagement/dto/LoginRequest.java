package com.university.fst.resourcemanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format email invalide")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;

    // Optionnel — envoyé seulement par le front après la popup
    // Valeurs possibles : "CHEF_DEPARTEMENT" ou "ENSEIGNANT"
    private String roleChoisi;

    public LoginRequest() {}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRoleChoisi() { return roleChoisi; }
    public void setRoleChoisi(String roleChoisi) { this.roleChoisi = roleChoisi; }
}