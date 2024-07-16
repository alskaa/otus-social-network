package ru.otus.socialnetwork.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.socialnetwork.model.dto.AuthResponse;
import ru.otus.socialnetwork.model.entity.UserEntity;
import ru.otus.socialnetwork.service.JwtService;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {

    private final SecretKey secretKey;

    public JwtServiceImpl(@Value("${token.signing.key}") String key) {
        this.secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }


    @Override
    public AuthResponse getToken(UserEntity user) {
        Map<String, Object> tokenData = new HashMap<>();

        tokenData.put("id", user.getId());
        tokenData.put("token_create_date", new Date().getTime());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);

        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.expiration(calendar.getTime());
        jwtBuilder.claims(tokenData);
        jwtBuilder.subject(user.getUsername());

        String token = jwtBuilder.signWith(secretKey).compact();
        return new AuthResponse(token);
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = Jwts.parser()
                    .decryptWith(secretKey)
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            if (claims.getExpiration().before(new Date())) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public String extractUsername(String token) {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .decryptWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload().getSubject();
    }

}
