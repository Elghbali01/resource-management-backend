package com.university.fst.resourcemanagement.service;

import com.university.fst.resourcemanagement.dto.LoginRequest;
import com.university.fst.resourcemanagement.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}