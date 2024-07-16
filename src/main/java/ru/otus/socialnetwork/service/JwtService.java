package ru.otus.socialnetwork.service;

import ru.otus.socialnetwork.model.dto.AuthResponse;
import ru.otus.socialnetwork.model.entity.UserEntity;

public interface JwtService {
    AuthResponse getToken(UserEntity user);
    boolean isTokenValid(String token);
    String extractUsername(String token);
}
