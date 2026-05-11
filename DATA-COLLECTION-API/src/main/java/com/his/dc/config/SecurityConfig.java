package com.his.dc.config;

import com.his.dc.security.JwtAuthConverter;
import com.his.dc.service.DcUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String secretKey;

    private final DcUserDetailsService userDetailsService;

    public SecurityConfig(DcUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Authentication endpoints open
                        .requestMatchers("/auth/**").permitAll()

                        // DC Module rules
                        .requestMatchers("/dc/create-case").hasRole("CASE_WORKER")
                        .requestMatchers("/dc/case/**").hasAnyRole("CASE_WORKER", "ADMIN")

                        // Any other request must be authenticated
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth -> oauth.jwt(jwt -> jwt.jwtAuthenticationConverter(new JwtAuthConverter())));

        return http.build();
    }


@Bean
public JwtDecoder jwtDecoder() {
    //String secretKey = secretKeyFromProperties(); // use @Value injection
    SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
    NimbusJwtDecoder decoder = NimbusJwtDecoder.withSecretKey(secretKeySpec).build();

    decoder.setJwtValidator(token -> {
        try {
            return OAuth2TokenValidatorResult.success();
        } catch (Exception e) {
            System.out.println("JWT validation failed: " + e.getMessage());
            return OAuth2TokenValidatorResult.failure(new OAuth2Error("invalid_token", e.getMessage(), null));
        }
    });

    return decoder;
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
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
