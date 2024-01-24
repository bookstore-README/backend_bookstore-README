package com.bookstore.readme.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    private static final long ACCESS_TOKEN_EXPIRED_TIME = 1000L * 60 * 60; // 1시간
    private static final long REFRESH_TOKEN_EXPIRED_TIME = 1000L * 60L * 60L * 24L * 7L; // 7일

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    private Key getSecretKey() {
        byte[] keyBytes = jwtSecretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(String email, List<String> roles, long expiredTime) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", roles);
        Date date = new Date();

        Key secretKey = getSecretKey();

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + expiredTime))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String createAccessToken(String email, List<String> roles) {
        return createToken(email, roles, ACCESS_TOKEN_EXPIRED_TIME);
    }

    public String createRefreshToken(String email, List<String> roles) {
        return createToken(email, roles, REFRESH_TOKEN_EXPIRED_TIME);
    }

    public String extractLoginEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Date extractExpiredTime(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

}
