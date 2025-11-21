package com.hospital.gateway_service.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtService {
  private static final String BEARER_PREFIX = "Bearer ";
  private final SecretKey signingKey;

  public JwtService(@Value("${jwt.secret}") String secretKeyString) {
    this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKeyString));
  }

  public Optional<String> getToken(ServerHttpRequest request) {
    final String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
      return Optional.of(authHeader.substring(BEARER_PREFIX.length()));
    }
    return Optional.empty();
  }

  private Claims extractAllClaims(String token) {
    if (token == null || token.isEmpty()) {
      return null;
    }
    try {
      return Jwts.parser()
          .verifyWith(signingKey)
          .build()
          .parseSignedClaims(token)
          .getPayload();
    } catch (JwtException | IllegalArgumentException e) {
      log.warn("La validacion del JWT fallo: {}", e.getMessage());
      return null;
    }
  }

  public boolean isTokenValid(String token) {
    Claims claims = extractAllClaims(token);

    if (claims == null) {
      return false;
    }

    try {
      return claims.getExpiration().after(new Date());
    } catch (Exception e) {
      log.error("Error al verificar la expiración del token: {}", e.getMessage());
      return false;
    }
  }

  public List<String> getAuthorities(String token) {
    Claims claims = extractAllClaims(token);
    if (claims == null) {
      return Collections.emptyList();
    }

    Object authorities = claims.get("authorities");
    if (authorities instanceof List<?>) {
      List<?> authoritiesList = (List<?>) authorities;
      List<String> stringAuthoritiesList = new ArrayList<>();
      for (Object authority : authoritiesList) {
        if (authority instanceof String) {
          stringAuthoritiesList.add((String) authority);
        } else {
          log.warn("La lista de autoridades contiene un tipo no esperado.");
          return Collections.emptyList();
        }
      }
      return stringAuthoritiesList;
    }
    return Collections.emptyList();
  }

  public String getSubject(String token) {
    Claims claims = extractAllClaims(token);
    return claims != null ? claims.getSubject() : null;
  }

}