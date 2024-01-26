package com.bookstore.readme.common.security.filter;

import com.bookstore.readme.common.jwt.JwtTokenProvider;
import com.bookstore.readme.domain.member.dto.MemberLoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.List;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/member/sign-in", "POST");

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    private static final String AUTHENTICATION = "Authentication ";
    private static final String PREFIX_BEARER = "Bearer ";
    private static final String EXPIRE = "Expire ";

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String method = request.getMethod();

        if(!method.equals("POST"))
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());

        MemberLoginDto memberLoginDto = null;

        try{
            ServletInputStream inputStream = request.getInputStream();

            memberLoginDto = new ObjectMapper().readValue(inputStream, MemberLoginDto.class);
        } catch(IOException e) {
            throw new RuntimeException();
        }

        log.info("member : {}", memberLoginDto);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberLoginDto.getEmail(), memberLoginDto.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }

    /**
     * front 서버에서 넘어온 회원정보를 이용해서 해당 authentication 객체를 만들어 다음 작업으로 넘겨주는 메소드
     *
     * front -> request(loginId, password) -> authentication생성 -> authenticationManager로 위임
     * */


    // @Override
    // public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
    //         throws AuthenticationException, ServletException {
    //
    //     String method = request.getMethod();
    //
    //     if(!method.equals("POST"))
    //         throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
    //
    //     ServletInputStream inputStream = request.getInputStream();
    //
    //     MemberLoginDto memberLoginDto = new ObjectMapper().readValue(inputStream, MemberLoginDto.class);
    //
    //     UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberLoginDto.getEmail(), memberLoginDto.getPassword());
    //
    //     return authenticationManager.authenticate(authenticationToken);
    // }

    // 로그인 filter 성공
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("==============================successful authenication=============================");
        // List<String> roles = authResult.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        String email = authResult.getName();

        String accessToken = jwtTokenProvider.createAccessToken(email);
        String refreshToken = jwtTokenProvider.createRefreshToken(email);

        Cookie cookie = new Cookie("refreshToken", refreshToken);

        response.addHeader(AUTHENTICATION, PREFIX_BEARER + accessToken);
        response.addCookie(cookie);
    }

    // 로그인 filter 실패
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
