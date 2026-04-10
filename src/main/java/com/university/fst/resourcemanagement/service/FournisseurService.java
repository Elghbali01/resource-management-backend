package com.university.fst.resourcemanagement.service;

import com.university.fst.resourcemanagement.dto.FournisseurRegisterRequest;
import com.university.fst.resourcemanagement.dto.FournisseurRegisterResponse;

public interface FournisseurService {
    FournisseurRegisterResponse inscrire(FournisseurRegisterRequest request);
}