package com.guavapay.ms.auth.security;

import com.guavapay.ms.auth.model.JwtToken;
import com.guavapay.ms.auth.model.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class TokenProvider {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final int TOKEN_START_INDEX = 7;

    @Value("${application.security.authentication.jwt.secret}")
    private String secret;

    @Value("${application.security.authentication.jwt.token-validity-in-seconds}")
    private long tokenValidityInSeconds;

    private Key key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtToken createJwtToken(CustomUserPrincipal principal) {
        return JwtToken.builder()
                .accessToken(createToken(principal))
                .build();
    }

    private String createToken(CustomUserPrincipal principal) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + tokenValidityInSeconds * 1000);

        return Jwts.builder()
                .claim(TokenKey.TOKEN_TYPE, TokenType.ACCESS)
                .claim(TokenKey.USER_ID, principal.getId())
                .claim(TokenKey.USERNAME, principal.getUsername())
                .claim(TokenKey.FULL_NAME, principal.getFullName())
                .claim(TokenKey.EMAIL, principal.getEmail())
                .claim(TokenKey.PHONE, principal.getMobile())
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public String getUserName(String token) {
        return getClaims(token).get(TokenKey.USERNAME, String.class);
    }

    public Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("Fail on parsing token: {}", token);
            throw e;
        }
    }
}
