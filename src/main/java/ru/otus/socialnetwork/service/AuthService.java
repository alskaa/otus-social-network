package ru.otus.socialnetwork.service;

import ru.otus.socialnetwork.model.dto.AuthRequest;
import ru.otus.socialnetwork.model.dto.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthRequest authRequest);
    AuthResponse register(AuthRequest authRequest);
}
