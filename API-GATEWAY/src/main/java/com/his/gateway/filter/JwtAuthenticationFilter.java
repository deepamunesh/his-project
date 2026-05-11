package com.his.gateway.filter;

import com.his.gateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtAuthenticationFilter implements GlobalFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // Allow /auth endpoints without token
        if (path.startsWith("/auth")) {
            return chain.filter(exchange);
        }

        // Check Authorization header
        String header = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = header.substring(7);

        try {
            // Validate JWT signature/expiration first
            if (!jwtUtil.validateToken(token)) {
                System.out.println("JWT INVALID OR EXPIRED");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            Claims claims = jwtUtil.extractAllClaims(token);
            String username = claims.getSubject();
            String role = claims.get("role", String.class);
            if (role == null || role.isBlank()) {
                role = "USER";
            }

            System.out.println("Token valid for user: " + username + " with role: " + role);

            Authentication auth = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    List.of(new SimpleGrantedAuthority("ROLE_" + role))
            );

            return chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));

        } catch (Exception e) {
            System.out.println("JWT ERROR: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }
}


















//package com.his.gateway.filter;
//
//import com.his.gateway.util.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//
//@Component
//public class JwtAuthenticationFilter implements GlobalFilter {
//    @Autowired
//    private JwtUtil jwtUtil;
//
//
//    public Mono<Void> filter(ServerWebExchange exchange,
//                             GatewayFilterChain chain){
//
//    String path = exchange.getRequest().getURI().getPath();
//
//    if(path.contains("/auth/login"))
//
//    {
//        return chain.filter(exchange);
//    }
//
//    String header = exchange.getRequest().getHeaders().getFirst("Authorization");
//        if(header == null || !header.startsWith("Bearer "))
//
//    {
//        exchange.getResponse().setStatusCode(
//                HttpStatus.UNAUTHORIZED);
//
//        return exchange.getResponse().setComplete();
//    }
//
//    String token = header.substring(7);
//
//        try {
//
//            String username = jwtUtil.extractUsername(token);
//            System.out.println("Token valid for user: " + username);
//
//        }
//        catch(Exception e) {
//
//            System.out.println("JWT ERROR: " + e.getMessage());
//
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
//        return chain.filter(exchange);
//    }
//}
//
//
//
//
//
//
//
//
