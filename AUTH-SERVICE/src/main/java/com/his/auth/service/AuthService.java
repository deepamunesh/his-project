package com.his.auth.service;

import com.his.auth.dto.AuthResponse;
import com.his.auth.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.his.auth.security.JwtUtil;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager manager;
    private final JwtUtil jwtUtil;

    public AuthResponse login(LoginRequest request) {
        // Authenticate against DB
        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            // Extract role from authenticated principal
            String role = authentication.getAuthorities()
                                        .iterator()
                                        .next()
                                        .getAuthority()
                                        .replace("ROLE_", "");

            // Generate token with username + role
            String token = jwtUtil.generateToken(request.getUsername(), role);
            return new AuthResponse(token);
        }

        throw new RuntimeException("Invalid credentials");
    }
}
