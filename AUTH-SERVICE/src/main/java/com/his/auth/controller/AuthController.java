package com.his.auth.controller;

import com.his.auth.dto.AuthResponse;
import com.his.auth.dto.RegisterRequest;
import com.his.auth.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import com.his.auth.dto.LoginRequest;
import com.his.auth.security.JwtUtil;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RegisterService registerService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        String result = registerService.registerUser(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {

            System.out.println("UserName::::: "+request.getUsername());
            System.out.println("Password::::: "+request.getPassword());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            if (authentication.isAuthenticated()) {
                // Extract actual role from authenticated principal
                String role = authentication.getAuthorities()
                        .iterator()
                        .next()
                        .getAuthority()
                        .replace("ROLE_", "");

                // Generate token with correct role
                String token = jwtUtil.generateToken(request.getUsername(), role);
                return ResponseEntity.ok(new AuthResponse(token));
            } else {
                return ResponseEntity.status(401).body("Invalid credentials");
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials: " + e.getMessage());
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader(name = "Authorization") String authorization) {
        String token = authorization.substring(7);
        boolean valid = jwtUtil.validateToken(token);
        if (!valid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }
        String username = jwtUtil.extractUsername(token);
        String role = jwtUtil.extractRole(token);
        return ResponseEntity.ok("Valid token for user: " + username + ", role: " + role);
    }

    @GetMapping("/token-info")
    public ResponseEntity<?> tokenInfo(@RequestHeader(name = "Authorization", required = false) String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Missing or invalid Authorization header (Bearer token required)");
        }
        String token = authorization.substring(7);
        try {
            var claims = jwtUtil.extractAllClaims(token);
            var info = Map.of(
                    "subject", claims.getSubject(),
                    "role", claims.get("role", String.class),
                    "issuedAt", claims.getIssuedAt(),
                    "expiration", claims.getExpiration(),
                    "valid", !jwtUtil.isTokenExpired(token)
            );
            return ResponseEntity.ok(info);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token: " + e.getMessage());
        }
    }
}
