package com.bookstore.readme.common.jwt;

import com.bookstore.readme.common.jwt.exception.JwtErrorException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final static long ACCESS_TOKEN_EXPIRED_TIME = 1000L * 60L * 60L * 24L; // 1일
            // 1000L * 60 * 30; // 30분
    private final static long REFRESH_TOKEN_EXPIRED_TIME = 1000L * 60L * 60L * 24L * 7L; // 7일
    private final static String AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";
    private final static String REFRESH_TOKEN = "RefreshToken";

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    private Key getSecretKey() {
        byte[] keyBytes = jwtSecretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(String email) {
        Date date = new Date();

        Claims claims = Jwts.claims()
                .setSubject("ACCESS_TOKEN")
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + ACCESS_TOKEN_EXPIRED_TIME));

        claims.put("email", email);

        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(claims)
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String createRefreshToken(String email) {
        Date date = new Date();

        Claims claims = Jwts.claims()
                .setSubject("REFRESH_TOKEN")
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + REFRESH_TOKEN_EXPIRED_TIME));

        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(claims)
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ","JWT");
        header.put("alg","HS512");
        return header;
    }

    /**
     * 헤더에서 RefreshToken 추출
     */
    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(REFRESH_TOKEN));
    }

    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        Cookie cookie = new Cookie("RefreshToken", refreshToken);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(AUTHORIZATION, accessToken);
        response.addCookie(cookie);
    }

    /**
     * 헤더에서 AccessToken 추출
     * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
     * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
     */
    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(AUTHORIZATION))
                .filter(accessToken -> accessToken.startsWith(TOKEN_PREFIX))
                .map(accessToken -> accessToken.replace(TOKEN_PREFIX, ""));
    }

    /**
     * AccessToken에서 Email 추출
     */
    public Optional<String> extractEmail(String token) {
        try {
            return Optional.ofNullable(Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody().get("email", String.class));
        } catch (Exception e) {
            log.error("액세스 토큰이 유효하지 않습니다.");
            return Optional.empty();
        }
    }

    // 토큰 복호화
    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public boolean isTokenValid(String token) throws JwtException {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            // log.info("잘못된 JWT 서명입니다.");
            throw new JwtErrorException(JwtErrorStatus.MALFORMED_JWT);
        } catch (ExpiredJwtException e) {
            // log.info("만료된 JWT 토큰입니다.");
            throw new JwtErrorException(JwtErrorStatus.EXPIRED_JWT);
        } catch (UnsupportedJwtException e) {
            // log.info("지원되지 않는 JWT 토큰입니다.");
            throw new JwtErrorException(JwtErrorStatus.UNSUPPORTED_JWT);
        } catch (IllegalArgumentException e) {
            // log.info("JWT 토큰이 잘못되었습니다.");
            throw new JwtErrorException(JwtErrorStatus.ILLEGAL_ARGUMENT);
        }
    }

    /**
     * 구글 JWT ID Token 디코딩
     */
    public String getBaseUrlToken(String token) {
        byte[] decode = Base64.getDecoder().decode(token);
        return new String(decode, StandardCharsets.UTF_8);
    }
}
