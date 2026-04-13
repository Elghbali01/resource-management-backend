package com.university.fst.resourcemanagement.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class AjouterBudgetRequest {

    @NotNull(message = "Le montant est obligatoire")
    @DecimalMin(value = "0.01", message = "Le montant doit être supérieur à 0")
    private BigDecimal montant;

    public AjouterBudgetRequest() {}

    public BigDecimal getMontant()             { return montant; }
    public void       setMontant(BigDecimal m) { this.montant = m; }
}