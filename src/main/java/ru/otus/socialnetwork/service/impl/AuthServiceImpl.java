package ru.otus.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.socialnetwork.exception.UsernameAlreadyInUseException;
import ru.otus.socialnetwork.model.dto.AuthRequest;
import ru.otus.socialnetwork.model.dto.AuthResponse;
import ru.otus.socialnetwork.model.entity.UserEntity;
import ru.otus.socialnetwork.repository.UserRepository;
import ru.otus.socialnetwork.service.AuthService;
import ru.otus.socialnetwork.service.JwtService;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsManager userDetailsManager;
    private final AuthenticationProvider authenticationProvider;

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        UserDetails userDetails = userDetailsManager.loadUserByUsername(authRequest.getUsername());

        UserEntity userEntity = userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new AccessDeniedException("User with username %s not found".formatted(authRequest.getUsername())));

        authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(userDetails, authRequest.getPassword()));

        return jwtService.getToken(userEntity);
    }

    @Override
    @Transactional
    public AuthResponse register(AuthRequest authRequest) {
        if (userDetailsManager.userExists(authRequest.getUsername())) {
            throw new UsernameAlreadyInUseException(authRequest.getUsername());
        }

        UserEntity userEntity = UserEntity.builder()
                .username(authRequest.getUsername())
                .build();
        userRepository.save(userEntity);

        UserDetails user = User.builder()
                .username(authRequest.getUsername())
                .password(passwordEncoder.encode(authRequest.getPassword()))
                .build();

        userDetailsManager.createUser(user);
        return jwtService.getToken(userEntity);
    }
}
