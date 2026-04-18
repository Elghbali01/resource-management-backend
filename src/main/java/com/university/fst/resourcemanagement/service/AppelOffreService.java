package com.university.fst.resourcemanagement.service;

import com.university.fst.resourcemanagement.dto.AppelOffreResponse;
import com.university.fst.resourcemanagement.dto.CreateAppelOffreRequest;

import java.util.List;

public interface AppelOffreService {

    AppelOffreResponse creerAppelOffre(Long responsableUserId, CreateAppelOffreRequest request);

    List<AppelOffreResponse> listerAppelsOffreResponsable();

    AppelOffreResponse getAppelOffreResponsable(Long appelOffreId);

    List<AppelOffreResponse> listerAppelsOffreActuelsPourFournisseur();

    AppelOffreResponse getAppelOffrePourFournisseur(Long appelOffreId);
}