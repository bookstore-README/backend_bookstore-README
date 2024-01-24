package com.bookstore.readme.common.jwt;

import com.bookstore.readme.common.security.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailService customUserDetailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {

        String email = auth.getName();
        String password = (String) auth.getCredentials();

        UserDetails user = customUserDetailService.loadUserByUsername(email);

        if(Objects.isNull(user))
            throw new BadCredentialsException("해당 유저는 존재하지 않습니다.");
        else if(!this.passwordEncoder.matches(password, user.getPassword()))
            throw new BadCredentialsException("비밀번호가 틀렸습니다.");

        return new UsernamePasswordAuthenticationToken(
                email, null, user.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
