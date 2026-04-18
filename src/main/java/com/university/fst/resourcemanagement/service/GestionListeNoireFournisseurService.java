package com.university.fst.resourcemanagement.service;

import com.university.fst.resourcemanagement.dto.BlacklistFournisseurRequest;
import com.university.fst.resourcemanagement.dto.FournisseurAdminResponse;

import java.util.List;

public interface GestionListeNoireFournisseurService {

    List<FournisseurAdminResponse> listerFournisseurs();

    FournisseurAdminResponse blacklisterFournisseur(Long fournisseurId, BlacklistFournisseurRequest request);

    FournisseurAdminResponse retirerListeNoire(Long fournisseurId);
}