package com.bookstore.readme.common.security.handler;

import com.bookstore.readme.common.jwt.JwtTokenService;
import com.bookstore.readme.domain.member.repository.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class SignInSuccessHanlder extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenService jwtTokenService;
    private final MemberRepository memberRepository;

    private static final String AUTHENTICATION = "Authentication";
    private static final String PREFIX_BEARER = "Bearer ";
    private static final String EXPIRE = "Expire ";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String email = extractUsername(authentication);
        String accessToken = jwtTokenService.createAccessToken(email);
        String refreshToken = jwtTokenService.createRefreshToken(email);

        Cookie cookie = new Cookie("refreshToken", refreshToken);

        response.setHeader(AUTHENTICATION, PREFIX_BEARER + accessToken);
        response.addCookie(cookie);

        log.info("로그인 성공. 이메일: {}", email);
        log.info("로그인 성공. AccessToken : {}", accessToken);
    }

    private String extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
