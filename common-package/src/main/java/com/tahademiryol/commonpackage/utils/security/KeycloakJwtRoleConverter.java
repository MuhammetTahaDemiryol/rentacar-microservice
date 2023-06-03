package com.tahademiryol.commonpackage.utils.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakJwtRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    private final static String ROLE_PREFIX = "ROLE_";

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        return extractRoles(jwt);
    }

    private Collection<GrantedAuthority> extractRoles(Jwt jwt) {
        var claims = jwt.getClaims();
        var realmAccess = (Map<String, Object>) claims.getOrDefault("realm_access", Collections.emptyMap());
        var roles = (List<String>) realmAccess.getOrDefault("roles", Collections.emptyList());

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role))
                .collect(Collectors.toList());
    }
}
