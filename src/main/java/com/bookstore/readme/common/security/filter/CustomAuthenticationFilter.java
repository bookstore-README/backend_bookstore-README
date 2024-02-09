package com.bookstore.readme.common.security.filter;

import com.bookstore.readme.domain.member.dto.MemberLoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

@Slf4j
public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/member/sign-in", "POST");

    private final ObjectMapper objectMapper;


    public CustomAuthenticationFilter(ObjectMapper objectMapper) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String method = request.getMethod();

        if(!method.equals("POST"))
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());

        MemberLoginDto memberLoginDto = null;

        try{
            ServletInputStream inputStream = request.getInputStream();

            memberLoginDto = objectMapper.readValue(inputStream, MemberLoginDto.class);
        } catch(IOException e) {
            throw new RuntimeException();
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberLoginDto.getEmail(), memberLoginDto.getPassword());

        return this.getAuthenticationManager().authenticate(authenticationToken);
    }
}
