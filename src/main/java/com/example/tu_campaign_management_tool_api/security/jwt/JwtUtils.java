package com.example.tu_campaign_management_tool_api.security.jwt;

import java.nio.charset.MalformedInputException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.tu_campaign_management_tool_api.security.services.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    protected String jwtSecret = System.getProperty("tu.app.jwtSecret");

    @Value("${tu.app.jwtExpirationMs}")
    protected int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userPrincipal.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public List<String> getRolesFromJwtToken(String token) {
        return (List<String>) Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody().get("roles");
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (Exception e) {
            getMapExceptionMessages().entrySet().stream()
                    .filter(entry -> entry.getKey().isInstance(e))
                    .findFirst()
                    .ifPresent(entry -> logger.error("{}: {}", entry.getValue(), e.getMessage()));
        }

        return false;
    }

    private Map<Class<? extends Exception>, String> getMapExceptionMessages() {
        return new HashMap<>() {{
            put(MalformedJwtException.class, "Invalid JWT Token");
            put(ExpiredJwtException.class, "JWT token is expired ");
            put(UnsupportedJwtException.class, "JWT token is unsupported");
            put(IllegalArgumentException.class, "JWT claims string is empty");
        }};
    }
}
