package com.university.fst.resourcemanagement.service;

import com.university.fst.resourcemanagement.dto.AffectationPrevueResponse;
import com.university.fst.resourcemanagement.dto.DemandeCollecteResponse;

import java.util.List;

public interface ResponsableTransmissionService {

    List<DemandeCollecteResponse> listerDemandesTransmises();

    List<AffectationPrevueResponse> listerAffectationsPrevues(Long demandeId);
}