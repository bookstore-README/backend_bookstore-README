package com.bookstore.readme.common.security.handler;

import com.bookstore.readme.common.jwt.JwtTokenService;
import com.bookstore.readme.domain.member.model.Member;
import com.bookstore.readme.domain.member.repository.MemberRepository;
import com.bookstore.readme.domain.member.response.MemberResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class SignInSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenService jwtTokenService;
    private final MemberRepository memberRepository;

    private static final String AUTHENTICATION = "Authentication";
    private static final String PREFIX_BEARER = "Bearer ";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String email = extractUsername(authentication);
        String accessToken = jwtTokenService.createAccessToken(email);
        String refreshToken = jwtTokenService.createRefreshToken(email);

        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다.")
        );

        Map<String, Object> mem = new HashMap<>();
        mem.put("email", member.getEmail());
        mem.put("memberId", member.getId());
        mem.put(AUTHENTICATION, PREFIX_BEARER + accessToken);

        // refresh token update
        member.updateRefreshToken(refreshToken);

        memberRepository.save(member);

        Cookie cookie = new Cookie("RefreshToken", refreshToken);

        ObjectMapper objectMapper = new ObjectMapper();

        // response.setHeader(AUTHENTICATION, PREFIX_BEARER + accessToken);
        response.addCookie(cookie);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        objectMapper.writeValue(response.getWriter(), MemberResponse.ok(mem));

        // log.info("로그인 성공. 이메일: {}", email);
        // log.info("로그인 성공. AccessToken : {}", accessToken);
        // log.info("로그인 성공. RefreshToken : {}", refreshToken);
    }

    private String extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
