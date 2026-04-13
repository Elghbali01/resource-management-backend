package com.university.fst.resourcemanagement.dto;

import java.math.BigDecimal;

public class ChefBudgetResponse {

    private Long departementId;
    private String departementNom;
    private BigDecimal budget;

    public ChefBudgetResponse(Long departementId, String departementNom, BigDecimal budget) {
        this.departementId = departementId;
        this.departementNom = departementNom;
        this.budget = budget;
    }

    public Long getDepartementId() {
        return departementId;
    }

    public String getDepartementNom() {
        return departementNom;
    }

    public BigDecimal getBudget() {
        return budget;
    }
}
