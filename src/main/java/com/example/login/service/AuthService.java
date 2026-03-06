package com.example.login.service;

import com.example.login.dto.AuthRequest;
import com.example.login.dto.AuthResponse;
import com.example.login.dto.RegisterRequest;

public interface AuthService {
    AuthResponse login(AuthRequest authRequest);
    String register(RegisterRequest registerRequest);
}
