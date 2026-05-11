package com.his.dc.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthConverter.class);

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        log.debug("JWT Claims: {}", jwt.getClaims());

        // Extract roles from claims
        List<String> roles = jwt.getClaimAsStringList("roles");
        if (roles == null || roles.isEmpty()) {
            String singleRole = jwt.getClaim("role");
            roles = (singleRole != null) ? List.of(singleRole) : List.of();
        }

        log.debug("Extracted roles: {}", roles);

        // Map roles to Spring Security authorities
        Collection<GrantedAuthority> authorities = roles.stream()
                .map(role -> {
                    String mappedRole = role.startsWith("ROLE_") ? role : "ROLE_" + role;
                    log.debug("Mapping role {} to authority {}", role, mappedRole);
                    return new SimpleGrantedAuthority(mappedRole);
                })
                .collect(Collectors.toList());

        log.debug("Final authorities: {}", authorities);

        // JwtAuthenticationToken will now be created with subject and authorities
        return new JwtAuthenticationToken(jwt, authorities, jwt.getSubject());
    }
}
