package com.his.admin.config;

import com.his.admin.security.JwtAuthConverter;
import com.his.admin.service.AdminUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableMethodSecurity // Enables method-level security annotations like @PreAuthorize, @Secured
public class SecurityConfig {

    private final AdminUserDetailsService userDetailsService;

    // Constructor injection for custom UserDetailsService
    public SecurityConfig(AdminUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Disable CSRF for APIs (since we use JWT, not sessions)
                .authorizeHttpRequests(auth -> auth

                        // Account Management

                        // Account creation: only authenticated users can reach service layer
                        .requestMatchers("/account/create").authenticated()
                        // Role assignment: restricted to SUPER_ADMIN
                        .requestMatchers("/account/assign-role").hasRole("SUPER_ADMIN")
                        // Deactivation: allowed for SUPER_ADMIN and ADMIN
                        .requestMatchers("/account/deactivate/**").hasAnyRole("SUPER_ADMIN", "ADMIN")
                        // View accounts: allowed for all roles (SUPER_ADMIN, ADMIN, CASE_WORKER)
                        .requestMatchers("/account/all").hasAnyRole("SUPER_ADMIN", "ADMIN", "CASE_WORKER")



                        // Plan Management
                        .requestMatchers("/plan/create").hasRole("ADMIN")
                        .requestMatchers("/plan/update/**").hasRole("ADMIN")
                        .requestMatchers("/plan/delete/**").hasRole("ADMIN")
                        .requestMatchers("/plan/deactivate/**").hasRole("ADMIN")

                        // View APIs (open to all roles)
                        .requestMatchers("/plan/all").hasAnyRole("SUPER_ADMIN", "ADMIN", "CASE_WORKER")
                        .requestMatchers("/plan/active").hasAnyRole("SUPER_ADMIN", "ADMIN", "CASE_WORKER")
                        .requestMatchers("/plan/search").hasAnyRole("SUPER_ADMIN", "ADMIN", "CASE_WORKER")
                        .requestMatchers("/plan/expiring").hasAnyRole("SUPER_ADMIN", "ADMIN", "CASE_WORKER")
                        .requestMatchers("/plan/audit/**").hasAnyRole("SUPER_ADMIN", "ADMIN")

                        // Any other request must be authenticated
                        .anyRequest().authenticated()
                )


                // Configure JWT-based authentication with custom converter
                .oauth2ResourceServer(oauth -> oauth.jwt(jwt -> jwt.jwtAuthenticationConverter(new JwtAuthConverter())));
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        // Secret key used to validate JWT tokens (HS256 algorithm)
        String secretKey = "mysecretkeymysecretkeymysecretkey";
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);   // correct
        provider.setPasswordEncoder(passwordEncoder());       // correct
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt encoder for hashing passwords securely
        return new BCryptPasswordEncoder();
    }
}
